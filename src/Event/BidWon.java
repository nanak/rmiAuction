package Event;

public class BidWon extends BidEvent {

	public BidWon(String iD, String type, long timestamp, String username,
			long auctionID, double price) {
		super(iD, type, timestamp, username, auctionID, price);
		// TODO Auto-generated constructor stub
	}
 
	@Override
	public String toString(){
		return "" + type + ": "+timestamp + " - Bid won on auction: " + auctionID + " with value " + price;
	}
}
 
