package management;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.concurrent.ConcurrentLinkedQueue;

import billing.BillingServer;
import billing.RemoteBillingServerSecure;
import Event.Event;
import Exceptions.*;

//import analytics.AnalyticTaskComputing;
//import billing.RemoteBillingServerSecure;

/**
 * Class ManagementClient which creates a new ManagmentClient.
 * The Userinput is read and the matchig Commands are executed.
 * 
 * @author Michaela Lipovits
 * @version 20140211
 */
public class ManagmentClient implements ClientInterface, Runnable {

	private static boolean printAutomatic;

//	private UI ui;

	private static CommandFactory cf;

	private static BillingServer bs;

//	private AnalyticTaskComputing atc;

	private static RemoteBillingServerSecure rsbs;

	private ConcurrentLinkedQueue<String> events;
	private boolean running;
	private Command c;
	private boolean secure;
	private BufferedReader br;
	private String[] logout;
	
	public ManagmentClient(){
		// TODO get to know the billing server!!! Creating a new one here is just for now!!
		this.bs=new BillingServer();
		cf=new CommandFactory();
		running=true;
		c=null;
		br = new BufferedReader(new InputStreamReader(System.in));
		secure=false;
		logout=new String[1];
		logout[0]="!logout";
	}

	public static void main(String[] args){
		new Thread(new ManagmentClient()).start();
		
	}


	/**
	 * @see management.ClientInterface#notify(java.lang.String)
	 */
	public void notify(Event e) {

	}

	@Override
	public void run() {
		String[] cmd=null;
		String line;
		try {
			while (running) {
				line = br.readLine();
				try{
					cmd=line.split(" ");
					if(line.equals("!end")){
						if(secure==true){
							System.out.println(rsbs.executeSecureCommand(cf.createSecureCommand(logout),logout));
						}
						System.out.println("Management Client is shutting down!");
						secure=false;
						running=false;
					}
					else if(cmd[0].equals("!login")){
						rsbs=bs.login((Login) cf.createCommand(cmd));
						c=cf.createCommand(cmd);
						System.out.println(c.execute(cmd));
						secure=true;
					}
					else if(secure==true){
						if(cmd[0].equals("!logout")){
							secure=false;
						}
						System.out.println(rsbs.executeSecureCommand(cf.createSecureCommand(cmd),cmd));
					}	
					else{	
						c=cf.createCommand(cmd);
						//null is returned when the command is !print
						if(c==null){
							printAutomatic=true;
						}else{
							System.out.println(c.execute(cmd));
						}
					}					
				}catch(IllegalNumberOfArgumentsException | WrongInputException | CommandNotFoundException | CommandIsSecureException e){
					System.err.println(e.getMessage());
				}
			}
			br.close();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
			
		}
		
	}

}
