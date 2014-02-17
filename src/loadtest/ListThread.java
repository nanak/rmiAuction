package loadtest;


/**
 * Thread, which lists all running auctions in a specified interval
 * 
 * @author Michaela Lipovits
 * @version 20140217
 */
public class ListThread implements Runnable{
	private FakeCli cli;
	private int update;
	private Thread t;
	private boolean first;
	
	public ListThread(FakeCli cli, int update){
		this.cli=cli;
		this.update=update;
		first=true;
		t= new Thread(this);
		t.start();
	}

	@Override
	public void run() {
		if(first){
			try {
				t.sleep(600);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			first=false;
		}
		while(cli.isClientAlive()){
			cli.write("!list");
			try {
				t.sleep(update*1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		
	}

}
