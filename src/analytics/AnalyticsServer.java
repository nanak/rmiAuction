package analytics;

import java.util.AbstractCollection;
import java.util.ArrayList;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.LinkedBlockingQueue;

import management.ClientInterface;
import Event.Event;
import ServerModel.FileHandler;

public class AnalyticsServer {
 
	private ConcurrentHashMap<Event, ConcurrentLinkedQueue<ClientInterface>> subscriptions; //Saves 
	private ConcurrentHashMap<String, ArrayList<Event>> auctionEvents, userEvents, bidEvents, StatisticEvents; 
	private LinkedBlockingQueue<Event> incomingEvents;
	private LinkedBlockingQueue<Event> dispatchedEvents; //Events which shall be sent to the user
	 
	public LinkedBlockingQueue<Event> getIncomingEvents() {
		return incomingEvents;
	}

	public void setIncomingEvents(LinkedBlockingQueue<Event> incomingEvents) {
		this.incomingEvents = incomingEvents;
	}

	public LinkedBlockingQueue<Event> getDispatchedEvents() {
		return dispatchedEvents;
	}

	public void setDispatchedEvents(LinkedBlockingQueue<Event> dispatchedEvents) {
		this.dispatchedEvents = dispatchedEvents;
	}

	private FileHandler fileHandler;
	 
	public void processEvent(Event e) {
	 
	}
	 
	public static void main(String[] args) {
	 
	}

	 
}
 
