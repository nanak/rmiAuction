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
	
	@Before
	public void setUp(){
		as = new AnalyticsServer();
		new AnalyticTaskComputing(as);
		start = new StartBillingServer();
		bs =new BillingServer(start.loginMap());

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
	
	@After
	public void cleanUp() {
		s.setActive(false);
		tr.interrupt();
	}
	
	@Test
	public void testLogin() {
		t = new TaskExecuter(c);
		t.login("test", 5000, 5001);
	}
	
	@Test
	public void testLogout() {
		t = new TaskExecuter(c);
		t.logout();
	}
	
	@Test
	public void testBid() {
		t = new TaskExecuter(c);
		t.bid(1, 150.00);
	}

	@Test
	public void testCreate(){
		t = new TaskExecuter(c);
		t.create((long)25200, "Super small notebook");
	}
	
	@Test
	public void testList(){
		t = new TaskExecuter(c);
		t.list();
	}

}
