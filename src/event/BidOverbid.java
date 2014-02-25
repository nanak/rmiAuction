package event;

import java.util.Date;

public class BidOverbid extends BidEvent {

	public BidOverbid(String iD, String type, long timestamp, String username,
			long auctionID, double price) {
		super(iD, type, timestamp, username, auctionID, price);
		// TODO Auto-generated constructor stub
	}
 
	@Override
	public String toString(){
		return "" + type + ": "+new Date(timestamp).toString() + " - Bid was overbid on auction " + auctionID+ " with value " + price;
	}
}
 
