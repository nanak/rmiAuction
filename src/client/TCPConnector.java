package client;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

import client.UI;
import model.LoginMessage;
import model.Message;

/**
 * Connects from the Client to the server using TCPSockets
 * 
 * @author Daniel Reichman <dreichmann@student.tgm.ac.at>
 * @version 2014-01-07
 */
public class TCPConnector implements Runnable{
	
	int tcpPort;
	private Message message; //Message to be sent
	private Socket s; //Connection to Server
	private ReentrantLock lock = new ReentrantLock(); //To lock specific actions
	private Condition con; //Thread wait until message is set
	private Thread t; //Thread in which program is running
	//Fehlt Objekt fuer Ausgabe
	private ObjectOutputStream objectOutput; //Stream for Output
	private ObjectInputStream input; //Stream for Input
	UI ui; //Output into CLI/GUI
	Client client; //Client
	
	public TCPConnector(int p, UI ui,Client c){
		tcpPort = p;
		this.ui = ui;
		con = lock.newCondition();
//		con.await();
		t = new Thread(this);
		client=c;
		
		try {
			s = new Socket(c.getHost(),tcpPort);
			objectOutput = new ObjectOutputStream(s.getOutputStream());
			input = new ObjectInputStream(s.getInputStream());
			objectOutput.writeObject(null); //Initialize stream
			ui.out("Connector started");
		} catch (UnknownHostException e) {
			System.out.println("Could not Connect to Server");
			return;
		} catch (IOException e) {
			System.out.println("Could not open Connection\nCheck server and restart");
			client.setActive(false);
			return;
		}
		t.start();
	}
	/**
	 * Gives the Connector a message to send to the server
	 * Notifies the Thread that it has to work
	 */
	public void sendMessage(Message m){
		lock.lock();
		message = m;
		con.signal();
		lock.unlock();
	}

	@Override
	public void run() {
		try {			
			while(client.isActive()){
				try{
					lock.lock();
					//Wait if message is null
					if(message==null){
						try {
							//Wait until sendMessage is called
							con.await();						
//							if(!client.isActive())
//								break;
						} catch (InterruptedException e) {
							//Could not wait
						}
					}
					
					objectOutput.writeObject(message);	
					String s="";
					try {
						s = (String)input.readObject();
						if(message instanceof LoginMessage){
							if(s.contains("Successfully")){
								client.setLoggedIn(true);
								client.setUsername(message.getName());
							}
						}
						ui.out(s);
					} catch (ClassNotFoundException e) {
						
					}
					
					message=null;
				}finally{
					lock.unlock();
				}
			}
			s.close();
			
		} catch (IOException e) {
			System.out.println("Server unreachable. Check configs and restart");
			client.setActive(false);
		}
	}

}
