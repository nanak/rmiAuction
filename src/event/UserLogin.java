package event;

import java.util.Date;

public class UserLogin extends UserEvent {

	public UserLogin(String iD, String type, long timestamp, String username) {
		super(iD, type, timestamp, username);
		// TODO Auto-generated constructor stub
	}
 
	@Override
	public String toString(){
		return "" + type + ": "+new Date(timestamp).toString()+ " - User logged in: " + username;
	}
}
 
