package billing;

import java.io.IOException;
import java.rmi.RemoteException;
import java.util.Iterator;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentSkipListMap;

import billing.model.Bill;
import billing.model.CompositeKey;
import billing.model.PriceStep;
import exceptions.PriceStepIntervalOverlapException;

/**
 * provides the actual functionality of the BillingServer
 *    
 * @author Rudolf Krepela
 * @email rkrepela@student.tgm.ac.at
 * @version 11.02.2014
 *
 */
public class BillingServerSecure  {

	//private ConcurrentHashMap<String,Login> user;

	private ConcurrentSkipListMap<CompositeKey,PriceStep> priceSteps;

	private ConcurrentHashMap<String,Bill> bills;

	private FileHandler<String,Bill> fileHandler;
	
	
	public BillingServerSecure(){
		priceSteps=new ConcurrentSkipListMap<CompositeKey,PriceStep>();
		try {
			fileHandler= new FileHandler<String,Bill>("bills.txt");
			bills=(ConcurrentHashMap<String, Bill>) fileHandler.readAll();
			fileHandler.deleteFile();
		} catch (IOException | CannotCastToMapException e) {
			bills=new ConcurrentHashMap<String,Bill>();
		}
	}
	/**
	 * This method returns the current configuration of price steps. 
	 * (prints all PriceSteps)
	 * @return
	 */
	public String getPriceSteps() {
		Iterator<PriceStep> i=priceSteps.values().iterator();
		if(!i.hasNext())
			return "No Pricesteps yet";
		PriceStep p=i.next();
		String r=p.getVariableNames()+"\n"+p.toString();
		while(i.hasNext()){
			r=r+"\n"+i.next().toString();
		}
		return r;
	}
	/**
	 * This method allows to create a price step for a given price interval.
	 * @param startPrice
	 * @param endPrice
	 * @param fixedPrice
	 * @param variablePricePercent
	 * @throws PriceStepIntervalOverlapException
	 */
	public void createPriceStep(double startPrice, double endPrice, double fixedPrice, double variablePricePercent)throws PriceStepIntervalOverlapException {
		PriceStep p;
		CompositeKey k;
			try {
				p = new PriceStep(startPrice, endPrice, fixedPrice, variablePricePercent);
				k=new CompositeKey(startPrice, endPrice);
				Iterator<CompositeKey> i=priceSteps.keySet().iterator();
				while(i.hasNext()){
					if(k.overlaps(i.next())){
						throw new PriceStepIntervalOverlapException();
					}
				}
				priceSteps.put(k, p);
			} catch (IllegalValueException e) {
				System.err.println(e);
			}

	}
	/**
	 * This method allows to delete a price step for the pricing curve.
	 * @param startPrice
	 * @param endPrice
	 * @throws RemoteException if interval does not exist
	 */
	public boolean deletePriceStep(Double startPrice, Double endPrice) {
		CompositeKey k=new CompositeKey(startPrice, endPrice);
		if(priceSteps.containsKey(k)){
			priceSteps.remove(k);
			return true;
		}
		return false;
	}

	/**
	 * This method is called by the auction server as soon as an auction has ended.
	 * The billing server stores the auction result 
	 * and later uses this information to calculate the bill for a user.
	 * 
	 * if price step does not exist adds 0,0 as pricing curve
	 * @param auction
	 */
	public void billAuction(String user, long auctionID, double price) {
		boolean fail=true;
		Iterator<CompositeKey> it=priceSteps.keySet().iterator();
		while(it.hasNext()){
			CompositeKey key=(CompositeKey) it.next();
			if(key.matches(price)){
				fail=false;
				PriceStep step=priceSteps.get(key);
				if(bills.containsKey(user)){
					bills.get(user).addBillingLine(auctionID, price,step.getFixedPrice(),step.getVariablePricePercent());
				}else{
					bills.put(user, new Bill(user, auctionID, price,step.getFixedPrice(),step.getVariablePricePercent()));
				}
			}
		}
		if(fail){
			if(bills.containsKey(user)){
				bills.get(user).addBillingLine(auctionID, price,0,0);
			}else{
				bills.put(user, new Bill(user, auctionID, price,0,0));
			}
		}
	}
	
	/**
	 * This method calculates and returns the bill for a given user,
	 * based on the price steps stored within the billing server.
	 * @param user
	 * @return
	 */
	public String getBill(String user) {
		if(bills.containsKey(user))return bills.get(user).toString();
		return "No bill for the user "+user+" available.";
	}
	
	/**
	 * saves all data to files
	 */
	public void shutdown(){
		try {
			fileHandler= new FileHandler<String,Bill>("bills.txt");
			fileHandler.writeMap(bills);
		} catch (IOException e) {
			System.err.println("Could not save bill.");
		}
	}
}
