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
	private static final String ALPHA_NUMERIC_STRING = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
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
			cli.write("!login "+randomAlphaNumeric(10));
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
			cli.write("!create "+aucD+" "+randomAlphaNumeric(7));
			try{
				t.sleep(aucpM*60000);
			}catch(InterruptedException e){
				e.printStackTrace();	
			}
		}
		
	}
	public static String randomAlphaNumeric(int count) {
		StringBuilder builder = new StringBuilder();
		while (count-- != 0) {
			int character = (int)(Math.random()*ALPHA_NUMERIC_STRING.length());
			builder.append(ALPHA_NUMERIC_STRING.charAt(character));
		}
		return builder.toString();
	}

}
