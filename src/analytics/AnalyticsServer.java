package analytics;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URL;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.AbstractCollection;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;
import java.util.Set;
import java.util.Timer;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import billing.BillingServer;
import management.ClientInterface;
import Event.Event;
import ServerModel.FileHandler;
/**
 * Processes Events from the auction Server and forwards them to the Management Client, after
 * it has done several calculations.
 * 
 * @author Daniel Reichmann
 * @version 2014-02-16
 */
public class AnalyticsServer {
 
	private ConcurrentHashMap<String, ConcurrentHashMap<String, ClientInterface>> subscriptions; //Saves the SubscriptionId+Clientinterface, per Event 
	private LinkedBlockingQueue<Event> incomingEvents;
	private LinkedBlockingQueue<Event> dispatchedEvents; //Events which shall be sent to the user
	private EventHandler eh;
	private static int id = 0; //Saves all subsrciption IDs
	 
	/**
	 * Starts the EventHandler and the Timer to schedule the BidCount per Minute
	 * 
	 */
	public AnalyticsServer(){
		
		incomingEvents = new LinkedBlockingQueue<>();
		dispatchedEvents = new LinkedBlockingQueue<>();
		//Initialize EventHashMap
		subscriptions = new ConcurrentHashMap<>();
		//Now put every EventType in it
		for(String type : getEventTypes()){
			subscriptions.put(type, new ConcurrentHashMap<String, ClientInterface>());
//			System.out.println(type);
		}
		eh = new EventHandler(this);
		initRmi(new AnalyticTaskComputing(this));
		Thread t = new Thread(eh);
		t.start();
		Timer timer = new Timer();
		timer.schedule(new BidCountPerMinuteWatcher(this), 60*1000);
		
	}
	
	/**
	 * Puts an incoming Event into the Queue
	 *  
	 * @param e		Event which shall be processed.
	 */
	public void processEvent(Event e) {
		try {
			incomingEvents.put(e);
		} catch (InterruptedException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}
	 
	/**
	 * Subsribes a User with a specific Regex
	 * 
	 * @param regex		Regex which says which Event a User want to receive
	 * @param ci		ClientInterface which is used for CallBack
	 */
	public String subscribe(String regex, ClientInterface ci){
	
		//Iterate over all Keys
		Set<String> keyset = subscriptions.keySet();
		Iterator<String> it = keyset.iterator();
		Pattern pattern;
		try{
			pattern = Pattern.compile(regex);
		}
		catch(Exception e){
			System.out.println("INVALID PATTERN");
			return "Your pattern is invalid!";
		}
		boolean foundMatch = false;
		String subsid=id+"_"+pattern;
		while(it.hasNext()){
			String compare = it.next();
			System.out.println(compare);
			
			Matcher matcher = pattern.matcher(compare);
			if(matcher.matches()){
				foundMatch = true;
				subscriptions.get(compare).put(subsid, ci);
			}
		}
		if(foundMatch){
			id++;
			return subsid;
		}
			
		else
			return "No Matching Events for your pattern found";
	}
	
	/**
	 * Notifies Clients that new Events have occurred
	 */
	public void notifyClients(){
		while(!dispatchedEvents.isEmpty()){
			Event event = null;
			try {
				event = dispatchedEvents.take();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			//Get Map with Interfaces of the SubscriptionId/Clients who want notification
			ConcurrentHashMap<String, ClientInterface> cmap = subscriptions.get(event.getType());
			//Notify
			Set<String> clist = cmap.keySet();
			for (Iterator iterator = clist.iterator(); iterator.hasNext();) {
				ClientInterface clientInterface =cmap.get(iterator.next());
				try {
					clientInterface.notify(event);
				} catch (RemoteException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}
	public static void main(String[] args) {
		AnalyticsServer as = new AnalyticsServer();
		new AnalyticTaskComputing(as);
	}

	public LinkedBlockingQueue<Event> getIncomingEvents() {
		return incomingEvents;
	}

	public LinkedBlockingQueue<Event> getDispatchedEvents() {
		return dispatchedEvents;
	}



	public EventHandler getEventHandler() {
		return eh;
	}
	
	/**
	 * Returns a Array of all Events
	 * 
	 * @return String[] with all EventTypes
	 */
	public String[] getEventTypes(){
		String[] events = new String[] {"AUCTION_ENDED", "AUCTION_STARTED", "AUCTION_SUCCESS_RATIO","AUCTION_TIME_AVG","USER_LOGIN","USER_LOGOUT",
				"USER_DISCONNECTED","BID_PLACED","BID_OVERBID","BID_WON","USER_SESSIONTIME_MIN","USER_SESSIONTIME_MAX","USER_SESSIONTIME_AVG"
				,"BID_PRICE_MAX","BID_COUNT_PER_MINUTE"};
		return events;
	}
	
	 private static void initRmi(AnalyticTaskComputing atc){
		 try {
			 if (System.getSecurityManager() == null) {
					System.setSecurityManager(new SecurityManager());
				}
			 
			// neues Properties Objekt eerstellen
				Properties properties = new Properties();
				// neuen stream mit der messenger.properties Datei erstellen
				BufferedInputStream stream = new BufferedInputStream(new FileInputStream("Server.properties"));

				
				properties.load(stream);
			
				stream.close();
	            Registry registry = null;
	            try{
	            	System.out.println("Getting registry");
		            registry = LocateRegistry.createRegistry(Integer.parseInt(properties.getProperty("rmi.port")));
	            }catch( RemoteException e){
	            	System.out.println("Could not create registry");
	            	try{
	            		
	            		registry = LocateRegistry.getRegistry(properties.getProperty("rmi.registryURL"),Integer.parseInt(properties.getProperty("rmi.port")));
	            	}catch(Exception xe){//TODO Einschraenken
	            		System.out.println("Could not bind or locate Registry, stopping Programm");
	            		System.exit(370);
	            	}
	            }
	            if (registry == null){
	            	System.out.println("Could not bind or locate Registry, stopping Programm");
            		System.exit(370);
	            }
	            RemoteAnalyticsTaskComputing stub =
	                (RemoteAnalyticsTaskComputing) UnicastRemoteObject.exportObject(atc, 0);
	            registry.rebind(properties.getProperty("rmi.analyticsServer"), stub);
	            System.out.println("AnalyticServer bound");
	            

	            
	        }catch (Exception e){
	        	e.printStackTrace();
	        }
	 }
	 /**
	  * Iterates throug notification list and deletes specific notifications for one ID
	  * 
	  * @param subsId	NotificationID which shall be canceled
	  * @return
	  */
	public String unsubscribe(String subsId) {
		boolean canceled = false;
		//Iterator through map
		Iterator<String> it = subscriptions.keySet().iterator();
		while(it.hasNext()){
			ConcurrentHashMap<String, ClientInterface> clientPEvent = subscriptions.get(it.next());
			if(clientPEvent.remove(subsId)!=null)
				canceled = true;
		}
		if(canceled)
			return "Subscription " + subsId + "successfully canceled";
		else
			return "No subscription found";
		// TODO Auto-generated method stub
		
	}
	 
}
 
