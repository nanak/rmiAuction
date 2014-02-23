package Event;

import java.util.Date;

public class AuctionEnded extends AuctionEvent {

	public AuctionEnded(String iD, String type, long timestamp, long auctionID) {
		super(iD, type, timestamp, auctionID);
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public String toString(){
		return "" + type + ": "+new Date(timestamp).toString() + " - Auction ended with id " + auctionID;
	}
 
}
 
