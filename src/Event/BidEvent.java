package Event;

public abstract class BidEvent extends Event {
 

	protected String username;
	 
	protected long auctionID;
	 
	protected double price;

	/**
	 * @param iD
	 * @param type
	 * @param timestamp
	 * @param username
	 * @param auctionID
	 * @param price
	 */
	public BidEvent(String iD, String type, long timestamp, String username,
			long auctionID, double price) {
		super(iD, type, timestamp);
		this.username = username;
		this.auctionID = auctionID;
		this.price = price;
	}

	public String getUsername() {
		return username;
	}

	public long getAuctionID() {
		return auctionID;
	}

	public double getPrice() {
		return price;
	}
	
	
}
 
