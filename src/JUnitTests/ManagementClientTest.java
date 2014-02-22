package JUnitTests;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.concurrent.ConcurrentHashMap;

import loadtest.FakeCli;
import management.ManagmentClient;
import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import Exceptions.CommandNotFoundException;
import Exceptions.WrongNumberOfArgumentsException;
import analytics.AnalyticTaskComputing;
import analytics.AnalyticsServer;
import billing.BillingServer;
import billing.BillingServerSecure;
import billing.RemoteBillingServerSecure;
import billing.StartBillingServer;

public class ManagementClientTest {
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
		
	}

	@Test
	public void loginTest(){
		cli=new FakeCli("");
		m=new ManagmentClient(cli);
		cli.write("!login test test");
	}
	@Test
	public void loginTest1(){
		cli=new FakeCli("!login test test");
		m=new ManagmentClient(cli);
	}
	@Test
	public void printTest(){
		cli=new FakeCli("");
		m=new ManagmentClient(cli);
		cli.write("!print");
	}
	@Test
	public void autoTest(){
		cli=new FakeCli("");
		m=new ManagmentClient(cli);
		cli.write("!auto");
	}
	@Test
	public void hideTest(){
		cli=new FakeCli("");
		m=new ManagmentClient(cli);
		cli.write("!hide");
	}
	@Test
	public void subscribeTest(){
		cli=new FakeCli("");
		m=new ManagmentClient(cli);
//		m.setAnalyticTaskComputing(ats);
//		m.setBillingServer(bs);
		cli.write("!subscribe .*");
	}
	@Test
	public void unsubscribeTest(){
		cli=new FakeCli("");
		m=new ManagmentClient(cli);
//		m.setAnalyticTaskComputing(ats);
//		m.setBillingServer(bs);
		cli.write("!unsubscribe 1");
	}
	@Test(expected=WrongNumberOfArgumentsException.class)
	public void unsubscribeExceptionTest(){
		cli=new FakeCli("");
		m=new ManagmentClient(cli);
//		m.setAnalyticTaskComputing(ats);
//		m.setBillingServer(bs);
		cli.write("!unsubscribe");
	}
	@Test(expected=WrongNumberOfArgumentsException.class)
	public void subscribeExceptionTest(){
		cli=new FakeCli("");
		m=new ManagmentClient(cli);
//		m.setAnalyticTaskComputing(ats);
//		m.setBillingServer(bs);
		cli.write("!subscribe");
	}
	@Test
	public void endUnsecureTest(){
		cli=new FakeCli("");
		m=new ManagmentClient(cli);
		cli.write("!end");
	}
	@Test
	public void secureTest(){
		cli=new FakeCli("");
		m=new ManagmentClient(cli);
		cli.write("!login test test\n!steps");
	}
	@Test
	public void logoutTest(){
		cli=new FakeCli("");
		m=new ManagmentClient(cli);
		cli.write("!login test test\n!logout");
	}
	@Test
	public void endSecureTest(){
		cli=new FakeCli("");
		m=new ManagmentClient(cli);
		cli.write("!login test test\n!end");
	}

}