package loadtest;

import java.util.TimerTask;

import client.TaskExecuter;


/**
 * Class BidTask, which is a TimerTask, bids on random auctions for the loadtest.
 * 
 * @author Michaela Lipovits
 * @version 20140217
 */
public class BidTask extends TimerTask{
	private int bidpM;
	private long starttime;
	private boolean first;
	private int id;
	private double amount;
	private TaskExecuter t;
	private FakeCli cli;

	/**
	 * Constructor, which saves BidsPerMinute, the starttime of the loadtest, as well as the TaskExecuter and the FakeCli 
	 * of the Client initiated in LoadTest.
	 * 
	 * @param bidspM Bids per Minute
	 * @param starttime Starttime of the loadtest in milliseconds
	 * @param t TaskExecuter of the Client initiated in LoadTest
	 * @param cli FakeCli of the Client initiated in LoadTest
	 */
	public BidTask(int bidspM, long starttime, TaskExecuter t, FakeCli cli) {
		this.cli=cli;
		this.t=t;
		this.bidpM=bidspM;
		this.starttime=starttime;
		first=true;
	}

	/**
	 * This method requests a random ID from the FakeCli of the Client, and bids on that auction.
	 * The bid-amount equals the time passed since the start of the loadtest in milliseconds.
	 */
	@Override
	public void run() {
//		if(cli.isClientAlive()){
			id=cli.getRandomID();
			//evtl genauer machen mit nano-s
			amount=(double)(System.currentTimeMillis()-starttime);
			t.bid(id, amount);
//		}
		
	}

}
