package Client;

public class BidThread implements Runnable{
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
		t= new Thread(this);
		t.start();
		// TODO Auto-generated constructor stub
	}

	@Override
	public void run() {
		if(first){
			try {
				t.sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			first=false;
		}
		while(true){
			id=cli.getRandomID();
			amount=(double)(System.currentTimeMillis()-starttime);
			System.out.println(amount);
			cli.write("!bid "+id+" "+amount);
			try{
				t.sleep(bidpM*60000);
			}catch(InterruptedException e){
				e.printStackTrace();	
			}
		}
		
	}

}
