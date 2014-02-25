package loadtest;

import java.util.TimerTask;

import client.TaskExecuter;


/**
 * Thread, which lists all running auctions in a specified interval
 * 
 * @author Michaela Lipovits
 * @version 20140217
 */
public class ListTask implements Runnable{
	private boolean first;
	private TaskExecuter t;
	
	/**
	 * Constructor, which sets the TaskExecuter of the Client initiated in LoadTest.
	 * @param t TaskExecuter of the Client initiated in LoadTest
	 */
	public ListTask( TaskExecuter t){
		this.t=t;
		first=true;
	}

	/**
	 * Lists all running auctions via the TaskExecuter
	 */
	@Override
	public void run() {
//		if(cli.isClientAlive()){
			t.list();
//		}
	}

}
