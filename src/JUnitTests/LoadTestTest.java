package JUnitTests;

import static org.junit.Assert.*;

import java.util.Timer;
import java.util.concurrent.ConcurrentHashMap;

import loadtest.CreateTask;
import loadtest.FakeCli;
import loadtest.LoadTest;
import management.AddStep;
import management.ManagmentClient;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.Test;

import connect.ReceiveConnection;
import server.Server;
import Client.Client;
import Client.TaskExecuter;
import analytics.AnalyticTaskComputing;
import analytics.AnalyticsServer;
import billing.BillingServer;
import billing.BillingServerSecure;
import billing.RemoteBillingServerSecure;
import billing.StartBillingServer;

public class LoadTestTest {
	private BillingServer bs;
	private AnalyticTaskComputing ats;
	private AnalyticsServer as;
	private ManagmentClient m;
	private FakeCli cli;
	private ConcurrentHashMap<String, byte[]> ret;
	private StartBillingServer start;
	private Server s;
	
	@Before
	public void setUp() {	
		ConcurrentHashMap<String,byte[]> map=new ConcurrentHashMap<String,byte[]>();
		String[] args=new String[0];
		as= new AnalyticsServer();
		new AnalyticTaskComputing(as);
		start=new StartBillingServer();
		bs =new BillingServer(start.loginMap());
//		BillingServerSecure bss = new BillingServerSecure();
//		RemoteBillingServerSecure rbss = new RemoteBillingServerSecure(bss);
//		start.initRmi(bs, rbss);
		BillingServerSecure bss = new BillingServerSecure();
		RemoteBillingServerSecure rbss = new RemoteBillingServerSecure(bss);
		bs.initRmi(bs, rbss);
		s= new Server();
		s.setTcpPort(5000);
		ReceiveConnection r = new ReceiveConnection(5000, s);	
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
		
		s.setActive(false);
		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	@Test
	public void constTest(){
		LoadTest l = new LoadTest("localhost", 5000, "loadtest.properties");
		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	@Test 
	public void auctionTest(){
		Client cl=new Client("localhost", 5000, new FakeCli(""));
		Timer t= new Timer();
		CreateTask c = new CreateTask(100, 200, new TaskExecuter(cl), 5000);
		t.schedule(c, 0, 100);
		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		t.cancel();
		t.purge();
	}
	@Test
	public void randomStringTest(){
		LoadTest l = new LoadTest("localhost", 5000, "loadtest.properties");
		
		String r=l.randomString(20);
		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		assertEquals(20, r.length(), 0);
		
	}
}
