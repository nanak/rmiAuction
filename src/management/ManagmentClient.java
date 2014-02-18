package management;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Iterator;
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
	
	private ClientInterface ci;

	private ConcurrentLinkedQueue<Event> events;
	private boolean running;
	private Command c;
	private boolean secure;
	private BufferedReader br;
	private String[] logout;
	
	public ManagmentClient(UI ui){
		// TODO get to know the billing server!!! Creating a new one here is just for now!!
		//this.bs=new BillingServer();
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
		if(printAutomatic==false){
			events.add(e);
		}
		else{
			ui.out(e.toString());
		}
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
					else if(cmd[0].equals("!print")){
						Iterator<Event> it = events.iterator();
						while(it.hasNext()){
							ui.out(it.next().toString());
						}
					}
					else if(cmd[0].equals("!auto")){
						printAutomatic=true;
						Iterator<Event> it = events.iterator();
						while(it.hasNext()){
							ui.out(it.next().toString());
						}
					}
					else if(cmd[0].equals("!hide")){
						printAutomatic=false;
					}
					else if(cmd[0].equals("!unsubscribe")){
						int id;
						if(cmd.length!=2){
							throw new IllegalNumberOfArgumentsException();
						}
						try{
							id=Integer.parseInt(cmd[1]);
						}catch(NumberFormatException e){
							throw new WrongInputException();
						}
						ui.out("subscription "+id+" terminated");
						//atc.unubscribe(cmd[1], cit);
					}
					else if(cmd[0].equals("!subscribe")){
						if(cmd.length!=2){
							throw new IllegalNumberOfArgumentsException();
						}
						//atc.subscribe(cmd[1], cit);
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
						anwser=(String) c.execute(cmd);
						ui.out(anwser);
					}					
				}catch(IllegalNumberOfArgumentsException | WrongInputException | CommandNotFoundException | CommandIsSecureException e){
					ui.out(e.getMessage());
				}
			}
			br.close();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
			
		}
		
	}

}
