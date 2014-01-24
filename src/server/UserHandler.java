package server;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.SocketException;

import model.LoginMessage;
import model.Message;
import model.User;

/**
 * 
 * Handles one TCP Connection with one User. Establishes the connection in own thread
 * 
 * User can be anonymous but has the Chance to log himself in. If the user logged in
 * the Server gets the User from the Server. If the Connection is closed logs out
 * the user automatically
 * 
 * @author Daniel Reichmann <dreichmann@student.tgm.ac.at>
 * @version 10-12-2013
 *
 */
public class UserHandler implements Runnable{

	private Server server;
	private User user; 
	private Socket client; //Socket-Verbindung mit Client
	private ObjectInputStream in;
	private ObjectOutputStream out;
	private Thread executor; //Fuerht die Aktionen durch.
	
	/**
	 * Creates the UserHandler and starts the Connection
	 * 
	 * @param c 	Socket which has the connection
	 * @param s		Server which shall handle the requests
	 */
	public UserHandler(Socket c,Server s){
		client = c;
		server = s;

		try {
			in = new ObjectInputStream( client.getInputStream());
			out = new ObjectOutputStream(client.getOutputStream());
			c.setSoTimeout(5000);
		} catch (IOException e) {
			System.out.println("Could not Create streams!\nExit");
			return;
		}catch(Exception e){
			return ;
		}
		executor = new Thread(this);
		executor.start();
	}
	@Override
	public void run() {
		Message m;
		while(server.isActive()){
			Object o = null;
			try {				
				o = in.readObject();
				m = (Message) o;
			} catch(SocketException e){
				System.out.println("Connection to Client lost.");
				break;
			}
			catch (ClassNotFoundException e) {
			} catch (IOException e) {

			}
			
			if(o instanceof Message){
				m = (Message) o;
				//If message is Login-Message log user in
				String ret;
				if(m instanceof LoginMessage){
					ret = server.request(m);
					if(ret.startsWith("Successfully")){
						User tmp = new User();
						tmp.setName(m.getName());
						user = server.getUser().get(server.getUser().indexOf(tmp));
					}
				}
				else{
					ret = server.request(m);
				}
				try {
					out.writeObject(ret);
				} catch (IOException e) {

				}
				
			}
		}
		try {
			client.close();
			//Finally log out User
			if(user!=null)
				if(user.isActive())
					user.setActive(false);
		} catch (IOException e) {
			System.out.println("Could not close client");
		}
	}
	
}
