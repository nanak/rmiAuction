package analytics;

public abstract class Event {
 
	protected String ID;
	
	protected String type;
	
	protected long timestamp;

	/**
	 * @param iD
	 * @param type
	 * @param timestamp
	 */
	public Event(String iD, String type, long timestamp) {
		ID = iD;
		this.type = type;
		this.timestamp = timestamp;
	}
	
	
	
	
}
 
