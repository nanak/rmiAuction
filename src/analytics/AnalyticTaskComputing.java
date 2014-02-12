package analytics;

import java.rmi.Remote;

import management.ClientInterface;

public class AnalyticTaskComputing implements Remote{
	
	public static final String SERVERNAME = "AnalyticServer";
 
	private AnalyticsServer as;
	 
	public boolean subscribe(String filter, ClientInterface ci) {
		return false;
	}
	 
	public boolean unsubscribe(ClientInterface ci) {
		return false;
	}
	 
	public void processEvent(Event e) {
		
	}
	 
}
 
