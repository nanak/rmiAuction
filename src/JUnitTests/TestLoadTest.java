package JUnitTests;

import java.net.ServerSocket;

import org.junit.Test;

import loadtest.LoadTest;
import server.Server;
import server.ServerStart;
import billing.BillingServer;
import billing.StartBillingServer;
import analytics.AnalyticsServer;

public class TestLoadTest {

	@Test
	public void constTest(){
		String[] args= new String[0];
		AnalyticsServer.main(args);
		StartBillingServer.main(args);
		ServerStart.main(args);
		new LoadTest("localhost", 5000);
	}
}
