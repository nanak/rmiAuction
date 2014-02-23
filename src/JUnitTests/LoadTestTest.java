package JUnitTests;

import static org.junit.Assert.*;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Properties;
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
		bs =new BillingServer(loginMap());
//		BillingServerSecure bss = new BillingServerSecure();
//		RemoteBillingServerSecure rbss = new RemoteBillingServerSecure(bss);
//		start.initRmi(bs, rbss);
		BillingServerSecure bss = new BillingServerSecure();
		RemoteBillingServerSecure rbss = new RemoteBillingServerSecure(bss);
		bs.initRmi(bs, rbss);
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
	private static ConcurrentHashMap<String,byte[]> loginMap(){
		 
		Properties properties = new Properties();
		// neuen stream mit der messenger.properties Datei erstellen

		try {
			BufferedInputStream stream = new BufferedInputStream(
					new FileInputStream("user.properties"));

			properties.load(stream);
			stream.close();
		} catch (IOException e1) {

			System.out.println("user.properties konnte nicht geladen werden. Erzeuge neues user.properties File");
			properties = new Properties();
			
			try {
				byte[] bytesOfMessage;
				MessageDigest md;
				bytesOfMessage = "auctionpw".getBytes("UTF-8");
				md = MessageDigest.getInstance("MD5");
				byte[] thedigest = md.digest(bytesOfMessage);
				properties.put("auction", new String(thedigest));
				bytesOfMessage = "test".getBytes("UTF-8");
				md = MessageDigest.getInstance("MD5");
				thedigest = md.digest(bytesOfMessage);
				properties.put("test", new String(thedigest));
				File f = new File("user.properties");
				if(f.exists())
					f.delete();
				f.createNewFile();
				PrintWriter pw = new PrintWriter (new FileOutputStream(f));
				properties.store(pw, null);
			} catch (NoSuchAlgorithmException e) {
				System.out.println("Should not possible to Reach");
				e.printStackTrace();
			} catch (UnsupportedEncodingException e) {
				System.out.println("Should not possible to Reach");
				e.printStackTrace();
			} catch (FileNotFoundException e) {
				System.out.println("Should not possible to Reach");
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		ConcurrentHashMap<String,byte[]> ret = new ConcurrentHashMap<String,byte[]>();
		
		try {
			for (Object o : properties.keySet()){
				ret.put((String)o, ((String)properties.get(o)).getBytes());
			}
			
			return ret;
		} catch (ClassCastException e) {
			System.out.println("user.properties Fehlerhaft");
		}
		return null;
 }


}
