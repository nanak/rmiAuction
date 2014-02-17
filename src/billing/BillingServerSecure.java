package billing;

import java.util.Iterator;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import Exceptions.IllegalValueException;
import Exceptions.PriceStepIntervalOverlapException;
import ServerModel.FileHandler;
import ServerModel.Auction;
import management.Login;
import management.Bill;

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
	}

	public String getPriceSteps() {
		String r="";
		Iterator i=priceSteps.values().iterator();
		while(i.hasNext()){
			r=r+"\n"+i.next().toString();
		}
		return r;
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

	public void billAuction(Auction auction) {

	}

	public String getBill(String user) {
		return null;
	}
	

}
