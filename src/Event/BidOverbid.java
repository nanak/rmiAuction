package Event;

public class BidOverbid extends BidEvent {

	public BidOverbid(String iD, String type, long timestamp, String username,
			long auctionID, double price) {
		super(iD, type, timestamp, username, auctionID, price);
		// TODO Auto-generated constructor stub
	}
 
	@Override
	public String toString(){
		return "" + type + ": "+timestamp + " - Bid was overbid on auction " + auctionID+ " with value" + price;
	}
}
 
