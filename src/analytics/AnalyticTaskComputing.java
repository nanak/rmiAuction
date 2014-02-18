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
		as.subscribe(filter, ci);
		return true;
	}
	 
	public boolean unsubscribe(ClientInterface ci) {
		as.unsubscribe(ci);//TODO Funktion im Analyticsserver ueberarbeiten
		return false;
	}
	 
	public void processEvent(Event e) {
		as.processEvent(e);
	}
	 
}
 
