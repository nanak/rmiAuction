package JUnitTests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

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

/**
 * Tests ManagementClient Class
 * @author Michaela Lipovits
 * @version 20140222
 */
public class ManagementClientTest {
	private BillingServer bs;
	private AnalyticTaskComputing ats;
	private AnalyticsServer as;
	private ManagmentClient m;
	private FakeCli cli;
	private ConcurrentHashMap<String, byte[]> ret;
	private StartBillingServer start;
	/**
	 * starts analyticsserver and billingserver as their mains would
	 */
	@Before
	public void setUp() {
		ConcurrentHashMap<String,byte[]> map=new ConcurrentHashMap<String,byte[]>();
		String[] args=new String[0];
		System.out.println("Now Billing Test initialization");
		bs =new BillingServer(start.loginMap());

		BillingServerSecure bss = new BillingServerSecure();
		RemoteBillingServerSecure rbss = new RemoteBillingServerSecure(bss);
		bs.initRmi(bs, rbss);
		System.out.println("New Analytics");
		as= new AnalyticsServer();
		new AnalyticTaskComputing(as);
	}

	/**
	 * Shutdown all Server
	 */
	@After
	public void end(){
//		bs.shutdown();
//		as.shutdown();
	}
	/**
	 * tests the login userinput
	 */
	@Test
	public void loginTest(){
		cli=new FakeCli("");
		m=new ManagmentClient(cli);
		cli.write("!login test test");
	}
	/**
	 * tests the login userinput
	 */
	@Test
	public void loginTest1(){
		cli=new FakeCli("!login test test");
		m=new ManagmentClient(cli);
	}
	/**
	 * tests the print userinput
	 */
	@Test
	public void printTest(){
		cli=new FakeCli("");
		m=new ManagmentClient(cli);
		cli.write("!print");
		assertFalse(m.getPrintAutomatic());
	}
	/**
	 * tests the auto userinput
	 */
	@Test
	public void autoTest(){
		cli=new FakeCli("");
		m=new ManagmentClient(cli);
		cli.write("!auto");
		try {
			Thread.sleep(200);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		assertTrue(m.getPrintAutomatic());
	}
	/**
	 * tests the hide userinput
	 */
	@Test
	public void hideTest(){
		cli=new FakeCli("");
		m=new ManagmentClient(cli);
		cli.write("!hide");
		assertFalse(m.getPrintAutomatic());
	}
	/**
	 * tests subscribe
	 */
	@Test
	public void subscribeTest(){
		cli=new FakeCli("");
		m=new ManagmentClient(cli);
//		m.setAnalyticTaskComputing(ats);
//		m.setBillingServer(bs);
		cli.write("!subscribe .*");
	}
	/**
	 * tests unsunscribe
	 */
	@Test
	public void unsubscribeTest(){
		cli=new FakeCli("");
		m=new ManagmentClient(cli);
//		m.setAnalyticTaskComputing(ats);
//		m.setBillingServer(bs);
		cli.write("subscribe .*");
		cli.write("!unsubscribe 0");
	}
	/**
	 * tests unsubscribe with the wrong number of arguments and checks if the errorMessage is the expected one.
	 */
	@Test
	public void unsubscribeExceptionTest(){
		cli=new FakeCli("");
		m=new ManagmentClient(cli);
//		m.setAnalyticTaskComputing(ats);
//		m.setBillingServer(bs);
		cli.write("!unsubscribe");
		try {
			Thread.sleep(200);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		assertEquals("ERROR: Wrong number of arguments given!\nUsage: !unsubscribe <subscriptionID>", cli.getLastOutputM());
	}
	/**
	 * tests subscribe with the wrong number of arguments and checks if the errorMessage is the expected one.
	 */
	@Test
	public void subscribeExceptionCaugthTest(){
		cli=new FakeCli("");
		m=new ManagmentClient(cli);
//		m.setAnalyticTaskComputing(ats);
//		m.setBillingServer(bs);
		cli.write("!subscribe");
		try {
			Thread.sleep(200);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		assertEquals("ERROR: Wrong number of arguments given!\nUsage: !subscribe <filterRegex>", cli.getLastOutputM());
	}
	/**
	 * tests end if no user is logged in
	 */
	@Test
	public void endUnsecureTest(){
		cli=new FakeCli("");
		m=new ManagmentClient(cli);
		cli.write("!end");
	}
	/**
	 * tests if secureCOmmand is created if logged in
	 */
	@Test
	public void secureTest(){
		cli=new FakeCli("");
		m=new ManagmentClient(cli);
		cli.write("!login test test\n!steps");
	}
	/**
	 * tests if logout is exected
	 */
	@Test
	public void logoutTest(){
		cli=new FakeCli("");
		m=new ManagmentClient(cli);
		cli.write("!login test test\n!logout");
	}
	/**
	 * tests end when a user is logged in
	 */
	@Test
	public void endSecureTest(){
		cli=new FakeCli("");
		m=new ManagmentClient(cli);
		cli.write("!login test test\n!end");
	}


}
