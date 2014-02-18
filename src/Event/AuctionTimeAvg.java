package Event;

public class AuctionTimeAvg extends StatisticsEvent {

	public AuctionTimeAvg(String iD, String type, long timestamp, double value) {
		super(iD, type, timestamp, value);
		// TODO Auto-generated constructor stub
	}
	@Override
	public String toString(){
		return "" + type + ": "+timestamp + " - Auction Time Average: " + (int)value/1000 + " seconds";
	}
}
 
