package event;

import java.util.Date;

public class BidCountPerMinute extends StatisticsEvent {

	public BidCountPerMinute(String iD, String type, long timestamp,
			double value) {
		super(iD, type, timestamp, value);
		// TODO Auto-generated constructor stub
	}
 
	@Override
	public String toString(){
		return "" + type + ": "+new Date(timestamp).toString() + " - Bids/Minute: " + value;
	}
}
 
