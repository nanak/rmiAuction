package management;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Serializable;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Properties;
import java.util.UUID;
import java.util.concurrent.ConcurrentLinkedQueue;

import rmi.InitRMI;
import Client.CLI;
import Client.UI;
import Event.Event;
import Exceptions.CommandIsSecureException;
import Exceptions.CommandNotFoundException;
import Exceptions.WrongInputException;
import Exceptions.WrongNumberOfArgumentsException;
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

	private CommandFactory commandFactory;

	private RemoteBillingServer billingServer;

	private RemoteAnalyticsTaskComputing analyticTaskComputing;

	private IRemoteBillingServerSecure billingServerSecure;
	
	private String uniqueID;

	private InitRMI ir;
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
	
	/**
	 * Constructor, which initializes all neccessary attributes and connections and sets the UI to the given one.
	 * @param ui Implementation of UI
	 */
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
			ui.outM(e.toString());
		}
	}

	/**
	 * Run, which waits for userinput and handles it.
	 */
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
							try{
								logout[0]="!logout";
								logout[1]=username;
								ui.outM((String)billingServerSecure.executeSecureCommand(commandFactory.createSecureCommand(logout),logout));
								billingServerSecure=null;
								username="";
							}catch(RemoteException | NullPointerException e){
								try {
									billingServer = (RemoteBillingServer) ir.lookup(billingIdentifier);
									ui.outM("ERROR: Connection to BillingServer lost. You have to login again!");
								} catch (NotBoundException | RemoteException  ex) {
									ui.outM("ERROR: BillingServer is not available right now. Retry after starting BillingServer");			 
								}	
							}
						}
						ui.outM("Management Client is shutting down!");
						secure=false;
						running=false;
						ir.unexport(this);
					}
					else if(cmd[0].equals("!print")){
						Iterator<Event> it = events.iterator();
						while(it.hasNext()){
							ui.outM(it.next().toString());
						}
					}
					else if(cmd[0].equals("!auto")){
						printAutomatic=true;
						Iterator<Event> it = events.iterator();
						while(it.hasNext()){
							ui.outM(it.next().toString());
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
								ui.outM(s);
							}
							catch(RemoteException e){
								try {
									analyticTaskComputing = (RemoteAnalyticsTaskComputing)ir.lookup(analyticsIdentifier);
									String s = analyticTaskComputing.unsubscribe(cmd[1]);
									ui.outM(s);
									
								} catch (NotBoundException| RemoteException  ex) {
									ui.outM("ERROR: AnalyticsServer not available right now. Retry after starting Analytics");
									
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
								ui.outM(analyticTaskComputing.subscribe(cmd[1], this));
							}
							catch(RemoteException e){
								try {
									ui.outM("INFO: Analytics seemed to have moved. Looking up");
									analyticTaskComputing = (RemoteAnalyticsTaskComputing) ir.lookup(analyticsIdentifier);
									ui.outM(analyticTaskComputing.subscribe(cmd[1], this));
									
								} catch (NotBoundException | RemoteException  ex) {
									ui.outM("ERROR: AnalyticsServer not available right now. Retry after starting Analytics");
									 
								}	
							}
							
						}
					}
					else if(secure==true){
						if(cmd[0].equals("!logout")){
							usernameLogout=new String[2];
							usernameLogout[0]=cmd[0];
							usernameLogout[1]=username;
							try{
								anwser=(String)billingServerSecure.executeSecureCommand(commandFactory.createSecureCommand(cmd),usernameLogout);
								ui.outM(anwser);
								username=""; 
								billingServerSecure=null;
								secure=false;
							}
							catch(RemoteException | NullPointerException e){
								try {
									billingServer = (RemoteBillingServer) ir.lookup(billingIdentifier);
									ui.outM("ERROR: Connection to BillingServer lost. You have to login again!");
								} catch (NotBoundException | RemoteException  ex) {
									ui.outM("ERROR: BillingServer not available right now. Retry after starting BillingServer");			 
								}	
							}
						}
						else{
							try{ 
								anwser=(String)billingServerSecure.executeSecureCommand(commandFactory.createSecureCommand(cmd),cmd);
								ui.outM(anwser);
							}
							catch(RemoteException | NullPointerException e){
								try {
									billingServer = (RemoteBillingServer) ir.lookup(billingIdentifier);
									ui.outM("ERROR: Connection to BillingServer lost. You have to login again!");
								} catch (NotBoundException | RemoteException  ex) {
									ui.outM("ERROR: BillingServer not available right now. Retry after starting BillingServer");			 
								}	
							}
						}
					}	
					else{	
						c= commandFactory.createCommand(cmd);
						if(cmd[0].equals("!login")){
							
							Login l = (Login)c;
							l.execute(cmd);

							try{ 
								billingServerSecure=billingServer.login(l);
								if(billingServerSecure == null){
									ui.outM("Wrong password!");
								}
								else{
									secure=true;
									username=cmd[1];
									ui.outM("Successfully logged in");
								}
							}
							catch(RemoteException | NullPointerException e){
								try {
									billingServer = (RemoteBillingServer) ir.lookup(billingIdentifier);
									ui.outM("ERROR: Connection to BillingServer lost. You have to login again!");
								} catch (NotBoundException | RemoteException  ex) {
									ui.outM("ERROR: BillingServer not available right now. Retry after starting BillingServer");			 
								}	
							}
						}
					}					
				}catch(WrongNumberOfArgumentsException | WrongInputException | CommandNotFoundException | CommandIsSecureException e){
					ui.outM(e.getMessage());
				}
			}
			br.close();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
//			e1.printStackTrace();
			
		}

	}
	private void initRMI(Properties properties){
		try {
			
			ir = new InitRMI(properties);
			ir.init();
			if(properties.getProperty("rmi.analyticsServer")==null || properties.getProperty("rmi.billingServer")==null){
				running=false;
				ui.out("Properties not sufficcient. Client shutting down. ");
				return;
			}
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
		} catch (IOException e) {
			System.out.println("ERROR: Problem loading Properties File: "+e.getMessage()+". Client shutting down.");
			running=false;
		} 
	}
	public void setRunning(boolean running){
		this.running=running;
	}
	public boolean getPrintAutomatic(){
		return printAutomatic;
	}
//	/**
//	 * initRMI if one name given
//	 * @param servername rmi Servername
//	 * @param server wich Servername (true if billing, false if analytic)
//	 */
//	private void initRMI(String servername, boolean server){
//		if(servername==null){
//			initRMI();
//			return;
//		}
//		try{
//			Properties properties = new Properties();
//			BufferedInputStream stream = new BufferedInputStream(new FileInputStream("Server.properties"));
//			
//			properties.load(stream);
//		
//			stream.close();
//			if(server){
//				properties.put("rmi.billingserver", servername);
//				
//			}else{
//				properties.put("rmi.analyticsServer", servername);
//			}
//			
//			
//			
//			initRMI (properties);
//		}catch (FileNotFoundException e) {
//			running=false;
//			System.out.println("Properties File doesn't exist. Client shutting down.");
//		}catch (IOException e) {
//			System.out.println("ERROR: Problem loading Properties File: "+e.getMessage()+". Client shutting down.");
//			running=false;
//		} 
//		
//		
//	}
//	private void initRMI(String analyticServerName, String billingServerName){
//
//		Properties properties = new Properties();
//		try{
//			BufferedInputStream stream = new BufferedInputStream(new FileInputStream("Server.properties"));
//			
//			properties.load(stream);
//		
//			stream.close();
//		}catch (FileNotFoundException e) {
//			running=false;
//			System.out.println("Properties File doesn't exist. Client shutting down.");
//			return;
//		}catch (IOException e) {
//			System.out.println("ERROR: Problem loading Properties File: "+e.getMessage()+". Client shutting down.");
//			running=false;
//			return;
//		} 
//		if(analyticServerName == null){
//			if(billingServerName==null){
//				initRMI();
//				return;
//			}
//			initRMI(billingServerName,true);
//			return;
//		}
//		if(billingServerName==null){
//			initRMI(analyticServerName,false);
//			return;
//		}
//		Properties p = new Properties();
//		p.put("rmi.billingserver", billingServerName);
//		p.put("rmi.analyticsServer", analyticServerName);
//		initRMI(p);
//	}
	/**
	 * RMI Initialisation
	 */
	private void initRMI(){
		try{
			Properties properties = new Properties();
			BufferedInputStream stream = new BufferedInputStream(new FileInputStream("Server.properties"));
			
			properties.load(stream);
		
			stream.close();
			initRMI (properties);
		}catch (FileNotFoundException e) {
			running=false;
			System.out.println("Properties File doesn't exist. Client shutting down.");
		}catch (IOException e) {
			System.out.println("ERROR: Problem loading Properties File: "+e.getMessage()+". Client shutting down.");
			running=false;
		} 
	}
//		if (System.getSecurityManager() == null) {
//			System.setSecurityManager(new SecurityManager());
//		}
	// neues Properties Objekt erstellen


		

}
