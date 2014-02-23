package Event;

import java.util.Date;

public class AuctionStarted extends AuctionEvent {

	public AuctionStarted(String iD, String type, long timestamp, long auctionID) {
		super(iD, type, timestamp, auctionID);
		// TODO Auto-generated constructor stub
	}
 
	@Override
	public String toString(){
		return "" + type + ": "+new Date(timestamp).toString() + " - Auction started with id " + auctionID;
	}
}
 
