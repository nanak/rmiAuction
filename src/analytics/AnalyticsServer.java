package analytics;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;

import billing.BillingServer;
import billing.RemoteBillingServerSecure;
import management.ClientInterface;
import ServerModel.FileHandler;

public class AnalyticsServer {
 
	private ConcurrentHashMap<Event, ConcurrentLinkedQueue<ClientInterface>> subscriptions;
	 
	private FileHandler fileHandler;
	 
	public void processEvent(Event e) {
	 
	}
	 
	public static void main(String[] args) {
	 
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
 
