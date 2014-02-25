package loadtest;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ConcurrentHashMap;

import server.Server;
import connect.ReceiveConnection;
import analytics.AnalyticTaskComputing;
import analytics.AnalyticsServer;
import billing.BillingServer;
import billing.BillingServerSecure;
import billing.RemoteBillingServerSecure;
import management.ManagmentClient;
import Client.Client;
import Client.TaskExecuter;
/**
 * Class LoadTest which starts the Loadtests.
 * 
 * @author Michaela Lipovits
 * @version 20140209
 */
public class LoadTest {
	private static final String STRING_CHARS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
	public Properties properties;
	private static ArrayList<Client> clients;
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
	private AnalyticsServer as;
	private BillingServer bs;
	private Server s;
	/**
	 * Method which reads and creates a System Descrtiption.
	 * 
	 * @return boolean Flag, if create was successful
	 */
	public boolean createSystemDescription() {
		return false;
	}
	/**
	 * Methos, which generates a random String with a given length.
	 * @param count Length 
	 * @return random String
	 */
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
		
		new LoadTest(hostname,port,filename,8*60000);
		
	}
	/**
	 * Initiation of the Loadtest. 
	 * The properties are read from a File using the Properties class.
	 * Clients are initiated as well as TimerTasks to execute Commands in the Intervals given by the properties file.
	 * 
	 * @param hostname Hostname of the server
	 * @param port Port of the Server
	 * @param filename Filename of the porpertiesfile
	 */
	public LoadTest(String hostname,int port, String filename,long min){
		System.out.println("Now Billing Test initialization");
		bs =new BillingServer();

		BillingServerSecure bss = new BillingServerSecure();
		RemoteBillingServerSecure rbss = new RemoteBillingServerSecure(bss);
		bs.initRmi(bs, rbss);
		System.out.println("New Analytics");
		as= new AnalyticsServer();
		new AnalyticTaskComputing(as);
		s= new Server();
		s.setTcpPort(5000);
		ReceiveConnection r = new ReceiveConnection(5000, s);	
		Thread th = new Thread(r);
		th.start();	
		try {
			Thread.sleep(300);
		} catch (InterruptedException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		mcli=new FakeCli("!subscribe .* \n!auto");
		m =new ManagmentClient(mcli);
		
		
		clients=new ArrayList<Client>();
		
		Properties p = new Properties();
		//read properties from file
		try {
			p.setFromFile(filename);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			System.out.println(e.getMessage());
		}
		System.out.println(filename+p.getClients());

		for (int i=0; i<p.getClients(); i++){
			starttime=System.currentTimeMillis();
			cli=new FakeCli("");
			c= new Client(hostname, port, cli);
			clients.add(c);
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
			checker.schedule(new CheckTimeTask(starttime, list, create, bid, m, mcli,min, as, bs), 1000, 1000);
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
		for (int i=0; i<clients.size(); i++){
			clients.get(i).setActive(false);
		}
	}

}
