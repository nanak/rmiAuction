package analytics;

import management.ClientInterface;

public class AnalyticTaskComputing implements Remote {
 
	private AnalyticsServer as;
	 
	public boolean subscribe(String filter, ClientInterface ci) {
		return false;
	}
	 
	public boolean unsubscribe(ClientInterface ci) {
		return false;
	}
	 
	public void processEvent(Event_T_ e) {
	 
	}
	 
}
 
