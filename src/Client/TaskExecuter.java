package Client;

import java.net.InetAddress;
import java.net.UnknownHostException;

import model.*;

/**
 * This class executes all the actions done by the user.
 * This actions are transmitted via the network to the server
 * 
 * @author Dominik Valka <dvalka@student.tgm.ac.at>
 * @version 2014-01-07
 */
public class TaskExecuter {
	Client client;
	TCPConnector tcp;
	/**
	 * Client object submitted
	 * @param c
	 */
	public TaskExecuter(Client c){
		client=c;
		tcp=c.getTcp();
	}
	/**
	 * Creates a Login-Message(with Client IP) and sends it via TCP to the Server
	 * @param username
	 * @param tcpPort
	 * @param udpPort
	 */
	public void login(String username,int tcpPort,int udpPort){
		String ip="";
		try {
			ip=InetAddress.getLocalHost().getHostAddress(); //Get Client IP
		} catch (UnknownHostException e) {
			System.out.println("Could not resolve ip address!");
		}
		LoginMessage lm=new LoginMessage(username,ip,tcpPort,udpPort);
		tcp.sendMessage(lm);
	}
	/**
	 * Logs out the Client
	 */
	public void logout(){
		LogoutMessage lom=new LogoutMessage(client.getUsername());
		tcp.sendMessage(lom);
	}
	/**
	 * Creates a bid message with username, auction id and the amount
	 * @param id
	 * @param amount
	 */
	public void bid(int id,double amount){
		BidMessage bm=new BidMessage(client.getUsername(),id,amount);
		tcp.sendMessage(bm);
	}
	/**
	 * Creates a Create-Message with username, duration and description
	 * @param duration
	 * @param description
	 */
	public void create(Long duration,String description){
		CreateMessage cm=new CreateMessage(client.getUsername(),description,duration);
		tcp.sendMessage(cm);
	}
	/**
	 * Lists all the current Auctions
	 */
	public void list(){
		ListMessage lim;
		lim=new ListMessage("");
		tcp.sendMessage(lim);
	}
}