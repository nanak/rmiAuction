package server;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.rmi.AccessException;
import java.rmi.AlreadyBoundException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Properties;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import Event.Event;
import analytics.AnalyticTaskComputing;
import billing.BillingServer;
import billing.BillingServerSecure;
import connect.Notifier;
import connect.NotifierFactory;
import model.*;

/**
 * The main server with all functionalities and the user data.
 * Has a request method which responds on every request from the client.
 * @author Tobias Schuschnig <tschuschnig@student.tgm.ac.at>
 * @version 2013-01-05
 */
public class Server {

	private int tcpPort;
	private ConcurrentHashMap<String, User> user; //Map for getting User
	private ConcurrentHashMap<Integer, Auction> auction; //Map of auctions with ID as identifier
	private AuctionHandler ahandler;
	private RequestHandler rhandler; 
	private Notifier udp;
	private BillingServerSecure bss;
	private AnalyticTaskComputing atc;
	private boolean active;
	
	/**
	 * The standard konstructor where are all attributes are set up and the attributes are
	 * initialised.
	 */
	public Server() {
		rmiInit();
		user= new ConcurrentHashMap<String, User>(); //Only way to get ConcurrendHashSet
		active = true;
		auction=new ConcurrentHashMap<Integer, Auction>();
		ahandler = new AuctionHandler(this);
		rhandler = new RequestHandler();
		udp = NotifierFactory.getUDPNotifer();
		Thread athread = new Thread();
		athread.setPriority(Thread.MIN_PRIORITY);
		new Thread(ahandler).start();
	}

	/**
	 * This method receives all requests of the client
	 * @param message contains every parameters for the work step
	 * @return result of the operation which is handed over to the client via TCP.
	 */
	public String request(Message message) {
		return rhandler.execute(message, this);
	}
	
	/**
	 * In this method the notify method of the class UDPNotifiers is called. There the 
	 * message is forwarded via UDP to the correct clients.
	 * @param al contains the users which should receive the message
	 * @param message the message which is sended to the client.
	 */
	public void notify(ArrayList<User> al, String message) {
		udp.notify(al,message);
		System.out.println(message); //TODO only for testing after that delete
	}
	
	/**
	 * Notifies the analyticsServer of new Events
	 * @param e
	 */
	public void notify(Event e){
		atc.processEvent(e);
	}
	

	
	/**
	 * @return the tcpPort
	 */
	public int getTcpPort() {
		return tcpPort;
	}


	/**
	 * @param tcpPort the tcpPort to set
	 */
	public void setTcpPort(int tcpPort) {
		this.tcpPort = tcpPort;
	}


	/**
	 * @return the user
	 */
	public ConcurrentHashMap<String, User> getUser() {
		return user;
	}


	/**
	 * @param user the user to set
	 */
	public void setUser(ConcurrentHashMap<String, User> user) {
		this.user = user;
	}


	/**
	 * @return the auction
	 */
	public ConcurrentHashMap<Integer,Auction> getAuction() {
		return auction;
	}


	/**
	 * @param auction the auction to set
	 */
	public void setAuction(ConcurrentHashMap<Integer, Auction> auction) {
		this.auction = auction;
	}


	public boolean isActive() {

		return active;
	}
	public void setActive(boolean active){
		this.active=active;
	}
	
	private void rmiInit(){
		if (System.getSecurityManager() == null) {
			System.setSecurityManager(new SecurityManager());
		}
	// neues Properties Objekt erstellen
		Properties properties = new Properties();

		
		try {
			// neuen stream mit der messenger.properties Datei erstellen
			BufferedInputStream stream = new BufferedInputStream(new FileInputStream("Server.properties"));
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
			 bss = (BillingServerSecure) registry
					.lookup(properties.getProperty("rmi.bilingServerSecure"));
			 atc = (AnalyticTaskComputing) registry.lookup(properties.getProperty("rmi.analyticsServer"));
			 
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NotBoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NumberFormatException nfe) {
			System.out.println("Properties File Fehlerhaft");
		} 
		
	
	}
	/**
	 * Bills an Auction on the billing server
	 * @param auction
	 */
	public void billAuction(Auction auction) {
		bss.billAuction(auction.getOwner().getName(), auction.getId(), auction.getHighestBid());
		
	}
}
