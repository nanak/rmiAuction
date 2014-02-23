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

import rmi.InitRMI;
import Event.Event;
import analytics.AnalyticTaskComputing;
import analytics.RemoteAnalyticsTaskComputing;
import billing.BillingServer;
import billing.BillingServerSecure;
import billing.IRemoteBillingServerSecure;
import billing.RemoteBillingServerSecure;
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
	private IRemoteBillingServerSecure bss;
	private RemoteAnalyticsTaskComputing atc;
	private boolean active;
	private InitRMI ir;
	private String billingServer, analyticsServer;
	
	/**
	 * The standard konstructor where are all attributes are set up and the attributes are
	 * initialised.
	 */
	public Server() {
		active = true;
		rmiInit();
		user= new ConcurrentHashMap<String, User>(); //Only way to get ConcurrendHashSet
		
		auction=new ConcurrentHashMap<Integer, Auction>();
		ahandler = new AuctionHandler(this);
		rhandler = new RequestHandler();
//		udp = NotifierFactory.getUDPNotifer();
		Thread athread = new Thread(ahandler);
		athread.setPriority(Thread.MIN_PRIORITY);
		
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
//		udp.notify(al,message);
//		System.out.println(message); 
	}
	
	/**
	 * Notifies the analyticsServer of new Events
	 * @param e
	 */
	public void notify(Event e){
		try {
			atc.processEvent(e);
		} catch (RemoteException e1) {
			//AnalyticsServer not available anymore
			try {
				atc = (RemoteAnalyticsTaskComputing) ir.lookup(analyticsServer);
			} catch (RemoteException | NotBoundException e2) {
				System.out.println("AnalyticsServer not available anymore. Looking up next time");
			}
		}
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
		try {
			 Properties properties = new Properties();
				// neuen stream mit der messenger.properties Datei erstellen
				BufferedInputStream stream = new BufferedInputStream(new FileInputStream("Server.properties"));
				
				properties.load(stream);
			
				stream.close();
				ir  = new InitRMI(properties);
				ir.init();
				billingServer= properties.getProperty("rmi.billingServerSecure");
				analyticsServer = properties.getProperty("rmi.analyticsServer");
				try{
					bss = (IRemoteBillingServerSecure) ir.lookup(billingServer);
				}catch(NotBoundException ex){
					System.out.println("BillingServer not bound! Start Server then restart!");
					active=false;
					return;
				}
				try{
					atc = (RemoteAnalyticsTaskComputing) ir.lookup(analyticsServer);
				}
				catch(NotBoundException ex){
					System.out.println("AnalyticServer not bound! Start Analytics then restart!");
					active=false;
					return;
				}
				

	            
	        }catch (Exception e){
	        	e.printStackTrace();
	        }
		
	
	}
	/**
	 * Bills an Auction on the billing server
	 * @param auction
	 */
	public void billAuction(Auction auction) {
		try {
			bss.billAuction(auction.getOwner().getName(), auction.getId(), auction.getHighestBid());
		} catch (RemoteException e) {
			//BillingServer not available anymore
			//Looking him up
			try{
				bss = (IRemoteBillingServerSecure) ir.lookup(billingServer);
			}
			catch(NotBoundException | RemoteException ex){
				//Could not receive billing server
				System.err.println("Billing Server is not available anymore. Looking up next time");
			}
		}
		
	}
	/**
	 * Kills the Server immediatly!
	 * For testing purposes only
	 */
	public void kill(){
		ahandler = null;
		
	}
}
