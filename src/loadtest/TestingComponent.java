package loadtest;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import management.ManagmentClient;
import Client.Client;
import Client.TCPConnector;
import Client.TaskExecuter;
import Client.UI;
/**
 * Class LoadTest which starts the Loadtests.
 * 
 * @author Michaela Lipovits
 * @version 20140209
 */
public class TestingComponent {
	private static final String STRING_CHARS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
	public Properties properties;
	private static ConcurrentHashMap<Integer, Thread> clients;
	private static ManagmentClient m;
	private static FakeCli mcli;
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
		String hostname,filename;
		if(args.length!=2){
			port=5000;
			hostname="localhost";
			filename="loadtest.properties";
			System.out.println("No/Wrong number of arguments. default (hostname: localhost, port: 5000, filename: loadtest.properties) used.");
		}
		else{
			hostname=args[0];
			filename=args[2];
			try{
				port=Integer.parseInt(args[1]);
			}catch(NumberFormatException e){
				System.out.println("The given port is not a number, default port 5000 used.");
				port=5000;
			}
		}
		mcli=new FakeCli("!subscribe .* \n!auto");
		m =new ManagmentClient(mcli);
		new TestingComponent(hostname,port,filename);
		
		
	}
	public TestingComponent(String hostname,int port, String filename){
		clients=new ConcurrentHashMap<Integer,Thread>();
		
		Properties p = new Properties();
		//read properties from file
		try {
			p.setFromFile(filename);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			System.out.println(e.getMessage());
		}

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
			TimerTask c=new CreateTask(p.getAuctionsPerMin(), p.getAuctionDuration(), t, tcp);
			create.schedule(c, 0, 60000/p.getAuctionsPerMin());
			bid.schedule(new BidTask(p.getBidsPerMin(), starttime, t,cli), 500, 60000/p.getBidsPerMin());
			list.schedule(new ListTask(t), 600, p.getUpdateIntervalSec()*1000);
			checker.schedule(new CheckTimeTask(starttime, list, create, bid, m, mcli), 1000, 10000);
		}
		
	}
	/**
	 * Kills all Timers
	 */
	public void shutdown(){
		bid.cancel();bid.purge();
		checker.cancel();checker.purge();
		list.cancel();list.purge();
		create.cancel();create.purge();
	}

}
