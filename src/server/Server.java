package server;

import java.util.ArrayList;

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
	private ArrayList<User> user;
	private ArrayList<Auction> auction;
	private AuctionHandler ahandler;
	private RequestHandler rhandler; 
	private Notifier udp;
	private boolean active;
	
	/**
	 * The standard konstructor where are all attributes are set up and the attributes are
	 * initialised.
	 */
	public Server() {
		user=new ArrayList<User>();
		active = true;
		auction=new ArrayList<Auction>();
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
	public ArrayList<User> getUser() {
		return user;
	}


	/**
	 * @param user the user to set
	 */
	public void setUser(ArrayList<User> user) {
		this.user = user;
	}


	/**
	 * @return the auction
	 */
	public ArrayList<Auction> getAuction() {
		return auction;
	}


	/**
	 * @param auction the auction to set
	 */
	public void setAuction(ArrayList<Auction> auction) {
		this.auction = auction;
	}


	public boolean isActive() {

		return active;
	}
	public void setActive(boolean active){
		this.active=active;
	}
}
