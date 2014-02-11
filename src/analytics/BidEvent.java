package analytics;

public abstract class BidEvent<T> extends Event<T> {
 
	protected String username;
	 
	protected long auctionID;
	 
	protected double price;
	 
}
 
