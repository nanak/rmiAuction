package Event;

import java.util.Date;

public class BidWon extends BidEvent {

	public BidWon(String iD, String type, long timestamp, String username,
			long auctionID, double price) {
		super(iD, type, timestamp, username, auctionID, price);
		// TODO Auto-generated constructor stub
	}
 
	@Override
	public String toString(){
		return "" + type + ": "+new Date(timestamp).toString() + " - Bid won on auction: " + auctionID + " with value " + price;
	}
}
 
