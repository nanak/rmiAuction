package analytics;

import java.util.ArrayList;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;

import management.ClientInterface;
import ServerModel.FileHandler;

public class AnalyticsServer {
 
	private ConcurrentHashMap<Event, ConcurrentLinkedQueue<ClientInterface>> subscriptions; //Saves 
	private ConcurrentHashMap<String, ArrayList<Event>> auctionEvents, userEvents, bidEvents, StatisticEvents; 
	
	 
	private FileHandler fileHandler;
	 
	public void processEvent(Event e) {
	 
	}
	 
	public static void main(String[] args) {
	 
	}
	 
}
 
