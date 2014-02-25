package jUnitTests;

import static org.junit.Assert.assertEquals;

import java.util.Timer;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import loadtest.CreateTask;
import loadtest.FakeCli;
import loadtest.LoadingComponent;
import management.ManagmentClient;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import client.Client;
import client.TaskExecuter;
import server.Server;
import analytics.AnalyticTaskComputing;
import analytics.AnalyticsServer;
import billing.BillingServer;
import billing.BillingServerSecure;
import billing.RemoteBillingServerSecure;
import billing.StartBillingServer;
import connect.ReceiveConnection;

/**
 * Tests the {@link LoadingComponent} class
 * @author Michaela Lipovits
 * @version 20140221
 */
public class Loading {
	private LoadingComponent l;
	
	/**
	 * starts analyticsserver and billingserver as their mains would
	 */
	@Before
	public void setUp() {
	}
	/**
	 * tests the constructoe of LoadTest
	 */
	@Test
	public void constTest(){
		l = new LoadingComponent("localhost", 6000, "loadtest.properties",5000);
	}

	/**
	 * Tests if the randomString method creates the string with the correct length
	 */
	@Test
	public void randomStringTest(){
		LoadingComponent l = new LoadingComponent("localhost", 6000, "loadtest.properties",5000);
		
		String r=l.randomString(20);
		try {
			Thread.sleep(10000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		assertEquals(20, r.length(), 0);
	}

}
