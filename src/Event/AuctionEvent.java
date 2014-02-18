package Event;

public abstract class AuctionEvent extends Event {
 
	protected long auctionID;

	/**
	 * @param iD
	 * @param type
	 * @param timestamp
	 * @param auctionID
	 */
	public AuctionEvent(String iD, String type, long timestamp, long auctionID) {
		super(iD, type, timestamp);
		this.auctionID = auctionID;
	}
	 
	public long getAuctionID(){
		return auctionID;
	}
	
	
	
}
 
