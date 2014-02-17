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
public class ManagmentClient implements ClientInterface {

	private static boolean printAutomatic;

//	private UI ui;

	private static CommandFactory cf;

	private static BillingServer bs;

//	private AnalyticTaskComputing atc;

	private static RemoteBillingServerSecure rsbs;

	private ConcurrentLinkedQueue<String> events;
	
	public ManagmentClient(){
		this.bs=new BillingServer();
	}

	public static void main(String[] args){
		// TODO get to know the billing server!!! Creating a new one here is just for now!!
		Command c=null;
		String[] cmd=null;
		cf=new CommandFactory();
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		boolean secure=false;
		String line;
		try {
			while ((line = br.readLine()) != null) {
				try{
					cmd=line.split(" ");
					if(cmd[0].equals("!login")){
						rsbs=bs.login((Login) cf.createCommand(cmd));
						secure=true;
					}
					else if(secure==true){
						rsbs.executeSecureCommand(cf.createSecureCommand(cmd),cmd);
					}	
					//null is returned when the command is !print
					else if(c==null){
						printAutomatic=true;
					}
					else{
						if(cmd[0].equals("!logout")){
							secure=false;
						}
						c=cf.createCommand(cmd);
						System.out.println(c.execute(cmd));
					}					
				}catch(IllegalNumberOfArgumentsException | WrongInputException | CommandNotFoundException e){
					e.printStackTrace();
					//System.err.println(e.getMessage());
				}
			}
			br.close();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
			
		}
	}


	/**
	 * @see management.ClientInterface#notify(java.lang.String)
	 */
	public void notify(Event e) {

	}

}
