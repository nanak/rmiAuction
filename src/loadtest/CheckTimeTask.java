package loadtest;

import java.util.Timer;
import java.util.TimerTask;

import analytics.AnalyticsServer;
import billing.BillingServer;
import management.ManagmentClient;


/**
 * Thread, which lists all running auctions in a specified interval
 * 
 * @author Michaela Lipovits
 * @version 20140217
 */
public class CheckTimeTask extends TimerTask{
	private long starttime;
	private long min=8*60000;
	private long status;
	private Timer list;
	private Timer create;
	private Timer bid;
	private ManagmentClient m;
	private FakeCli mcli;
	private AnalyticsServer as;
	private BillingServer bs;
	
	/**
	 * Constructor, which saves the starttime of the loadtest, the managementClient, the FakeCli of the ManagementClient initiated in LoadTest,
	 * as well as the Timers for list, bid and create.
	 * 
	 * @param starttime starttime of the loadtest in milliseconds
	 * @param list2 Timer of ListTask
	 * @param create2 Timer of CreateTask
	 * @param bid2 Timer of BidTask
	 * @param m ManagementClient
	 * @param mcli FakeCli of the ManagementClient initiated in LoadTest
	 * @param bs 
	 * @param as 
	 */
	public CheckTimeTask(long starttime, Timer list2, Timer create2, Timer bid2, ManagmentClient m, FakeCli mcli, long min, AnalyticsServer as, BillingServer bs){
		this.mcli=mcli;
		this.starttime=starttime;
		this.list=list2;
		this.create=create2;
		this.m=m;
		this.min=min;
		this.bid=bid2;
		this.as=as;
		this.bs=bs;
	}

	/**
	 * This run method checks if 8 Minutes are over. 
	 * If it is so, the ManagementClient  unsubscribes and stops.
	 * The timers for ListTask, CreateTask and BidTask are stopped, as well as this timer itself.
	 */
	@Override
	public void run() {
		status=System.currentTimeMillis()-starttime;
		if(status>=min){
			if(mcli!=null)
				mcli.write("!unsubscribe 0\n!end");
			list.cancel();
			list.purge();
			create.cancel();
			create.purge();
			bid.cancel();
			bid.purge();
			long passed=min+status;
			System.out.println("Loadtest ended. Time passed: "+passed);
			if(m!=null)
				m.setRunning(false);
			as.shutdown();
			bs.shutdown();
			this.cancel();
		}
	}

}
