package billing;

import java.util.Iterator;
import java.util.concurrent.ConcurrentHashMap;
import Exceptions.IllegalValueException;
import Exceptions.PriceStepIntervalOverlapException;
import management.Login;

/**
 * provides the actual functionality of the BillingServer
 *    
 * @author Rudolf Krepela
 * @email rkrepela@student.tgm.ac.at
 * @version 11.02.2014
 *
 */
public class BillingServerSecure  {

	private ConcurrentHashMap<String,Login> user;

	private ConcurrentHashMap<CompositeKey,PriceStep> priceSteps;

	private ConcurrentHashMap<String,Bill> bills;

	//private FileHandler<K,T> fileHandler;
	
	public BillingServerSecure(){
		priceSteps=new ConcurrentHashMap<CompositeKey,PriceStep>();
		bills=new ConcurrentHashMap<String,Bill>();
	}

	public String getPriceSteps() {
		Iterator<PriceStep> i=priceSteps.values().iterator();
		PriceStep p=i.next();
		String r=p.getVariableNames()+"\n"+p.getHeadLine()+"\n"+p.toString();
		while(i.hasNext()){
			r=r+"\n"+i.next().toString();
		}
		return r+"\n"+p.getHeadLine();
	}

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
				e.printStackTrace();
			}

	}

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
	 * @param auction
	 */
	public void billAuction(String user, long auctionID, double price) {
		//TODO Test if auction server is logged in
		if(bills.containsKey(user)){
			bills.get(user).addBillingLine(auctionID, price);
		}else{
			bills.put(user, new Bill(user, auctionID, price));
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
}
