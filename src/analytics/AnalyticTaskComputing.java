package analytics;

import event.Event;
import management.ClientInterface;

/**
 * RemoteKlasse des AnalyticsSercer
 * @author Thomas Traxler <ttraxler@student.tgm.ac.at>
 *
 */

public class AnalyticTaskComputing implements RemoteAnalyticsTaskComputing{
 
	private AnalyticsServer as;
	
	public AnalyticTaskComputing (AnalyticsServer as){
		this.as=as;
	}
	 /**
	  * Created Subsrciption with callback
	  * @param filter
	  * @param clientId
	  * @param ci
	  * @return
	  */
	public String subscribe(String filter, ClientInterface ci) {
		return as.subscribe(filter, ci);
	}
	 /**
	  * Terminates a Subsrciption
	  * @param subscriptionId
	  * @return
	  */
	public String unsubscribe(String subscriptionId) {
		return as.unsubscribe(subscriptionId);//TODO Funktion im Analyticsserver ueberarbeiten, ID;
	}
	 
	public void processEvent(Event e) {
		as.processEvent(e);
	}
	 
}
 
