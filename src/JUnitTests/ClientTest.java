package JUnitTests;

import static org.junit.Assert.assertEquals;

import java.util.Scanner;
import java.util.concurrent.ConcurrentHashMap;

import loadtest.FakeCli;
import management.ManagmentClient;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import analytics.AnalyticTaskComputing;
import analytics.AnalyticsServer;
import billing.BillingServer;
import billing.BillingServerSecure;
import billing.RemoteBillingServerSecure;
import billing.StartBillingServer;

import server.Server;
import server.ServerStart;
import connect.ReceiveConnection;

import Client.CLI;
import Client.Client;

public class ClientTest {

	private int serverPort = 5000;
	private FakeCli cli;
	private Client c;

	private BillingServer bs;
	private AnalyticTaskComputing ats;
	private AnalyticsServer as;
	private ManagmentClient m;
	private StartBillingServer start;
	private Server s;

	@Before
	public void setUp() {
//		as = new AnalyticsServer();
//		new AnalyticTaskComputing(as);
//		start = new StartBillingServer();
//		bs = new BillingServer(start.loginTestMap());
//		BillingServerSecure bss = new BillingServerSecure();
//		RemoteBillingServerSecure rbss = new RemoteBillingServerSecure(bss);
//		start.initRmi(bs, rbss);
//		cli = new FakeCli("");
		
		
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
	public void testSetUsername() {
		cli = new FakeCli("");
		c = new Client("127.0.0.1", serverPort, cli);
		c.setUsername("test");
		assertEquals("test", c.getUsername());
	}

	@Test
	public void testList() {
		cli = new FakeCli("");
		c = new Client("127.0.0.1", serverPort, cli);
		c.run();
		cli.write("!list");
	}

	@Test
	public void testLogin() {
		cli = new FakeCli("");
		c = new Client("127.0.0.1", serverPort, cli);
		c.run();
		cli.write("!login test");
	}

	@Test
	public void testCreate() {
		cli = new FakeCli("");
		c = new Client("127.0.0.1", serverPort, cli);
		c.run();
		cli.write("!create 25200 Super small notebook");
	}

	@Test
	public void testBid() {
		cli = new FakeCli("");
		c = new Client("127.0.0.1", serverPort, cli);
		c.run();
		cli.write("!bid 1 100");
	}

	@Test
	public void testLogout() {
		cli = new FakeCli("");
		c = new Client("127.0.0.1", serverPort, cli);
		c.run();
		cli.write("!logout");
	}

	@Test
	public void testEnd() {
		cli = new FakeCli("");
		c = new Client("127.0.0.1", serverPort, cli);
		c.run();
		cli.write("!end");
	}
	
}
