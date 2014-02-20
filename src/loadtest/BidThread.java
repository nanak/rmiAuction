package loadtest;

import java.util.TimerTask;


public class BidThread extends TimerTask{
	private Thread t;
	private int bidpM;
	private long starttime;
	private boolean first;
	private FakeCli cli;
	private int id;
	private double amount;

	public BidThread(FakeCli cli, int bidspM, long starttime) {
		this.cli=cli;
		this.bidpM=bidspM;
		this.starttime=starttime;
		first=true;
	}

	@Override
	public void run() {
		if(cli.isClientAlive()){
			id=cli.getRandomID();
			//evtl genauer machen mit nano-s
			amount=(double)(System.currentTimeMillis()-starttime);
			System.out.println(amount);
			cli.write("!bid "+id+" "+amount);
		}
		
	}

}
