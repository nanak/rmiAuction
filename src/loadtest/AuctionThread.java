package loadtest;


/**
 * Thread which creates Auctions with certain durations in set Intervals.
 * 
 * @author Michaela Lipovits
 * @version 20140216
 *
 */
public class AuctionThread implements Runnable{
	private int aucpM,aucD;
	private FakeCli cli;
	private static final String STRING_CHARS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
	private Thread t;
	private boolean first;
	
	public AuctionThread(int aucpM, int aucD, FakeCli cli){
		this.aucD=aucD;
		this.aucpM=aucpM;
		this.cli=cli;
		first=true;
		t= new Thread(this);
		t.start();
	}

	@Override
	public void run() {
		if(first){
			cli.write("!login "+randomString(10));
			first=false;
			try {
				t.sleep(aucpM*60000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		while(cli.isClientAlive()){
			cli.out("HERE");
			cli.write("!create "+aucD+" "+randomString(7));
			try{
				t.sleep(aucpM*60000);
			}catch(InterruptedException e){
				e.printStackTrace();	
			}
		}
		
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
