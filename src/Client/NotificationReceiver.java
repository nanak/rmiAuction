package Client;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;

/**
 * Receive UDP packages and prints it on the ouptu of the Client.
 * 
 * @author Daniel Reichmann <dreichmann@student.tgm.ac.at>
 * @version 01-10-2014
 */
public class NotificationReceiver implements Runnable{
	private Client client;
	
	/**
	 * Creates the Receiver and starts receiving
	 * @param c	Client where the output is
	 */
	public NotificationReceiver(Client c){
		Thread t = new Thread(this);
		t.start();
		client = c;
	}
	/**
	 * Waits for Notifications from the server
	 */
	@Override
	public void run() {
		DatagramSocket ds = null;
		try {
			ds = new DatagramSocket(client.getUdpPort());
			ds.setSoTimeout(5000);
			//System.out.println(client.getUdpPort());
		} catch (SocketException e) {
			System.out.println("Could not open socket");
		}
		while(client.isActive()){
			byte[] buf = new byte[1024];
			DatagramPacket p=new DatagramPacket(buf, buf.length);
			try {
				if(ds!=null){
					ds.receive(p);
					client.getCli().outln("\n"+new String(p.getData()).trim()+"\n"+client.getUsername()+"> ");
				}
			} catch (IOException e) {
				//Timeout
			}
			
		}
		if(ds != null)
			ds.close();
	}	
}