package event;

public abstract class StatisticsEvent extends Event {
 
	protected double value;

	/**
	 * @param iD
	 * @param type
	 * @param timestamp
	 * @param value
	 */
	public StatisticsEvent(String iD, String type, long timestamp, double value) {
		super(iD, type, timestamp);
		this.value = value;
	}
	 
	public double getValue(){
		return value;
	}
	
}
 
