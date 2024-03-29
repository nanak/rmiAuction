package analytics;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.rmi.AlreadyBoundException;
import java.rmi.NoSuchObjectException;
import java.rmi.RemoteException;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Properties;
import java.util.Set;
import java.util.Timer;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import event.AuctionEnded;
import event.Event;
import management.ClientInterface;
import rmi.InitRMI;
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
	private InitRMI ir; //RMI Stub for export/unexport
	private static int id = 0; //Saves all subsrciption IDs
	private AnalyticTaskComputing remoteTask;
	private Timer bidTimer; //Timer for BidCountPerMinute
	 
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
		}
		eh = new EventHandler(this);
		remoteTask = new AnalyticTaskComputing(this);
		if(initRmi(remoteTask)){
			Thread t = new Thread(eh);
			t.start();
			bidTimer = new Timer();
			bidTimer.schedule(new BidCountPerMinuteWatcher(this), 60*1000,60*1000);
		}
	}
	/**
	 * Closes all open tasks
	 * and stops the Timer.
	 */
	public void shutdown(){
		System.out.println("Server ending!");		//If Enter Button pressed, Server will end
		try {
			ir.unexport(remoteTask);
		} catch (NoSuchObjectException e) {
			System.out.println("Could not unexport object");
		}
		bidTimer.cancel();
		bidTimer.purge();
		System.out.println("Timer stopped");
		eh.setActive(false);
		//Push Event for shutdown
		processEvent(new AuctionEnded(null, null, 0, 0));
		System.out.println("Server end");
		
	}
	
	/**
	 * Puts an incoming Event into the Queue so the EventHandler can process it
	 * 
	 *  
	 * @param e		Event which shall be processed.
	 */
	public void processEvent(Event e) {
		try {
			incomingEvents.put(e);
		} catch (InterruptedException e1) {
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
	
		//Delete ' or " from regex
		regex = regex.replaceAll("\"", "");
		regex = regex.replaceAll("\'", "");
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
		String subsid=id+"";
		while(it.hasNext()){
			String compare = it.next();			
			Matcher matcher = pattern.matcher(compare);
			if(matcher.matches()){
				foundMatch = true;
				subscriptions.get(compare).put(subsid, ci);
			}
		}
		if(foundMatch){
			id++;
			return "Created subscription with ID " + subsid + " with filter "+regex;
		}
			
		else
			return "No Matching Events for your pattern found";
	}
	 /**
	  * Iterates through notification list and deletes specific notifications for one ID
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
		
	}
	 
	/**
	 * Notifies Clients that new Events have occurred.
	 * Removes a Client from the list, if he is not reachable anymore
	 */
	public void notifyClients(){
		while(!dispatchedEvents.isEmpty()){
			Event event = null;
			try {
				event = dispatchedEvents.take();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			//Get Map with Interfaces of the SubscriptionId/Clients who want notification
			ConcurrentHashMap<String, ClientInterface> cmap = subscriptions.get(event.getType());
			
			//Notify
			Set<String> clist = cmap.keySet();
			HashSet<ClientInterface> notifiedClients = new HashSet<>();
			HashSet<String> remove = new HashSet();
			int i = 0;
			Iterator<String> iterator = clist.iterator(); 
			while(iterator.hasNext()){
				String a = iterator.next();
				ClientInterface clientInterface =cmap.get(a);
				try {
					if(notifiedClients.add(clientInterface))
						clientInterface.notify(event);
				} catch (RemoteException e) {
					remove.add(a);
					System.out.println("Client Not Available Anymore; Removing him");
				}
			}
			Iterator<String> it = remove.iterator();
			//Remove all Unavailable Clients
			while(it.hasNext()){
				String rem = it.next();
				Iterator<String> iter = subscriptions.keySet().iterator();
				while(iter.hasNext()){
					subscriptions.get(iter.next()).remove(rem);
				}
			}
				

		}
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
	/**
	 * Initialieses the RMI Connection and exports the AnalyticsServer into the registry
	 * 
	 * @param atc	AnalyticsTaskComputing which is exported into the registry
	 * @param analyticServerName RMIname for the analyticserver
	 * @return true if successfull
	 */
	 private boolean initRmi(AnalyticTaskComputing analytics, String analyticServerName) throws RemoteException{
		 try{
		 Properties properties = new Properties();
		 File f = new File("registry.properties");
			if(!f.exists()){
					System.out.println("Properties File doesn't exist. Server shutting down.");
					return false;
			}
				
			// neuen stream mit der messenger.properties Datei erstellen
			BufferedInputStream stream = new BufferedInputStream(new FileInputStream("registry.properties"));
			
			properties.load(stream);
		
			stream.close();
		 if(analyticServerName == null){
			 return initRmi(analytics);
		 }
		 properties.put("rmi.analyticsServer", analyticServerName);
		 ir = new InitRMI(properties);
			ir.init();
			ir.rebind(analytics, properties.getProperty("rmi.analyticsServer"));
         System.out.println("AnalyticsServer bound");
		 }
		 catch(Exception e){
			 System.out.println("Could not bind Analyticserver. Shutting down");
			 return false;
		 }
		 
		 return true;
	 }
	
	/**
	 * Initialieses the RMI Connection and exports the AnalyticsServer into the registry
	 * 
	 * @param atc	AnalyticsTaskComputing which is exported into the registry
	 * @return true if successfull
	 */
	 private boolean initRmi(AnalyticTaskComputing analytics){
		 try {
			 Properties properties = new Properties();
			 //Sicherstellen dass registry.properties existiert
			 File f = new File("registry.properties");
				if(!f.exists()){
						System.out.println("Properties File doesn't exist. Server shutting down.");
						return false;
				}
					
				// neuen stream mit der messenger.properties Datei erstellen
				BufferedInputStream stream = new BufferedInputStream(new FileInputStream("registry.properties"));
				
				properties.load(stream);
			
				stream.close();
				
				return initRmi(analytics, properties.getProperty("rmi.analyticsServer"));
	            
	        }catch (Exception e){
	        	 System.out.println("Could not bind Analyticserver. Shutting down");
				 return false;
	        }
	 }

}
 
