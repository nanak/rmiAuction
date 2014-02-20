package loadtest2;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;


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
	private BufferedReader in;
	private String line;
	private long endtime;

	public CheckDuration(FakeCli cli, long starttime){
		this.cli=cli;
		this.starttime=starttime;
		in=new BufferedReader(new InputStreamReader(System.in));
		t=new Thread(this);
		t.start();
	}
	@Override
	public void run() {
		while(cli.isClientAlive()){
			status=System.currentTimeMillis()-starttime;
			endtime=min+status/60000;
			try {
				line=in.readLine();
			} catch (IOException e1) {
			}
			if(status>=min){
				cli.write("!end");
				cli.setClientsAlive(false);
				System.out.println("client ended. time passed:"+endtime);
			}else if(!line.equals("")){
				cli.write("!end");
				cli.setClientsAlive(false);
				System.out.println("client ended. time passed:"+endtime);
			}
			else{
				try {
					t.sleep(500);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
		
	}

}
