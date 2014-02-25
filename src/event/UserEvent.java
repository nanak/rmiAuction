package event;

public abstract class UserEvent extends Event {
 
	protected String username;

	/**
	 * @param iD
	 * @param type
	 * @param timestamp
	 * @param username
	 */
	public UserEvent(String iD, String type, long timestamp, String username) {
		super(iD, type, timestamp);
		this.username = username;
	}

	public String getUsername() {
		return username;
	}


	 
	
	
}
 
