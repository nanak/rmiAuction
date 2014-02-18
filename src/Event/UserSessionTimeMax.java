package Event;

public class UserSessionTimeMax extends StatisticsEvent {

	public UserSessionTimeMax(String iD, String type, long timestamp,
			double value) {
		super(iD, type, timestamp, value);
		// TODO Auto-generated constructor stub
	}
 
	@Override
	public String toString(){
		return "" + type + ": "+timestamp + " - User Session Time Max: " + (int)value/1000 + " seconds";
	}
}
 
