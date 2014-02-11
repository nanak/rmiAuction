package billing;

import java.util.concurrent.ConcurrentHashMap;

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

	private ConcurrentHashMap<Integer,PriceSteps> priceSteps;

	private ConcurrentHashMap<Integer,Bill> bills;

	//private FileHandler<K,T> fileHandler;

	public PriceSteps getPriceSteps() {
		return null;
	}

	public String createPriceStep(Double startPrice, Double endPrice, Double fixedPrice, double variablePricePercent) {
		return null;
	}

	public String deletePriceStep(Double startPrice, Double endPrice) {
		return null;
	}

	public void billAuction(Auction auction) {

	}

	public String getBill(String user) {
		return null;
	}
	
	

}
