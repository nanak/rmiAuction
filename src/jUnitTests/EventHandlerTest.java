package jUnitTests;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import event.AuctionEnded;
import event.AuctionStarted;
import event.AuctionSuccessRatio;
import event.AuctionTimeAvg;
import event.BidCountPerMinute;
import event.BidPlaced;
import event.BidPriceMax;
import event.Event;
import event.UserLogin;
import event.UserLogout;
import analytics.AnalyticsServer;
import analytics.EventHandler;

/**
 * Tests the EventHandler, if it processes the events right.
 * The executor has to wait to get the event notifications, because it is an external Thread
 * 
 * @author Daniel Reichmann
 *
 */
public class EventHandlerTest {

	private EventHandler eh;
	private AnalyticsServer dummyAs;
	private MockClientInterface ci;
	
	/**
	 * Creates a dummy analyticsServer and subscribe with a mock-interface
	 */
	@Before
	public void setup(){
		dummyAs = new AnalyticsServer();
		ci = new MockClientInterface();
		dummyAs.subscribe("(BID_.*|USER_.*|AUCTION_.*)", ci);
		try {
			Thread.sleep(500);
		} catch (InterruptedException e) {
			
			e.printStackTrace();
		}
	}
	/**
	 * Shutsdown all Servers
	 */
	@After
	public void end(){
		dummyAs.shutdown();
	}
	
	/**
	 * Tests to place a bid because 1 bid is placed in one minute. Thread has to wait one Minute.
	 * Also the highest bid shall be updated to 100
	 */
	@Test
	public void testPlaceBidAndBPM(){
		try {
			Thread.sleep(500);
		} catch (InterruptedException e1) {
			
			e1.printStackTrace();
		}
		Date d = new Date();
		AuctionStarted as = new AuctionStarted("Auction1", "AUCTION_STARTED", d.getTime(), 1);
		dummyAs.processEvent(as);
		BidPlaced bp = new BidPlaced("Bid1", "BID_PLACED", d.getTime(), "User1", 1, 100);
		dummyAs.processEvent(bp);
		try {
			Thread.sleep(60000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		//Tests the Events
		ArrayList<Event> al = ci.getEvents();
		
		Iterator<Event> it = al.iterator();
		it.next(); //Skip Auction Started
		//Next Event should be BidPlaced
		assertEquals("BID_PLACED", it.next().getType());
		//NExt event is new BidPriceMax -> 100
		BidPriceMax bpmax = (BidPriceMax) it.next();
		assertEquals(bpmax.getValue(), 100,0);
		//NextEvent is BidCount perMinute
		BidCountPerMinute bpm = (BidCountPerMinute) it.next();
		assertEquals(bpm.getValue(), 1, 0);

	}
	/**
	 * Tests if a UserLoginEvent is processed correctly and put into the dispatcher Queue
	 */
	@Test
	public void testUserLoginEvent() {
		Date d = new Date();
		UserLogin ul = new UserLogin("1", "USER_LOGIN", d.getTime(), "Testuser");
		dummyAs.processEvent(ul);
		//Wait because it is in another thread
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		//Tests the Events
		ArrayList<Event> al = ci.getEvents();
		Iterator<Event> it = al.iterator();
		//Event shall now be in DispatchEvents
		Event comp = it.next();
		assertEquals(comp, ul);
	}
	
	/**
	 * Tests if a UserLogin and UserLogout ends in 5 new Events:
	 * UserLogin, UserLogout, SessionTimeMin, SessionTimeMax, SessionTimeAVG 
	 */
	@Test
	public void testUserLoginLogoutSessionEvent() {
		Date d = new Date();
		UserLogin ul = new UserLogin("1", "USER_LOGIN", d.getTime(), "Testuser");
		dummyAs.processEvent(ul);
		//Wait because it is in another thread
		try {
			Thread.sleep(100);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		//EventQueue empty, log user Out
		d = new Date();
		UserLogout uout = new UserLogout("2", "USER_LOGOUT", d.getTime(), "Testuser");
		dummyAs.processEvent(uout);
		try {
			Thread.sleep(200);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		//Test the Events:
		ArrayList<Event> events = ci.getEvents();
		Iterator<Event> it = events.iterator();
		assertEquals("USER_LOGIN", it.next().getType());
		assertEquals("USER_LOGOUT", it.next().getType());
		assertEquals("USER_SESSIONTIME_MAX", it.next().getType());
		assertEquals("USER_SESSIONTIME_MIN", it.next().getType());
		assertEquals("USER_SESSIONTIME_AVG", it.next().getType());
	}
	/**
	 * Tests if an auction is started an Ended, if i get a successRatio 0 and an AverageTime of 50ms
	 */
	@Test
	public void testAuctionStartedEndedNoBid(){
		Date d = new Date();
		AuctionStarted as = new AuctionStarted("Auction1", "AUCTION_STARTED", d.getTime(), 1);
		dummyAs.processEvent(as);
		try {
			Thread.sleep(400);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		AuctionEnded ae = new AuctionEnded("Auction2", "AUCTION_ENDED", d.getTime()+50, 1);
		dummyAs.processEvent(ae);
		//Wait for processing
		try {
			Thread.sleep(400);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		//Tests the Events
		ArrayList<Event> al = ci.getEvents();
		Iterator<Event> it = al.iterator();
		//AuctionStart
		assertEquals("AUCTION_STARTED", it.next().getType());
		assertEquals("AUCTION_ENDED", it.next().getType());
		//AuctionTime Average should be 50
		AuctionTimeAvg atavg = (AuctionTimeAvg) it.next();
		assertEquals(atavg.getValue(), 50,0);
		//AuctionSuccessRatio should be 0
		AuctionSuccessRatio asuc = (AuctionSuccessRatio) it.next();
		assertEquals(asuc.getValue(), 0,0);
	}

	/**
	 * Test if the auctionSuccessFullRatio will be 1, if there is a bid placed on it
	 */
	@Test
	public void testBidOnAuctionChangesRatio(){
		Date d = new Date();
		AuctionStarted as = new AuctionStarted("Auction1", "AUCTION_STARTED", d.getTime(), 1);
		dummyAs.processEvent(as);
		BidPlaced bp = new BidPlaced("AuctionBid1", "BID_PLACED", d.getTime(), "Daniel", 1, 100);
		dummyAs.processEvent(bp);
		try {
			Thread.sleep(200);
		} catch (InterruptedException e1) {
			e1.printStackTrace();
		}
		AuctionEnded ae = new AuctionEnded("Auction2", "AUCTION_ENDED", d.getTime()+50, 1);
		dummyAs.processEvent(ae);
		//Wait for processing
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		//Tests the Events
		ArrayList<Event> al = ci.getEvents();
		Iterator<Event> it = al.iterator();
		//AuctionStart
		assertEquals("AUCTION_STARTED", it.next().getType());
		//First Event should be BidPlaced
		assertEquals("BID_PLACED", it.next().getType());
		//NExt event is new BidPriceMax -> 100
		BidPriceMax bpmax = (BidPriceMax) it.next();
		assertEquals(bpmax.getValue(), 100,0);
		assertEquals("AUCTION_ENDED", it.next().getType());
		//AuctionTime Average should be 50
		AuctionTimeAvg atavg = (AuctionTimeAvg) it.next();
		assertEquals(atavg.getValue(), 50,0);
		//AuctionSuccessRatio should be 1
		AuctionSuccessRatio asuc = (AuctionSuccessRatio) it.next();
		assertEquals(asuc.getValue(), 1,0);
	}

}
