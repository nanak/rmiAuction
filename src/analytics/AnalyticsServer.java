package analytics;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.AbstractCollection;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.Timer;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
 
	private ConcurrentHashMap<String, ConcurrentLinkedQueue<ClientInterface>> subscriptions; //Saves the Clients, per Event
	private LinkedBlockingQueue<Event> incomingEvents;
	private LinkedBlockingQueue<Event> dispatchedEvents; //Events which shall be sent to the user
	private EventHandler eh;
	 
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
			subscriptions.put(type, new ConcurrentLinkedQueue<ClientInterface>());
//			System.out.println(type);
		}
		eh = new EventHandler(this);
		
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
	public void subscribe(String regex, ClientInterface ci){
		//Iterate over all Keys
		Set<String> keyset = subscriptions.keySet();
		Iterator<String> it = keyset.iterator();
		Pattern pattern;
		try{
			pattern = Pattern.compile(regex);
		}
		catch(Exception e){
			System.out.println("INVALID PATTERN");
			return;
		}
		
		while(it.hasNext()){
			String compare = it.next();
			System.out.println(compare);
			
			Matcher matcher = pattern.matcher(compare);
			if(matcher.matches()){
				subscriptions.get(compare).add(ci);
			}
		}
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
			//Get List with Interfaces of the Clients who want notification
			ConcurrentLinkedQueue<ClientInterface> clist = subscriptions.get(event.getType());
			//Notify
			
			for (Iterator iterator = clist.iterator(); iterator.hasNext();) {
				ClientInterface clientInterface = (ClientInterface) iterator
						.next();
				clientInterface.notify(event);
			}
		}
	}
	public static void main(String[] args) {
	 
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
	            AnalyticTaskComputing stub =
	                (AnalyticTaskComputing) UnicastRemoteObject.exportObject(atc, 0);
	            Registry registry = LocateRegistry.getRegistry();
	            registry.rebind(AnalyticTaskComputing.SERVERNAME, stub);
	            System.out.println("AnalyticServer bound");
	            

	            
	        }catch (Exception e){
	        	//TODO Handling
	        }
	 }
	 
}
 
