package JUnitTests;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.concurrent.ConcurrentHashMap;

import loadtest.FakeCli;
import management.ManagmentClient;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import server.Server;
import Client.Client;
import analytics.AnalyticTaskComputing;
import analytics.AnalyticsServer;
import billing.BillingServer;
import billing.BillingServerSecure;
import billing.RemoteBillingServerSecure;
import billing.StartBillingServer;
import connect.ReceiveConnection;
/**
 * Tests all functions of the Client
 * 
 * @author Nanak Tattyrek
 * @version 23.02.2014
 * @email ntattyrek@student.tgm.ac.at
 *
 */
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
	private static int testcounter =0;
	private ArrayList<Thread> threads = new ArrayList<>();
	private ArrayList<Server> servers = new ArrayList<Server>();
	
	/**
	 * Gets executed before every test method
	 * Starts all needed components like servers, etc.
	 */
	@Before
	public void setUp() {
		ConcurrentHashMap<String,byte[]> map=new ConcurrentHashMap<String,byte[]>();
		String[] args=new String[0];
		as= new AnalyticsServer();
		new AnalyticTaskComputing(as);
		start=new StartBillingServer();
		bs =new BillingServer(start.loginMap());

//		bs =new BillingServer(start.loginTestMap());
		BillingServerSecure bss = new BillingServerSecure();
		RemoteBillingServerSecure rbss = new RemoteBillingServerSecure(bss);
		bs.initRmi(bs, rbss);
		s = new Server();
		
		s.setTcpPort(5000);
		servers.add(s);
		ReceiveConnection r = new ReceiveConnection(5000, s);	
		Thread t = new Thread(r);
		t.start();	
		threads.add(t);
		cli = new FakeCli("");
		testcounter++;
	}
	/**
	 * Gets executed after every test method
	 * Shutdown the server
	 */
	@After
	public void end(){
		if(testcounter==17){
			bs.shutdown();
			as.shutdown();
//			t.interrupt();
			Iterator<Server> it = servers.iterator();
			while(it.hasNext())
				it.next().setActive(false);
			
			try {
				Thread.sleep(5000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			for (Iterator iterator = threads.iterator(); iterator.hasNext();) {
				Thread type = (Thread) iterator.next();
				type.interrupt();
			}
		}
//		System.out.println("Shutdown");
//		try {
//			Thread.sleep(500);
//		} catch (InterruptedException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}

		
	}
	
	/**
	 * Tests if setUsername() works as expected
	 */
	@Test
	public void testSetUsername() {
		cli = new FakeCli("");
		c = new Client("127.0.0.1", serverPort, cli);
		c.setUsername("test");
		assertEquals("test", c.getUsername());
	}

	/**
	 * tests the execution of the !list command
	 */
	@Test
	public void testList() {
		cli = new FakeCli("");
		c = new Client("127.0.0.1", serverPort, cli);
		cli.write("!list\n!end");
		try {
			Thread.sleep(500);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		c.run();

	}

	/**
	 * tests the executoin of the !login command
	 */
	@Test
	public void testLogin() {
		cli = new FakeCli("");
		c = new Client("127.0.0.1", serverPort, cli);
		cli.write("!login test\n");
		Thread t = new Thread(new Runnable() {
			
			@Override
			public void run() {
				c.run();				
			}
		});
		t.start();
		try {
			Thread.sleep(200);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		cli.write("!end");
		c.setActive(false);
	}

	/**
	 * tests the execution of the !create command
	 */
	@Test
	public void testCreate() {
		cli = new FakeCli("");
		c = new Client("127.0.0.1", serverPort, cli);
		cli.write("!login test\n!create 25200 Super small notebook\n!end");
		c.run();
		try {
			Thread.sleep(500);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		c.setActive(false);
	}

	/**
	 * tests the execution of the !bid command
	 */
	@Test
	public void testBid() {
		cli = new FakeCli("");
		c = new Client("127.0.0.1", serverPort, cli);
		cli.write("!login test2\n!bid 1 100.00\n");
		Thread t = new Thread(new Runnable() {
			
			@Override
			public void run() {
				c.run();				
			}
		});
		t.start();
		try {
			Thread.sleep(200);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		cli.write("!end\n");
		c.setActive(false);
	}

	/**
	 * tests the execution of the !logout command
	 */
	@Test
	public void testLogout() {
		cli = new FakeCli("");
		c = new Client("127.0.0.1", serverPort, cli);
		cli.write("!login test\n");
		Thread t = new Thread(new Runnable() {
			@Override
			public void run() {
				c.run();				
			}
		});
		t.start();
		try {
			Thread.sleep(200);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		cli.write("!logout\n!end");
		c.setActive(false);
	}

	/**
	 * tests the execution of the !logout command
	 */
	@Test
	public void testEnd() {
		cli = new FakeCli("");
		c = new Client("127.0.0.1", serverPort, cli);
		cli.write("!end");
		c.run();
		try {
			Thread.sleep(500);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	

	/**
	 * test the error handling if the !bid command gets a wrong number of arguments
	 */
	@Test
	public void testBidWrongNumberOfArguments(){
		cli = new FakeCli("");
		c = new Client("127.0.0.1", serverPort, cli);
		cli.write("!login testbid\n");
		Thread t = new Thread(new Runnable() {
			@Override
			public void run() {
				c.run();				
			}
		});
		t.start();
		try {
			Thread.sleep(200);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		cli.write("\n!bid 1 123456.12 1234\n!end\n");
		try {
			Thread.sleep(200);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		assertEquals("ERROR: Wrong number of arguments given!\nUsage !bid ID Amount",cli.getOutputBeforeEnd());
	}
	
	/**
	 * tests the error handing if the !login command gets a wrong number of arguments
	 */
	@Test
	public void testLoginWrongNumberOfArguments(){
		cli = new FakeCli("");
		c = new Client("127.0.0.1", serverPort, cli);
		cli.write("!login test test\n!end");
		c.run();
		assertEquals("ERROR: Wrong number of arguments given!\nUsage: !login Username",cli.getOutputOnIndex(1));
	}
	
	/**
	 * tests the error handling if trying to !bid without being logged in
	 */
	@Test
	public void testBidNotLoggedIn(){
		cli = new FakeCli("");
		c = new Client("127.0.0.1", serverPort, cli);
		cli.write("!bid 1 123456.12 1234\n!end");
		c.run();
		assertEquals("Currently not logged in\nPlease login first",cli.getOutputOnIndex(1));
	}
	
	/**
	 * tests the error handling if the !bid command doesn't get a number as value
	 */
	@Test
	public void testBidNotANumber(){
		cli = new FakeCli("");
		c = new Client("127.0.0.1", serverPort, cli);
		cli.write("!login test1\n!create 25200 Super small notebook\n");
		Thread t = new Thread(new Runnable() {
			@Override
			public void run() {
				c.run();				
			}
		});
		t.start();
		try {
			Thread.sleep(200);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		cli.write("!logout\n!login test2");
		try {
			Thread.sleep(200);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		cli.write("\n!bid sd 1\n!end");
		try {
			Thread.sleep(200);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		assertEquals("ERROR: One or more arguments are invalid!",cli.getOutputBeforeEnd());
	}
	
	/**
	 * tests the error handling if the specified command doesn't exist
	 */
	@Test
	public void testNoSuchCommand(){
		cli = new FakeCli("");
		c = new Client("127.0.0.1", serverPort, cli);
		cli.write("!loiigiin test1\n!end");
		c.run();
		assertEquals("ERROR: This Command does not exist!\nCould not recognize input\nPlease try again",cli.getOutputOnIndex(1));
	}
	
	/**
	 * tests error handling if already logged in user tries to login again
	 */
	@Test
	public void testDoubleLogin(){
		cli = new FakeCli("");
		c = new Client("127.0.0.1", serverPort, cli);
		cli.write("!login test4\n");
		Thread t = new Thread(new Runnable() {
			@Override
			public void run() {
				c.run();				
			}
		});
		t.start();
		try {
			Thread.sleep(200);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		cli.write("!login test5\n!end");
		try {
			Thread.sleep(200);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		assertEquals("Already logged in, logout first!",cli.getOutputBeforeEnd());
	}
	
	/**
	 * tests error handling if user tries to create an auction without being logged in
	 */
	@Test
	public void testCreateNotLoggedIn(){
		cli = new FakeCli("");
		c = new Client("127.0.0.1", serverPort, cli);
		cli.write("!create 25200 Super small notebook\n!end");
		c.run();
		assertEquals("Currently not logged in\nPlease login first",cli.getOutputOnIndex(1));
	}
	
	/**
	 * tests error handling if user tries to logout without being logged in first
	 */
	@Test
	public void testLogoutNotLoggedIn(){
		System.out.println("Not logged in Test");
		cli = new FakeCli("");
		c = new Client("127.0.0.1", serverPort, cli);
		cli.write("!logout\n!end");
		c.run();
		assertEquals("Logout not possible, not logged in!",cli.getOutputOnIndex(1));
	}
	
	/**
	 * tests if getCli() works as expected
	 */
	@Test
	public void testGetCli(){
		cli = new FakeCli("");
		c = new Client("127.0.0.1", serverPort, cli);
		assertEquals(cli, c.getCli());
	}
	
	/**
	 * tests if getTcpPort() works as expected
	 */
	@Test
	public void testGetTcpPort(){
		cli = new FakeCli("");
		c = new Client("127.0.0.1", serverPort, cli);
		assertEquals(5000, c.getTcpPort());
	}

}
