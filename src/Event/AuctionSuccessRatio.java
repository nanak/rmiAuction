package Event;

import java.util.Date;

public class AuctionSuccessRatio extends StatisticsEvent {

	public AuctionSuccessRatio(String iD, String type, long timestamp,
			double value) {
		super(iD, type, timestamp, value);
		// TODO Auto-generated constructor stub
	}
 
	@Override
	public String toString(){
		return "" + type + ": "+new Date(timestamp).toString() + " - Auction Success Ration: " + value;
	}
}
 
