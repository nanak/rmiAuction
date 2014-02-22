package loadtest;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

import loadtest.FakeCli;
import management.ManagmentClient;
import Client.TaskExecuter;
import Client.UI;


/**
 * Thread, which lists all running auctions in a specified interval
 * 
 * @author Michaela Lipovits
 * @version 20140217
 */
public class CheckTimeTask extends TimerTask{
	private long starttime;
	private long min=1*60000;
	private long status;
	private Timer list;
	private Timer create;
	private Timer bid;
	private ManagmentClient m;
	private FakeCli mcli;
	
	public CheckTimeTask(long starttime, Timer list2, Timer create2, Timer bid2, ManagmentClient m, FakeCli mcli){
		this.mcli=mcli;
		this.starttime=starttime;
		this.list=list2;
		this.create=create2;
		this.m=m;
		this.bid=bid2;
	}

	@Override
	public void run() {
		status=System.currentTimeMillis()-starttime;
		if(status>=min){
			mcli.write("!unsubscribe 1");
			list.cancel();
			list.purge();
			create.cancel();
			create.purge();
			bid.cancel();
			bid.purge();
			long passed=min+status;
			System.out.println("Loadtest ended. Time passed: "+passed);
			m.setRunning(false);
			this.cancel();
		}
	}

}
