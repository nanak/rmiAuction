package connect;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.ArrayList;

import model.User;

/**
 * Sends to every User of a given ArrayList a given Message
 * Implements the Notifier Interface.
 * 
 * @author Daniel Reichmann <dreichmann@student.tgm.ac.at>
 * @version 10-12-2013
 */
public class UDPNotifier implements Notifier{
	
	/**
	 * Sends the Notifications via UDP
	 * 
	 * @param al	User which shall receive the package
	 * @param message	Message which shall be send
	 */
	public void notify(ArrayList<User> al, String message){
		
		DatagramSocket ds=null;
		try {
			ds=new DatagramSocket();
			ds.setSoTimeout(5000);
		} catch (SocketException e1) {
		}
		byte[] buf = message.getBytes();
		DatagramPacket dp = new DatagramPacket(buf, buf.length);
		for (User user : al) {
			try {
				
				if(user.isActive()){
					ds = new DatagramSocket();
					InetAddress address = InetAddress.getByName(user.getAdresse());
					dp = new DatagramPacket(buf, buf.length, address,user.getUdpPort());
					ds.send(dp);
				}
				else{
						user.getMessages().add(message);
					}
				
			} catch (SocketException e) {} catch (UnknownHostException e) {
				System.out.println("Hostname could not be resolved");
			} catch (IOException e) {
				System.out.println("Could not send Paket");
			}
		}
	}
}
