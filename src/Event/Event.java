package Event;

import java.io.Serializable;

public abstract class Event implements Serializable {
 
	protected String ID;
	
	protected String type;
	
	public String getID() {
		return ID;
	}

	public long getTimestamp() {
		return timestamp;
	}

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
	
	public String getType(){
		return type;
	}
	
	/**
	 * Compares if to events are equal
	 * @return	true if the id is the same
	 */
	@Override
	public boolean equals(Object o){
		Event e = (Event)o;
		return e.getID().equals(ID);
		
	}
	
	
}
 
