package Event;

public class UserSessionTimeMin extends StatisticsEvent {

	public UserSessionTimeMin(String iD, String type, long timestamp,
			double value) {
		super(iD, type, timestamp, value);
		// TODO Auto-generated constructor stub
	}
 
	@Override
	public String toString(){
		return "" + type + ": "+timestamp + " - User Session Time Min: " + (int)value/1000 + " seconds";
	}
}
 
