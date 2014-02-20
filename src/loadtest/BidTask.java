package loadtest2;

import java.util.TimerTask;

import Client.TaskExecuter;


public class BidTask extends TimerTask{
	private int bidpM;
	private long starttime;
	private boolean first;
	private int id;
	private double amount;
	private TaskExecuter t;
	private FakeCli cli;

	public BidTask(int bidspM, long starttime, TaskExecuter t, FakeCli cli) {
		this.cli=cli;
		this.t=t;
		this.bidpM=bidspM;
		this.starttime=starttime;
		first=true;
	}

	@Override
	public void run() {
//		if(cli.isClientAlive()){
		System.out.println(id);
			id=cli.getRandomID();
			//evtl genauer machen mit nano-s
			amount=(double)(System.currentTimeMillis()-starttime);
			t.bid(id, amount);
//		}
		
	}

}
