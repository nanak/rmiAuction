package loadtest;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

import loadtest.FakeCli;
import Client.TaskExecuter;


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
	
	public CheckTimeTask(long starttime, Timer list2, Timer create2, Timer bid2){
		this.starttime=starttime;
		this.list=list2;
		this.create=create2;
		this.bid=bid2;
	}

	@Override
	public void run() {
		status=System.currentTimeMillis()-starttime;
		if(status>=min){
			list.cancel();
			create.cancel();
			bid.cancel();
			this.cancel();
		}
	}

}
