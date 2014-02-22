package loadtest;

import java.util.TimerTask;

import Client.TaskExecuter;


/**
 * Thread, which lists all running auctions in a specified interval
 * 
 * @author Michaela Lipovits
 * @version 20140217
 */
public class ListTask extends TimerTask{
	private boolean first;
	private TaskExecuter t;
	
	public ListTask( TaskExecuter t){
		this.t=t;
		first=true;
	}

	@Override
	public void run() {
//		if(cli.isClientAlive()){
			t.list();
//		}
	}

}
