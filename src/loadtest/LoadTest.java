package loadtest2;

import java.io.ByteArrayInputStream;
import java.util.Timer;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import management.ManagmentClient;
import Client.Client;
import Client.TCPConnector;
import Client.TaskExecuter;
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
	private Client c;
	private TaskExecuter t;
	private int tcp;
	private Timer create;
	private Timer bid;
	private long starttime;
	private Timer list;
	private FakeCli cli;
	private Timer checker;
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
		new ManagmentClient(new FakeCli("!subscribe .* \n!auto"));
		new LoadTest(hostname,port);
		
		
	}
	public LoadTest(String hostname,int port){
		clients=new ConcurrentHashMap<Integer,Thread>();
		
		Properties p = new Properties();
		//read properties from file
		p.setFromFile("loadtest.properties");
		//p.setFromFile("/home/mlipovits/gitRepos/rmiAuction/loadtest.properties");

		/**
		 * 
		 * UNBEDINGT THREADPOOL
		 */
 		//put clients to map
		
		for (int i=0; i<p.getClients(); i++){
			starttime=System.currentTimeMillis();
			cli=new FakeCli("");
			c= new Client(hostname, port, cli);
			t=c.getT();
			tcp=c.getTcpPort();
			create=new Timer();
			bid=new Timer();
			list=new Timer();
			checker=new Timer();
			create.schedule(new CreateTask(p.getAuctionsPerMin(), p.getAuctionDuration(), t, tcp), 0, 60000/p.getAuctionsPerMin());
			bid.schedule(new BidTask(p.getBidsPerMin(), starttime, t,cli), 500, 60000/p.getBidsPerMin());
			list.schedule(new ListTask(t), 600, p.getUpdateIntervalSec()*1000);
			checker.schedule(new CheckTimeTask(starttime, list, create, bid), 1000, 30000);
		}
		
	}

}
