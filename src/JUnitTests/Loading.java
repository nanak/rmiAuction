package JUnitTests;

import static org.junit.Assert.assertEquals;

import java.util.Timer;
import java.util.concurrent.ConcurrentHashMap;

import loadtest.CreateTask;
import loadtest.FakeCli;
import loadtest.LoadTest;
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
 * Tests the {@link LoadTest} class
 * @author Michaela Lipovits
 * @version 20140221
 */
public class Loading {
	private BillingServer bs;
	private AnalyticTaskComputing ats;
	private AnalyticsServer as;
	private ManagmentClient m;
	private FakeCli cli;
	private ConcurrentHashMap<String, byte[]> ret;
	private StartBillingServer start;
	private Server s;
	private LoadTest l;
	
	/**
	 * starts analyticsserver and billingserver as their mains would
	 */
	@Before
	public void setUp() {
		ConcurrentHashMap<String,byte[]> map=new ConcurrentHashMap<String,byte[]>();
		String[] args=new String[0];
		System.out.println("Now Billing Test initialization");
		bs =new BillingServer();

		BillingServerSecure bss = new BillingServerSecure();
		RemoteBillingServerSecure rbss = new RemoteBillingServerSecure(bss);
		bs.initRmi(bs, rbss);
		System.out.println("New Analytics");
		as= new AnalyticsServer();
		new AnalyticTaskComputing(as);
		s= new Server();
		s.setTcpPort(6000);
		ReceiveConnection r = new ReceiveConnection(6000, s);	
		Thread t = new Thread(r);
		t.start();	
	}
	/**
	 * Closes all Servers
	 */
	@After
	public void end(){
		as.shutdown();
		bs.shutdown();
		if(l!=null){
			l.shutdown();
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		s.setActive(false);
		try {
			Thread.sleep(6000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	/**
	 * tests the constructoe of LoadTest
	 */
	@Test
	public void constTest(){
		l = new LoadTest("localhost", 6000, "loadtest.properties",5000);
		
//		l.shutdown();
		try {
			Thread.sleep(10000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	/**
	 * tests the CreateTask
	 */
	@Test 
	public void auctionTest(){
		Client cl=new Client("localhost", 6000, new FakeCli(""));
		Timer t= new Timer();
		CreateTask c = new CreateTask(100, 200, new TaskExecuter(cl), 5000);
		t.schedule(c, 0, 100);
		try {
			Thread.sleep(10000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		t.cancel();
		t.purge();
	}
	/**
	 * Tests if the randomString method creates the string with the correct length
	 */
	@Test
	public void randomStringTest(){
		LoadTest l = new LoadTest("localhost", 6000, "loadtest.properties",5000);
		
		String r=l.randomString(20);
		try {
			Thread.sleep(10000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		assertEquals(20, r.length(), 0);
		l.shutdown();
	}

}