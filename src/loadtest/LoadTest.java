package loadtest;

import java.io.ByteArrayInputStream;
import java.util.concurrent.ConcurrentHashMap;

import Client.Client;
/**
 * Class LoadTest which starts the Loadtests.
 * 
 * @author Michaela Lipovits
 * @version 20140209
 */
public class LoadTest {

	private Properties properties;
	private static ConcurrentHashMap<Integer, Client> clients;
	/**
	 * Method which reads and creates a System Descrtiption.
	 * 
	 * @return boolean Flag, if create was successful
	 */
	public boolean createSystemDescription() {
		return false;
	}

	public static void main(String[] args) {
		Properties p = new Properties();
		//read properties from file
		p.setFromFile("/home/mlipovits/GitRepos/rmiAuction/lipovits/loadtest/loadtest.properties");
		
		//Byteinputstream for clients
		String text= "!login muh\n!bid miau";
  		ByteArrayInputStream in=new ByteArrayInputStream(text.getBytes());

  		//put clients to map
		for (int i=0; i<p.getClients(); i++){
			clients.put(i, new Client(null, 0, null));
		}
		


	}

}
