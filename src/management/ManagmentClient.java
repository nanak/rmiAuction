package management;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.NoSuchElementException;
import java.util.concurrent.ConcurrentLinkedQueue;

import analytics.AnalyticTaskComputing;
import billing.BillingServer;
import billing.RemoteBillingServerSecure;
import Client.CLI;
import Client.UI;
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

	private UI ui;

	private static CommandFactory cf;

	private static BillingServer bs;

	private AnalyticTaskComputing atc;

	private static RemoteBillingServerSecure rsbs;

	private ConcurrentLinkedQueue<String> events;
	private boolean running;
	private Command c;
	private boolean secure;
	private BufferedReader br;
	private String[] logout;
	
	public ManagmentClient(UI ui){
		// TODO get to know the billing server!!! Creating a new one here is just for now!!
		this.bs=new BillingServer();
		this.ui=ui;
		cf=new CommandFactory();
		running=true;
		c=null;
		br = new BufferedReader(new InputStreamReader(System.in));
		secure=false;
		logout=new String[1];
		logout[0]="!logout";
		new Thread(this).start();
	}

	public static void main(String[] args){
		new ManagmentClient(new CLI());
		
	}


	/**
	 * @see management.ClientInterface#notify(java.lang.String)
	 */
	public void notify(Event e) {

	}

	@SuppressWarnings("unchecked")
	@Override
	public void run() {
		String[] cmd=null;
		String line;
		String anwser;
		try {
			while (running) {
				try{
					line=ui.readln();
				}catch(NoSuchElementException e){
					continue;
				}
				try{
					cmd=line.split(" ");
					if(line.equals("!end")){
						if(secure==true){
							System.out.println(rsbs.executeSecureCommand(cf.createSecureCommand(logout),logout));
						}
						ui.out("Management Client is shutting down!");
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
						anwser=rsbs.executeSecureCommand(cf.createSecureCommand(cmd),cmd);
						ui.out(anwser);
					}	
					else{	
						c=cf.createCommand(cmd);
						//null is returned when the command is !print
						if(c==null){
							printAutomatic=true;
						}else{
							anwser=(String) c.execute(cmd);
							ui.out(anwser);
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
