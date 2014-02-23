package JUnitTests;

import static org.junit.Assert.fail;
import loadtest.FakeCli;
import management.ManagmentClient;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import server.Server;
import Client.Client;
import Client.TCPConnector;
import analytics.AnalyticTaskComputing;
import analytics.AnalyticsServer;
import billing.BillingServer;
import billing.BillingServerSecure;
import billing.RemoteBillingServerSecure;
import billing.StartBillingServer;
import connect.ReceiveConnection;

public class TCPConnectorTest {

	private int serverPort = 5000;
	private FakeCli cli;
	private Client c;

	private BillingServer bs;
	private AnalyticTaskComputing ats;
	private AnalyticsServer as;
	private ManagmentClient m;
	private StartBillingServer start;
	private Server s;
	
	private TCPConnector t;
	
	@Before
	public void setUp(){
		as = new AnalyticsServer();
		new AnalyticTaskComputing(as);
		start = new StartBillingServer();
		bs = new BillingServer(start.loginTestMap());
		BillingServerSecure bss = new BillingServerSecure();
		RemoteBillingServerSecure rbss = new RemoteBillingServerSecure(bss);
		start.initRmi(bs, rbss);
		cli = new FakeCli("");
		c = new Client("127.0.0.1", serverPort, cli);
		
		
//		m = new ManagmentClient(cli);

		// ServerStart.main(null);
		
		s = new Server(); // Generate Server object
		int port = 5000; // Default port set
		s.setTcpPort(port); // Set Server Port
		ReceiveConnection r = new ReceiveConnection(port, s); // Establish
																// Connection
		Thread t = new Thread(r);
		t.start(); // Generate and start Thread
	}
	
	@After
	public void cleanUp() {
		s.setActive(false);
	}
	
	@Test
	public void test() {
		fail("Not yet implemented");
	}

}
