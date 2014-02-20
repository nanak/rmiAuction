package loadtest;

import java.util.TimerTask;


/**
 * Thread, which lists all running auctions in a specified interval
 * 
 * @author Michaela Lipovits
 * @version 20140217
 */
public class ListThread extends TimerTask{
	private FakeCli cli;
	private int update;
	private Thread t;
	private boolean first;
	
	public ListThread(FakeCli cli, int update){
		this.cli=cli;
		this.update=update;
		first=true;
	}

	@Override
	public void run() {
		if(cli.isClientAlive()){
			cli.write("!list");
		}
	}

}
