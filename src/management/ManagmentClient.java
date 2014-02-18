package management;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Serializable;
import java.rmi.AccessException;
import java.rmi.AlreadyBoundException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Properties;
import java.util.UUID;
import java.util.concurrent.ConcurrentLinkedQueue;

import analytics.AnalyticTaskComputing;
import analytics.RemoteAnalyticsTaskComputing;
import billing.IRemoteBillingServerSecure;
import billing.RemoteBillingServer;
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
public class ManagmentClient implements Serializable, ClientInterface, Runnable {

	private static boolean printAutomatic;

	private UI ui;

	private static CommandFactory cf;

	private static RemoteBillingServer bs;

	private RemoteAnalyticsTaskComputing atc;

	private static IRemoteBillingServerSecure rsbs;
	
	private String uniqueID;
//	private ClientInterface ci;

	private ConcurrentLinkedQueue<Event> events;
	private boolean running;
	private Command c;
	private boolean secure;
	private BufferedReader br;
	private String[] logout;
	String username= "";
	
	public ManagmentClient(UI ui){
		this.ui=ui;
		events = new ConcurrentLinkedQueue<Event>();
		cf=new CommandFactory();
		running=true;
		c=null;
		br = new BufferedReader(new InputStreamReader(System.in));
		secure=false;
		logout=new String[1];
		logout[0]="!logout";
		uniqueID = UUID.randomUUID().toString();
		new Thread(this).start();
		initRMI();
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
				// TODO get username here
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
							System.out.println(rsbs.executeSecureCommand(cf.createSecureCommand(logout),logout));
						}
						ui.out("Management Client is shutting down!");
						secure=false;
						running=false;
					}
					else if(cmd[0].equals("!login")){
						c= cf.createCommand(cmd);
						ui.out((String) c.execute(cmd));
						rsbs=bs.login((Login)c);
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
						if(cmd.length!=2){
							throw new IllegalNumberOfArgumentsException();
						}
						
						
						// TODO UNSUBSCRIBE
						String s = atc.unsubscribe(cmd[1]);
						ui.outln(s);
					}
					else if(cmd[0].equals("!subscribe")){
						if(cmd.length!=2){
							throw new IllegalNumberOfArgumentsException();
						}
						// TODO subscribe
						System.out.println(atc.subscribe(cmd[1], this));
					}
					else if(secure==true){
						if(cmd[0].equals("!logout")){
							secure=false;
						}
						System.out.println(cmd.length);
						System.out.println(rsbs.toString());
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
	/**
	 * RMI Initialisation
	 */
	private void initRMI(){
//		if (System.getSecurityManager() == null) {
//			System.setSecurityManager(new SecurityManager());
//		}
	// neues Properties Objekt erstellen
		Properties properties = new Properties();

		
		try {
			// neuen stream mit der messenger.properties Datei erstellen
			BufferedInputStream stream = new BufferedInputStream(new FileInputStream("ManagementClient.properties"));
			properties.load(stream);
			stream.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	
		Registry registry = null;
		try {
			registry = LocateRegistry.getRegistry(
					properties.getProperty("rmi.registryURL"),
					Integer.parseInt(properties.getProperty("rmi.port")));
			System.out.println(properties.getProperty("rmi.registryURL"));
			 bs = (RemoteBillingServer) registry
					.lookup(properties.getProperty("rmi.billingServer"));
			 atc = (RemoteAnalyticsTaskComputing) registry.lookup(properties.getProperty("rmi.analyticsServer"));
			 
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NotBoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NumberFormatException nfe) {
			System.out.println("Properties File Fehlerhaft");
		} 
		//Try to bind ManagementClient to registry for Callback
		try{

			if(registry != null)
				System.out.println("Registry not null");
			ClientInterface ci = (ClientInterface) UnicastRemoteObject.exportObject(this,0);
			registry.bind(uniqueID, ci);
			
		}
		catch(AlreadyBoundException e){
			System.out.println("Client is already bound to registry");
		} catch (AccessException e) {
			System.err.println("Access violation");
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
