package Event;

import java.util.Date;

public class UserSessionTimeAvg extends StatisticsEvent {

	public UserSessionTimeAvg(String iD, String type, long timestamp,
			double value) {
		super(iD, type, timestamp, value);
		// TODO Auto-generated constructor stub
	}
 
	@Override
	public String toString(){
		return "" + type + ": "+new Date(timestamp).toString() + " - User Session Time Average: "+  (int)value/1000 + " seconds";
	}
}
 
