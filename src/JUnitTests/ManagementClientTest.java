package JUnitTests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.rmi.NoSuchObjectException;
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
		bs =new BillingServer();

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
			bs.shutdown();
			as.shutdown();
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
	 * tests a wrong login userinput
	 */
	@Test
	public void loginWrongPwTest(){
		cli=new FakeCli("!login test muh");
		m=new ManagmentClient(cli);
		try {
			Thread.sleep(200);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		assertEquals("Wrong password!", cli.getLastOutputM());
	}
	/**
	 * tests the login when billingserver is down
	 */
	@Test
	public void loginNoBillingTest(){
		bs.shutdown();
		cli=new FakeCli("");
		m=new ManagmentClient(cli);
		cli.write("!login test test");
		try {
			Thread.sleep(200);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		assertEquals("ERROR: Connection to BillingServer lost. You have to login again!", cli.getLastOutputM());
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
	 * tests subscribe while analytics down
	 */
	@Test
	public void subscribNoAnalyticeTest(){
		as.shutdown();
		cli=new FakeCli("");
		m=new ManagmentClient(cli);
//		m.setAnalyticTaskComputing(ats);
//		m.setBillingServer(bs);
		cli.write("!subscribe .*");
		try {
			Thread.sleep(200);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		assertEquals("ERROR: AnalyticsServer not available right now. Retry after starting Analytics", cli.getLastOutputM());
	}
	/**
	 * tests subscribe when analytics back up
	 */
	@Test
	public void subscrieAnalyticsBackTest(){
		
		cli=new FakeCli("");
		m=new ManagmentClient(cli);
		try {
			Thread.sleep(200);
		} catch (InterruptedException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		as.shutdown();
		try {
			Thread.sleep(200);
		} catch (InterruptedException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}
		as= new AnalyticsServer();
		new AnalyticTaskComputing(as);
		try {
			Thread.sleep(200);
		} catch (InterruptedException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		cli.write("!subscribe .*");
	}
	/**
	 * tests unsubscribe when analytics back up
	 */
	@Test
	public void unsubscrieAnalyticsBackTest(){
		
		cli=new FakeCli("");
		m=new ManagmentClient(cli);
		try {
			Thread.sleep(200);
		} catch (InterruptedException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		as.shutdown();
		try {
			Thread.sleep(200);
		} catch (InterruptedException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}
		as= new AnalyticsServer();
		new AnalyticTaskComputing(as);
		try {
			Thread.sleep(200);
		} catch (InterruptedException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		cli.write("!unsubscribe 0");
	}
	/**
	 * tests unsubscribe while analytics down
	 */
	@Test
	public void unsubscribNoAnalyticeTest(){
		as.shutdown();
		cli=new FakeCli("");
		m=new ManagmentClient(cli);
//		m.setAnalyticTaskComputing(ats);
//		m.setBillingServer(bs);
		cli.write("!unsubscribe 0");
		try {
			Thread.sleep(200);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		assertEquals("ERROR: AnalyticsServer not available right now. Retry after starting Analytics", cli.getLastOutputM());
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
		try {
			Thread.sleep(200);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		assertEquals("Management Client is shutting down!", cli.getLastOutputM());
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
	 * tests secureCOmmand when billing server is down
	 */
	@Test
	public void logoutNoBillingTest(){
		cli=new FakeCli("");
		m=new ManagmentClient(cli);
		cli.write("!login test test");
		try {
			Thread.sleep(200);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		bs.shutdown();
		cli.write("!logout");
		try {
			Thread.sleep(200);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		assertEquals("ERROR: Connection to BillingServer lost. You have to login again!", cli.getLastOutputM());
	}

	/**
	 * tests if logout is executed
	 */
	@Test
	public void logoutTest(){
		cli=new FakeCli("");
		m=new ManagmentClient(cli);
		cli.write("!login test test");
		try {
			Thread.sleep(200);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		cli.write("!logout");
	}
	/**
	 * tests logout if billing server is down
	 */
	@Test
	public void secureNoBillingTest(){
		cli=new FakeCli("");
		m=new ManagmentClient(cli);
		cli.write("!login test test");
		try {
			Thread.sleep(200);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		bs.shutdown();
		try {
			Thread.sleep(200);
		} catch (InterruptedException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		cli.write("!steps");
		try {
			Thread.sleep(200);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		assertEquals("ERROR: Connection to BillingServer lost. You have to login again!", cli.getLastOutputM());
	}
	/**
	 * tests end when a user is logged in
	 */
	@Test
	public void endSecureTest(){
		cli=new FakeCli("");
		m=new ManagmentClient(cli);
		cli.write("!login test test\n!end");
		try {
			Thread.sleep(200);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		assertEquals("Management Client is shutting down!", cli.getLastOutputM());
	}
	/**
	 * tests end when a user is logged in
	 */
	@Test
	public void endSecureNoBillingTest(){
		cli=new FakeCli("");
		m=new ManagmentClient(cli);
		cli.write("!login test test");
		try {
			Thread.sleep(200);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		bs.shutdown();
		cli.write("!end");      
		try {
			Thread.sleep(200);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		assertEquals("ERROR: Connection to BillingServer lost. You have to login again!", cli.getPreLastOutputM());
	}
	/**
	 * tests nonsense input
	 */
	@Test
	public void nonsenseTest(){
		cli=new FakeCli("");
		m=new ManagmentClient(cli);
		cli.write("!muh");
		try {
			Thread.sleep(200);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		assertEquals("ERROR: This Command does not exist!\nAllowed commands:\n!login <username> <password>\n"
				+ "!logout\n!steps\n!addStep <startPrice> <endPrice> <fixedPrice> <variablePricePercent>\n"
				+ "!removeStep <startPrice> <endPrice>\n!bill <userName>\n!subscribe <filterRegex>\n"
				+ "!unsubscribe <subscriptionID>\n!print\n!auto\n!hide", cli.getLastOutputM());
	}

}
