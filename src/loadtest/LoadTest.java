package loadtest;

import java.io.ByteArrayInputStream;
import java.util.concurrent.ConcurrentHashMap;

import Client.Client;
import Client.FakeCli;
/**
 * Class LoadTest which starts the Loadtests.
 * 
 * @author Michaela Lipovits
 * @version 20140209
 */
public class LoadTest {
	private static final String ALPHA_NUMERIC_STRING = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
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
	public static String randomAlphaNumeric(int count) {
		StringBuilder builder = new StringBuilder();
		while (count-- != 0) {
			int character = (int)(Math.random()*ALPHA_NUMERIC_STRING.length());
			builder.append(ALPHA_NUMERIC_STRING.charAt(character));
		}
		return builder.toString();
	}
	public static void main(String[] args) {
		int port;
		if(args.length!=1){
			port=5000;
			System.out.println("No port set, default port 5000 used.");
		}
		else{
			try{
				port=Integer.parseInt(args[0]);
			}catch(NumberFormatException e){
				System.out.println("The given port is not a number, default port 5000 used.");
				port=5000;
			}
		}
		Properties p = new Properties();
		//read properties from file
		//p.setFromFile("/home/mlipovits/GitRepos/rmiAuction/lipovits/loadtest/loadtest.properties");
		FakeCli cli=new FakeCli(" ");
		Client c=new Client("localhost", port, cli);
		c.run();
		cli.write("!login muh");
		cli.write("!bid 111111");
		
 		//put clients to map
//		for (int i=0; i<p.getClients(); i++)
//			clients.put(i, new Client("localhost", port, new FakeCli("!login "+randomAlphaNumeric(10))));
//		}
//	
	}

}
