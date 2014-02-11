package analytics;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;

import management.ClientInterface;

import ServerModel.FileHandler;

public class AnalyticsServer {
 
	private ConcurrentHashMap<Event, ConcurrentLinkedQueue<ClientInterface>> subscriptions;
	 
	private FileHandler<K,T> fileHandler_K,T_;
	 
	public void processEvent(Event e) {
	 
	}
	 
	public static void main(String[] args) {
	 
	}
	 
}
 
