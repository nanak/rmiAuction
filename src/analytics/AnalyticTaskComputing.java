package analytics;

import java.rmi.Remote;


import Event.Event;
import management.ClientInterface;

/**
 * RemoteKlasse des AnalyticsSercer
 * @author Thomas Traxler <ttraxler@student.tgm.ac.at>
 *
 */

public class AnalyticTaskComputing implements Remote{
 
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
 
