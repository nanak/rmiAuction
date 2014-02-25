package JUnitTests;

import loadtest.FakeCli;
import management.ManagmentClient;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import server.Server;
import Client.Client;
import Client.TaskExecuter;
import analytics.AnalyticTaskComputing;
import analytics.AnalyticsServer;
import billing.BillingServer;
import billing.BillingServerSecure;
import billing.RemoteBillingServerSecure;
import billing.StartBillingServer;
import connect.ReceiveConnection;
/**
 * Tests all functions of the TaskExecuter
 * 
 * @author Nanak Tattyrek
 * @version 23.02.2014
 * @email ntattyrek@student.tgm.ac.at
 *
 */
public class TaskExecuterTest {

	private int serverPort = 5000;
	private FakeCli cli;
	private Client c;

	private BillingServer bs;
	private AnalyticTaskComputing ats;
	private AnalyticsServer as;
	private ManagmentClient m;
	private StartBillingServer start;
	private Server s;
	
	private TaskExecuter t;
	Thread tr;
	
	/**
	 * gets executed before every test method
	 * starts all needed components such as servers, etc.
	 */
	@Before
	public void setUp(){
		as = new AnalyticsServer();
		new AnalyticTaskComputing(as);
		start = new StartBillingServer();
		bs =new BillingServer();

		BillingServerSecure bss = new BillingServerSecure();
		RemoteBillingServerSecure rbss = new RemoteBillingServerSecure(bss);
		bs.initRmi(bs, rbss);
		cli = new FakeCli("");
		c = new Client("127.0.0.1", serverPort, cli);
		
		
//		m = new ManagmentClient(cli);

		// ServerStart.main(null);
		
		s = new Server(); // Generate Server object
		int port = 5000; // Default port set
		s.setTcpPort(port); // Set Server Port
		ReceiveConnection r = new ReceiveConnection(port, s); // Establish
																// Connection
		tr = new Thread(r);
		tr.start(); // Generate and start Thread
	}
	
	/**
	 * gets executed after every test method
	 * stops previously started resources
	 */
	@After
	public void cleanUp() {
		s.setActive(false);
		tr.interrupt();
	}
	
	/**
	 * test if the login command is properly executed
	 */
	@Test
	public void testLogin() {
		t = new TaskExecuter(c);
		t.login("test", 5000, 5001);
	}
	
	/**
	 * test if the logout command is properly executed
	 */
	@Test
	public void testLogout() {
		t = new TaskExecuter(c);
		t.logout();
	}
	
	/**
	 * test if the bid command is properly executed
	 */
	@Test
	public void testBid() {
		t = new TaskExecuter(c);
		t.bid(1, 150.00);
	}

	/**
	 * test if the create command is properly executed
	 */
	@Test
	public void testCreate(){
		t = new TaskExecuter(c);
		t.create((long)25200, "Super small notebook");
	}
	
	/**
	 * test if the list command is properly executed
	 */
	@Test
	public void testList(){
		t = new TaskExecuter(c);
		t.list();
	}

}
