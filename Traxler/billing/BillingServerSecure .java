package billing;

import java.util.concurrent.ConcurrentHashMap;

import ServerModel.FileHandler<K,T>;
import ServerModel.Auction;
import management.Login;
import management.Bill;

public class BillingServerSecure  {

	private ConcurrentHashMap<String,Login> user;

	private ConcurrentHashMap<Integer,PriceSteps> priceSteps;

	private ConcurrentHashMap<Integer,Bill> bills;

	private FileHandler<K,T> fileHandler<K,T>;

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

	public String getBill(int String user) {
		return null;
	}
	
	

}
