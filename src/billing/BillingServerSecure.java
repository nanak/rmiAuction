package billing;

import java.io.IOException;
import java.rmi.RemoteException;
import java.util.Iterator;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentSkipListMap;

import serverModel.FileHandler;

import billing.model.Bill;
import billing.model.CompositeKey;
import billing.model.PriceStep;
import exceptions.CannotCastToMapException;
import exceptions.IllegalValueException;
import exceptions.PriceStepIntervalOverlapException;

/**
 * Provides the actual functionality of the BillingServer
 *    
 * @author Rudolf Krepela
 * @email rkrepela@student.tgm.ac.at
 * @version 11.02.2014
 *
 */
public class BillingServerSecure  {

	private ConcurrentSkipListMap<CompositeKey,PriceStep> priceSteps;

	private ConcurrentHashMap<String,Bill> bills;

	private FileHandler<String,Bill> fileHandler;	//FileHandler to Save the bills
	private FileHandler<CompositeKey,PriceStep> fhSteps; //FileHandler to save the pricesteps
	
	/**
	 * Loads the Bills/Pricesteps and puts it into the attributes.
	 */
	public BillingServerSecure(){
		try {
			fileHandler= new FileHandler<String,Bill>("bills.txt");
			bills=(ConcurrentHashMap<String, Bill>) fileHandler.readAll();
			fileHandler.deleteFile();
			fhSteps= new FileHandler<CompositeKey,PriceStep>("pricesteps.txt");
			priceSteps=(ConcurrentSkipListMap<CompositeKey,PriceStep>) fhSteps.readAll();
			fhSteps.deleteFile();
		} catch (IOException | CannotCastToMapException | ClassCastException e) {
			bills=new ConcurrentHashMap<String,Bill>();
			priceSteps=new ConcurrentSkipListMap<CompositeKey,PriceStep>();
		}
	}
	/**
	 * This method returns the current configuration of price steps. 
	 * (prints all PriceSteps)
	 * @return	Formatted String with allPricesteps
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
	 * 
	 * @param startPrice	Starting Price
	 * @param endPrice		End Price
	 * @param fixedPrice	Fix Price for an auction in this interval
	 * @param variablePricePercent	Variable percentage 
	 * @throws PriceStepIntervalOverlapException	Is thrown, if it overlaps with an existing pricestep
	 */
	public void createPriceStep(double startPrice, double endPrice, double fixedPrice, double variablePricePercent)throws PriceStepIntervalOverlapException,IllegalValueException{
		PriceStep p;
		CompositeKey k;
				p = new PriceStep(startPrice, endPrice, fixedPrice, variablePricePercent);
				k=new CompositeKey(startPrice, endPrice);
				Iterator<CompositeKey> i=priceSteps.keySet().iterator();
				while(i.hasNext()){
					if(k.overlaps(i.next())){
						throw new PriceStepIntervalOverlapException();
					}
				}
				priceSteps.put(k, p);
	}
	/**
	 * This method allows to delete a price step for the pricing curve.
	 * 
	 * @param startPrice		StartPrice
	 * @param endPrice			EndPrice
	 * @throws RemoteException if interval does not exist
	 * @return	true if pricestep was successfully deleted
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
	 * @param user	User the bill is for
	 * @param auctionID	AuctionID from the Auction
	 * @param price	StrikePrice of the auction
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
	 * @param user	User for whom the bill is
	 * @return	Returns the formatted bill for the specific user
	 */
	public String getBill(String user) {
		if(bills.containsKey(user))return bills.get(user).toString();
		return "No bill for the user "+user+" available.";
	}
	
	/**
	 * Saves all data to files
	 */
	public void shutdown(){
		try {
			fileHandler= new FileHandler<String,Bill>("bills.txt");
			fileHandler.writeMap(bills);
			fhSteps= new FileHandler<CompositeKey,PriceStep>("pricesteps.txt");
			fhSteps.writeMap(priceSteps);
		} catch (IOException e) {
			System.err.println("Could not save bill.");
		}
	}
}
