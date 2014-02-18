package loadtest;


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
	}

	@Override
	public void run() {
		if(first){
			try {
				t.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			first=false;
		}
		while(cli.isClientAlive()){
			id=cli.getRandomID();
			//evtl genauer machen mit nano-s
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
