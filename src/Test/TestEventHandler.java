package Test;

import static org.junit.Assert.*;

import java.util.Date;

import org.junit.Before;
import org.junit.Test;

import Event.AuctionEnded;
import Event.AuctionStarted;
import Event.AuctionSuccessRatio;
import Event.AuctionTimeAvg;
import Event.BidCountPerMinute;
import Event.BidPlaced;
import Event.BidPriceMax;
import Event.Event;
import Event.UserLogin;
import Event.UserLogout;
import analytics.AnalyticsServer;
import analytics.EventHandler;

/**
 * Tests the EventHandler, if it processes the events right.
 * The executor has to wait to get the event notifications, because it is an external Thread
 * 
 * @author Daniel Reichmann
 *
 */
public class TestEventHandler {

	private EventHandler eh;
	private AnalyticsServer dummyAs;
	
	@Before
	public void setup(){
		dummyAs = new AnalyticsServer();
		eh = new EventHandler(dummyAs);
		Thread t = new Thread(eh);
		t.start();
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
			Thread.sleep(50);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//Event shall now be in DispatchEvents
		Event comp = dummyAs.getDispatchedEvents().poll();
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
			Thread.sleep(20);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//Event shall now be in DispatchEvents
		Event comp = dummyAs.getDispatchedEvents().poll();
		assertEquals(comp, ul);
		
		//EventQueue empty, log user Out
		d = new Date();
		UserLogout uout = new UserLogout("2", "USER_LOGOUT", d.getTime(), "Testuser");
		dummyAs.processEvent(uout);
		try {
			Thread.sleep(100);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//Test the Events:
		assertEquals("USER_LOGOUT", dummyAs.getDispatchedEvents().poll().getType());
		assertEquals("USER_SESSIONTIME_MAX", dummyAs.getDispatchedEvents().poll().getType());
		assertEquals("USER_SESSIONTIME_MIN", dummyAs.getDispatchedEvents().poll().getType());
		assertEquals("USER_SESSIONTIME_AVG", dummyAs.getDispatchedEvents().poll().getType());
	}
	/**
	 * Tests if an auction is started an Ended, if i get a successRatio 0 and an AverageTime of 50ms
	 */
	@Test
	public void testAuctionStartedEndedNoBid(){
		Date d = new Date();
		AuctionStarted as = new AuctionStarted("Auction1", "AUCTION_STARTED", d.getTime(), 1);
		dummyAs.processEvent(as);
		AuctionEnded ae = new AuctionEnded("Auction2", "AUCTION_ENDED", d.getTime()+50, 1);
		dummyAs.processEvent(ae);
		//Wait for processing
		try {
			Thread.sleep(100);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//Tests the Events
		//AuctionStart
		assertEquals("AUCTION_STARTED", dummyAs.getDispatchedEvents().poll().getType());
		assertEquals("AUCTION_ENDED", dummyAs.getDispatchedEvents().poll().getType());
		//AuctionTime Average should be 50
		AuctionTimeAvg atavg = (AuctionTimeAvg) dummyAs.getDispatchedEvents().poll();
		assertEquals(atavg.getValue(), 50,0);
		//AuctionSuccessRatio should be 0
		AuctionSuccessRatio asuc = (AuctionSuccessRatio) dummyAs.getDispatchedEvents().poll();
		assertEquals(asuc.getValue(), 0,0);
	}
	/**
	 * Tests to place a bid and calculate bidcount per minute, should be 1, because 1 bid is placed in one minute.
	 * Also the highest bid shall be updated to 100
	 */
	@Test
	public void testPlaceBidAndBPM(){
		Date d = new Date();
		AuctionStarted as = new AuctionStarted("Auction1", "AUCTION_STARTED", d.getTime(), 1);
		BidPlaced bp = new BidPlaced("Bid1", "BID_PLACED", d.getTime(), "User1", 1, 100);
		dummyAs.processEvent(bp);
		try {
			Thread.sleep(20);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//First Event should be BidPlaced
		assertEquals("BID_PLACED", dummyAs.getDispatchedEvents().poll().getType());
		//NExt event is new BidPriceMax -> 100
		BidPriceMax bpmax = (BidPriceMax) dummyAs.getDispatchedEvents().poll();
		assertEquals(bpmax.getValue(), 100,0);
		//Next event BPM -> 1
		BidCountPerMinute bpm = (BidCountPerMinute) dummyAs.getDispatchedEvents().poll();
		assertEquals(bpm.getValue(), 1,0);
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
		AuctionEnded ae = new AuctionEnded("Auction2", "AUCTION_ENDED", d.getTime()+50, 1);
		dummyAs.processEvent(ae);
		//Wait for processing
		try {
			Thread.sleep(100);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//Tests the Events
		//AuctionStart
		assertEquals("AUCTION_STARTED", dummyAs.getDispatchedEvents().poll().getType());
		//First Event should be BidPlaced
		assertEquals("BID_PLACED", dummyAs.getDispatchedEvents().poll().getType());
		//NExt event is new BidPriceMax -> 100
		BidPriceMax bpmax = (BidPriceMax) dummyAs.getDispatchedEvents().poll();
		assertEquals(bpmax.getValue(), 100,0);
		//Next event BPM -> 1
		BidCountPerMinute bpm = (BidCountPerMinute) dummyAs.getDispatchedEvents().poll();
		assertEquals(bpm.getValue(), 1,0);
		assertEquals("AUCTION_ENDED", dummyAs.getDispatchedEvents().poll().getType());
		//AuctionTime Average should be 50
		AuctionTimeAvg atavg = (AuctionTimeAvg) dummyAs.getDispatchedEvents().poll();
		assertEquals(atavg.getValue(), 50,0);
		//AuctionSuccessRatio should be 1
		AuctionSuccessRatio asuc = (AuctionSuccessRatio) dummyAs.getDispatchedEvents().poll();
		assertEquals(asuc.getValue(), 1,0);
	}
}
