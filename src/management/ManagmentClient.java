package management;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.concurrent.ConcurrentLinkedQueue;

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

//	private BillingServer bs;

//	private AnalyticTaskComputing atc;

//	private RemoteBillingServerSecure rsbs;

	private ConcurrentLinkedQueue<String> events;

	public static void main(String[] args){
		Command c=null;
		String[] cmd=null;
		cf=new CommandFactory();
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		String line;
		try {
			while ((line = br.readLine()) != null) {
				try{
					cmd=line.split(" ");
					
					// TODO how do i know if  can run securecmmand? 
					c=cf.createCommand(cmd);
					//null is returned when the command is !print
					if(c==null){
						printAutomatic=true;
					}
					System.out.println(c.execute(cmd));
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
	public void notify(String s) {

	}

}
