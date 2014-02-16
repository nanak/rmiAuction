package analytics;

import java.util.AbstractCollection;
import java.util.ArrayList;
import java.util.Timer;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.LinkedBlockingQueue;

import management.ClientInterface;
import Event.Event;
import ServerModel.FileHandler;

public class AnalyticsServer {
 
	private ConcurrentHashMap<Event, ConcurrentLinkedQueue<ClientInterface>> subscriptions; //Saves the Clients 
	private LinkedBlockingQueue<Event> incomingEvents;
	private LinkedBlockingQueue<Event> dispatchedEvents; //Events which shall be sent to the user
	private EventHandler eh;
	 
	public AnalyticsServer(){
		incomingEvents = new LinkedBlockingQueue<>();
		dispatchedEvents = new LinkedBlockingQueue<>();
		eh = new EventHandler(this);
		Thread t = new Thread(eh);
		t.start();
		Timer timer = new Timer();
		timer.schedule(new BidCountPerMinuteWatcher(this), 60*1000);
		
	}
	
	 
	public void processEvent(Event e) {
		try {
			incomingEvents.put(e);
		} catch (InterruptedException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
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
	 
}
 
