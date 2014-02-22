package JUnitTests;

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

public class ManagementClientTest {
	private BillingServer bs;
	private AnalyticTaskComputing ats;
	private ManagmentClient m;
	private FakeCli cli;
	@Before
	public void setUp() {
		ConcurrentHashMap<String,byte[]> map=new ConcurrentHashMap<String,byte[]>();
		bs=new BillingServer(map);
		ats=new AnalyticTaskComputing(new AnalyticsServer());	
	}

	@Test
	public void loginTest(){
		cli=new FakeCli("");
		m=new ManagmentClient(cli);
		m.setAnalyticTaskComputing(ats);
		m.setBillingServer(bs);
		cli.write("!login muh muh");
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
		m.setAnalyticTaskComputing(ats);
		m.setBillingServer(bs);
		cli.write("!subscribe .*");
	}
	@Test
	public void unsubscribeTest(){
		cli=new FakeCli("");
		m=new ManagmentClient(cli);
		m.setAnalyticTaskComputing(ats);
		m.setBillingServer(bs);
		cli.write("!unsubscribe 1");
	}
	@Test(expected=WrongNumberOfArgumentsException.class)
	public void unsubscribeExceptionTest(){
		cli=new FakeCli("");
		m=new ManagmentClient(cli);
		m.setAnalyticTaskComputing(ats);
		m.setBillingServer(bs);
		cli.write("!unsubscribe");
	}
	@Test(expected=WrongNumberOfArgumentsException.class)
	public void subscribeExceptionTest(){
		cli=new FakeCli("");
		m=new ManagmentClient(cli);
		m.setAnalyticTaskComputing(ats);
		m.setBillingServer(bs);
		cli.write("!subscribe");
	}
	@Test
	public void endUnsecureTest(){
		cli=new FakeCli("");
		m=new ManagmentClient(cli);
		cli.write("!end");
	}


}
