package loadtest2;

import java.util.TimerTask;

import Client.TaskExecuter;


/**
 * Thread which creates Auctions with certain durations in set Intervals.
 * 
 * @author Michaela Lipovits
 * @version 20140216
 *
 */
public class CreateTask extends TimerTask{
	private int aucpM,aucD;
	private TaskExecuter t;
	private static final String STRING_CHARS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
	private boolean first;
	private int tcpPort;
	
	public CreateTask(int aucpM, int aucD, TaskExecuter t, int tcpPort){
		this.tcpPort=tcpPort;
		this.aucD=aucD;
		this.aucpM=aucpM;
		this.t=t;
		first=true;
	}

	@Override
	public void run() {
		if(first){
			t.login(randomString(10), tcpPort, 0);
			first=false;
			try {
				Thread.sleep(500);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
//		if(cli.isClientAlive()){
		t.create((long) aucD, randomString(7));
//		}
		
	}
	public static String randomString(int count) {
		StringBuilder builder = new StringBuilder();
		while (count-- != 0) {
			int character = (int)(Math.random()*STRING_CHARS.length());
			builder.append(STRING_CHARS.charAt(character));
		}
		return builder.toString();
	}

}
