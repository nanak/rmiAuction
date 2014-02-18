package loadtest;


/**
 * Thread, which 
 * @author Michaela Lipovits
 * @version 2014
 */
public class CheckDuration implements Runnable{
	private long starttime;
	private FakeCli cli;
	private long min=8*60000;
	private Thread t;
	private long status;

	public CheckDuration(FakeCli cli, long starttime){
		this.cli=cli;
		this.starttime=starttime;
		t=new Thread(this);
		t.start();
	}
	@Override
	public void run() {
		while(cli.isClientAlive()){
			status=System.currentTimeMillis()-starttime;
			if(status>=min){
				cli.write("!end");
				cli.setClientsAlive(false);
				System.out.println("client ended. time passed:"+status/60000);
			}
			else{
				try {
					t.sleep(50000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
		
	}

}
