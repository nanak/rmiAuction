package management;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Serializable;
import java.net.ConnectException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.HashSet;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Properties;
import java.util.UUID;
import java.util.concurrent.ConcurrentLinkedQueue;

import javax.print.attribute.HashAttributeSet;

import rmi.InitRMI;
import Client.CLI;
import Client.UI;
import Event.Event;
import Exceptions.CommandIsSecureException;
import Exceptions.CommandNotFoundException;
import Exceptions.WrongNumberOfArgumentsException;
import Exceptions.WrongInputException;
import analytics.RemoteAnalyticsTaskComputing;
import billing.IRemoteBillingServerSecure;
import billing.RemoteBillingServer;


/**
 * Class ManagementClient which creates a new ManagmentClient.
 * The Userinput is read and the manalyticTaskComputinghig Commands are executed.
 * 
 * @author Michaela Lipovits
 * @version 20140211
 */
public class ManagmentClient implements Serializable, ClientInterface, Runnable {

	private static boolean printAutomatic;

	private UI ui;

	private static CommandFactory commandFactory;

	private static RemoteBillingServer billingServer;

	private RemoteAnalyticsTaskComputing analyticTaskComputing;

	private static IRemoteBillingServerSecure billingServerSecure;
	
	private String uniqueID;

	InitRMI ir;
	private ConcurrentLinkedQueue<Event> events;
	private boolean running;
	private Command c;
	private boolean secure;
	private BufferedReader br;
	private String[] logout;
	private String username= "";
	private String[] usernameLogout;

	private String analyticsIdentifier;

	private String billingIdentifier;
	
	public ManagmentClient(UI ui){
		this.ui=ui;
		events = new ConcurrentLinkedQueue<Event>();
		commandFactory=new CommandFactory();
		running=true;
		c=null;
		br = new BufferedReader(new InputStreamReader(System.in));
		secure=false;
		logout=new String[2];
		uniqueID = UUID.randomUUID().toString();
		initRMI();
		try {
			Thread.sleep(500);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
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
				ui.outln("\n"+username+"> ");
				try{
					line=ui.readln();
				}catch(NoSuchElementException e){
					continue;
				}
				try{
					cmd=line.split(" ");
					if(line.equals("!end")){
						if(secure==true){
							logout[0]="!logout";
							logout[1]=username;
							System.out.println(billingServerSecure.executeSecureCommand(commandFactory.createSecureCommand(logout),logout));
							billingServerSecure=null;
							username="";
							ir.unexport(this);
						}
						ui.out("Management Client is shutting down!");
						secure=false;
						running=false;
					}
					else if(cmd[0].equals("!login")){
						System.out.println("here");
						c= commandFactory.createCommand(cmd);
						ui.out((String) c.execute(cmd));
						Login l = (Login)c;
						System.err.println(l.getPw());
						billingServerSecure=billingServer.login(l);
						if(billingServerSecure == null){
							ui.out("Wrong password!");
							running=false;
						}
						else{
							secure=true;
							ui.out("Successfully logged in");
						}
								
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
							throw new WrongNumberOfArgumentsException("Usage: !unsubscribe <subscriptionID>");
						}
						else{
							try{
								String s = analyticTaskComputing.unsubscribe(cmd[1]);
								ui.outln(s);
							}
							catch(RemoteException e){
								try {
									analyticTaskComputing = (RemoteAnalyticsTaskComputing)ir.lookup(analyticsIdentifier);
									String s = analyticTaskComputing.unsubscribe(cmd[1]);
									ui.outln(s);
									
								} catch (NotBoundException| RemoteException  ex) {
									ui.out("ERROR: AnalyticsServer not available right now. Retry after starting Analytics");
									
								}
								
							}
							
						}
					}
					else if(cmd[0].equals("!subscribe")){
						if(cmd.length!=2){
							throw new WrongNumberOfArgumentsException("Usage: !subscribe <filterRegex>");
						}
						else{
							try{
								ui.out(analyticTaskComputing.subscribe(cmd[1], this));
							}
							catch(RemoteException e){
								try {
									analyticTaskComputing = (RemoteAnalyticsTaskComputing) ir.lookup(analyticsIdentifier);
									ui.out(analyticTaskComputing.subscribe(cmd[1], this));
									
								} catch (NotBoundException | RemoteException  ex) {
									ui.out("ERROR: AnalyticsServer not available right now. Retry after starting Analytics");
									
								}
								
							}
							
						}
					}
					else if(secure==true){
						if(cmd[0].equals("!logout")){
							usernameLogout=new String[2];
							usernameLogout[0]=cmd[0];
							usernameLogout[1]=username;
							anwser=(String)billingServerSecure.executeSecureCommand(commandFactory.createSecureCommand(cmd),usernameLogout);
							ui.out(anwser);
							username=""; 
							billingServerSecure=null;
							secure=false;
						}
						else{
							anwser=(String)billingServerSecure.executeSecureCommand(commandFactory.createSecureCommand(cmd),cmd);
							ui.out(anwser);
						}
					}	
					else{	
						c=commandFactory.createCommand(cmd);
						anwser=(String) c.execute(cmd);
						ui.out(anwser);
					}					
				}catch(WrongNumberOfArgumentsException | WrongInputException | CommandNotFoundException | CommandIsSecureException e){
					ui.out(e.getMessage());
				}
			}
			br.close();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
			
		}

	}
	/**
	 * RMI Initialisation
	 */
	private void initRMI(){
//		if (System.getSecurityManager() == null) {
//			System.setSecurityManager(new SecurityManager());
//		}
	// neues Properties Objekt erstellen


		try {
			Properties properties = new Properties();
			BufferedInputStream stream = new BufferedInputStream(new FileInputStream("Server.properties"));
			
			properties.load(stream);
		
			stream.close();
			ir = new InitRMI(properties);
			ir.init();
			analyticsIdentifier = properties.getProperty("rmi.analyticsServer");
			System.out.println("Getting server: " + analyticsIdentifier );
			billingIdentifier = properties.getProperty("rmi.billingServer");
			billingServer= (RemoteBillingServer) ir.lookup(properties.getProperty("rmi.billingServer"));
			analyticTaskComputing = (RemoteAnalyticsTaskComputing) ir.lookup(properties.getProperty("rmi.analyticsServer"));
			ir.rebind(this,uniqueID);
			
		} catch (RemoteException e) {
			
			running=false;
			e.printStackTrace();
		} catch (NotBoundException e) {
			System.out.println("ERROR: Problem binding Server: "+e.getMessage()+". Client shutting down.");
			running=false;
		} catch (NumberFormatException nfe) {
			System.out.println("Properties File not well formatet. Client shutting down.");
			running=false;
		} catch (FileNotFoundException e) {
			running=false;
			System.out.println("Properties File doesn't exist. Client shutting down.");
		} catch (IOException e) {
			System.out.println("ERROR: Problem loading Properties File: "+e.getMessage()+". Client shutting down.");
			running=false;
		} 
	}
	public void setRunning(boolean running){
		this.running=running;
	}

}
