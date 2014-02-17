package loadtest;


/**
 * Thread, which 
 * @author Michaela Lipovits
 * @version 2014
 */
public class CheckDuration implements Runnable{
	private long starttime;
	private FakeCli cli;
	private long min=1*60000;
	private Thread t;
	private long status;

	public CheckDuration(FakeCli cli, long starttime){
		this.cli=cli;
		this.starttime=starttime;
		t=new Thread(this);
		t.start();
		min=1*30;
	}
	@Override
	public void run() {
		while(cli.isClientAlive()){
			status=System.currentTimeMillis()-starttime;
			if(status>=min){
				cli.write("!end");
				cli.setClientsAlive(false);
				System.out.println("client ended");
			}
			else{
				System.out.println(status);
				try {
					t.sleep(50000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		
	}

}
