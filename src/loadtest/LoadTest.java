package loadtest;

import java.io.ByteArrayInputStream;
import java.util.concurrent.ConcurrentHashMap;

import management.ManagmentClient;
import Client.Client;
/**
 * Class LoadTest which starts the Loadtests.
 * 
 * @author Michaela Lipovits
 * @version 20140209
 */
public class LoadTest {
	private static final String STRING_CHARS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
	private Properties properties;
	private static ConcurrentHashMap<Integer, Thread> clients;
	private ManagmentClient mc;
	/**
	 * Method which reads and creates a System Descrtiption.
	 * 
	 * @return boolean Flag, if create was successful
	 */
	public boolean createSystemDescription() {
		return false;
	}
	public static String randomString(int count) {
		StringBuilder builder = new StringBuilder();
		while (count-- != 0) {
			int character = (int)(Math.random()*STRING_CHARS.length());
			builder.append(STRING_CHARS.charAt(character));
		}
		return builder.toString();
	}
	public static void main(String[] args) {
		int port;
		String hostname;
		if(args.length!=2){
			port=5000;
			hostname="localhost";
			System.out.println("No/Wrong number of arguments. default (hostname: localhost, port: 5000) used.");
		}
		else{
			hostname=args[0];
			try{
				port=Integer.parseInt(args[1]);
			}catch(NumberFormatException e){
				System.out.println("The given port is not a number, default port 5000 used.");
				port=5000;
			}
		}
		new LoadTest(hostname,port);
		new ManagmentClient(new FakeCli("!login admin admin\n!auto\n!subscribe '*'"));
		
	}
	public LoadTest(String hostname,int port){
		clients=new ConcurrentHashMap<Integer,Thread>();
		
		Properties p = new Properties();
		//read properties from file
		p.setFromFile("loadtest.properties");

		/**
		 * 
		 * UNBEDINGT THREADPOOL
		 */
 		//put clients to map
		Thread t;
		for (int i=0; i<p.getClients(); i++){
			try {
				Thread.sleep(10);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			t=new Thread(new Client(hostname, port, new FakeCli(p.getAuctionsPerMin(),p.getAuctionDuration(),p.getUpdateIntervalSec(),p.getBidsPerMin())));
			clients.put(i, t);
			t.start();
		}
		
	}

}
