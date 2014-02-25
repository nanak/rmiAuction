package analytics;

import java.util.Date;
import java.util.TimerTask;
import java.util.UUID;

import event.BidCountPerMinute;

/**
 * Creates every 60 seconds a Event which tells how many bids are placed statistically per minute
 * @author Daniel Reichmann
 * @version 2012-02-16
 *
 */
public class BidCountPerMinuteWatcher extends TimerTask{
	private AnalyticsServer as; //AnalyticsServeer to check how may bids were placed
	private int cycles = 0;
	
	/**
	 * Sets the AnalyticsServer
	 * 
	 * @param server	AnalyticsServer
	 */
	public BidCountPerMinuteWatcher(AnalyticsServer server){
		as = server;
	}
	
	/**
	 * Calculates the BidCount/Minute and Creates new Event
	 */
	@Override
	public void run() {
			cycles ++;
			double bidsmin = ((double)as.getEventHandler().getBidCount()) / cycles;
			Date now = new Date();
			BidCountPerMinute bpm =  new BidCountPerMinute("" +UUID.randomUUID().getMostSignificantBits(),"BID_COUNT_PER_MINUTE", now.getTime(), bidsmin);
			as.getDispatchedEvents().add(bpm);
			as.notifyClients();
	}
	
}
