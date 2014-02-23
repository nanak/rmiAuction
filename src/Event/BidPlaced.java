package Event;

import java.util.Date;

public class BidPlaced extends BidEvent {

	public BidPlaced(String iD, String type, long timestamp, String username,
			long auctionID, double price) {
		super(iD, type, timestamp, username, auctionID, price);
		// TODO Auto-generated constructor stub
	}
 
	@Override
	public String toString(){
		return "" + type + ": "+new Date(timestamp).toString() + " - Bid placed on auction " + auctionID + " with value " + price;
	}
}
 
