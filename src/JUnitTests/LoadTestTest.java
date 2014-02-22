package JUnitTests;

import static org.junit.Assert.*;

import java.util.Timer;
import java.util.concurrent.ConcurrentHashMap;

import loadtest.CreateTask;
import loadtest.FakeCli;
import loadtest.LoadTest;
import management.AddStep;
import management.ManagmentClient;

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
	
	@Before
	public void setUp() {	
		ConcurrentHashMap<String,byte[]> map=new ConcurrentHashMap<String,byte[]>();
		String[] args=new String[0];
		as= new AnalyticsServer();
		new AnalyticTaskComputing(as);
		start=new StartBillingServer();
		bs =new BillingServer(start.loginTestMap());
		BillingServerSecure bss = new BillingServerSecure();
		RemoteBillingServerSecure rbss = new RemoteBillingServerSecure(bss);
		start.initRmi(bs, rbss);
		Server s = new Server();
		s.setTcpPort(5000);
		ReceiveConnection r = new ReceiveConnection(5000, s);	
		Thread t = new Thread(r);
		t.start();	
	}
	@Test
	public void constTest(){
		LoadTest l = new LoadTest("localhost", 5000, "loadtest.properties");
	}
	@Test 
	public void auctionTest(){
		Client cl=new Client("localhost", 5000, new FakeCli(""));
		Timer t= new Timer();
		CreateTask c = new CreateTask(100, 200, new TaskExecuter(cl), 5000);
		t.schedule(c, 0, 100);
		try {
			Thread.sleep(2000);
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
		assertEquals(20, r.length(), 0);
	}
}
