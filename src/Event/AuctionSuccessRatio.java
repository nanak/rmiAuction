package Event;

public class AuctionSuccessRatio extends StatisticsEvent {

	public AuctionSuccessRatio(String iD, String type, long timestamp,
			double value) {
		super(iD, type, timestamp, value);
		// TODO Auto-generated constructor stub
	}
 
	@Override
	public String toString(){
		return "" + type + ": "+timestamp + " - Auction Success Ration: " + value;
	}
}
 
