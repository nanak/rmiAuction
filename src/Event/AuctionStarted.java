package Event;

public class AuctionStarted extends AuctionEvent {

	public AuctionStarted(String iD, String type, long timestamp, long auctionID) {
		super(iD, type, timestamp, auctionID);
		// TODO Auto-generated constructor stub
	}
 
	@Override
	public String toString(){
		return "" + type + ": "+timestamp + " - Auction started with id " + auctionID;
	}
}
 
