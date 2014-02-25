package event;

import java.util.Date;

public class BidPriceMax extends StatisticsEvent {

	public BidPriceMax(String iD, String type, long timestamp, double value) {
		super(iD, type, timestamp, value);
		// TODO Auto-generated constructor stub
	}
 
	@Override
	public String toString(){
		return "" + type + ": "+new Date(timestamp).toString()+ " - Bid price max seen so far: " + value;
	}
}
 
