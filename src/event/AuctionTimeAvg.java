package event;

import java.util.Date;

public class AuctionTimeAvg extends StatisticsEvent {

	public AuctionTimeAvg(String iD, String type, long timestamp, double value) {
		super(iD, type, timestamp, value);
		// TODO Auto-generated constructor stub
	}
	@Override
	public String toString(){
		return "" + type + ": "+new Date(timestamp).toString() + " - Auction Time Average: " + (int)value/1000 + " seconds";
	}
}
 
