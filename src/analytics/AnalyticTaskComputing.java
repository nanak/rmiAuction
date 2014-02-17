package analytics;

import java.rmi.Remote;

import Event.Event;
import management.ClientInterface;

public class AnalyticTaskComputing implements Remote{
 
	private AnalyticsServer as;
	public static final String SERVERNAME = "AnalyticServer";
	 
	public boolean subscribe(String filter, ClientInterface ci) {
		return false;
	}
	 
	public boolean unsubscribe(ClientInterface ci) {
		return false;
	}
	 
	public void processEvent(Event e) {
		
	}
	 
}
 
