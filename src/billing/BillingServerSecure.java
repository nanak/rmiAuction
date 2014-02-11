package billing;

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

	private ConcurrentHashMap<String,PriceSteps> priceSteps;

	private ConcurrentHashMap<Integer,Bill> bills;

	//private FileHandler<K,T> fileHandler;

	public ConcurrentHashMap<String, PriceSteps> getPriceSteps() {
		return priceSteps;
	}

	public void createPriceStep(double startPrice, double endPrice, double fixedPrice, double variablePricePercent)throws PriceStepIntervalOverlapException {
		PriceSteps p;
			try {
				p = new PriceSteps(startPrice, endPrice, fixedPrice, variablePricePercent);
				// TODO test if range overlaps and throw exception
				priceSteps.put(startPrice+""+endPrice, p);
			} catch (IllegalValueException e) {
				e.printStackTrace();
			}

	}

	public void deletePriceStep(Double startPrice, Double endPrice) {
		priceSteps.remove(startPrice+""+endPrice);
	}

	public void billAuction(Auction auction) {

	}

	public String getBill(String user) {
		return null;
	}
	
	

}
