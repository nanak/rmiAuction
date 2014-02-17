package loadtest;

import Client.FakeCli;

public class AuctionThread implements Runnable{
	private int aucpM,aucD;
	private FakeCli cli;
	private static final String ALPHA_NUMERIC_STRING = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
	
	public AuctionThread(int aucpM, int aucD, FakeCli cli){
		this.aucD=aucD;
		this.aucpM=aucpM;
		this.cli=cli;
		
		new Thread(this).start();
	}

	@Override
	public void run() {
		while(true){
			cli.write("!create "+randomAlphaNumeric(7)+" "+aucD);
			try{
				Thread.sleep(aucpM*60000);
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
