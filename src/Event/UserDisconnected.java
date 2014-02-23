package Event;

import java.util.Date;

public class UserDisconnected extends UserEvent {

	public UserDisconnected(String iD, String type, long timestamp,
			String username) {
		super(iD, type, timestamp, username);
		// TODO Auto-generated constructor stub
	}
 
	@Override
	public String toString(){
		return "" + type + ": "+new Date(timestamp).toString() + " - User diconnected: " + username;
	}
}
 
