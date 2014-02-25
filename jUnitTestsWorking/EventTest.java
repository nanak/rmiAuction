package jUnitTests;

/**
 * Tests alle Events and toString Methods
 */
import static org.junit.Assert.*;

import java.util.Date;

import model.Auction;

import org.junit.Test;

import event.*;

public class EventTest {
	private Event e;
	
	/**
	 * Tests auction Ended to String
	 */
	@Test
	public void testAuctionEndedToString() {
		AuctionEnded ae = new AuctionEnded("1","AUCTION_ENDED",0L,1L);
		assertEquals(ae.toString(), "AUCTION_ENDED: Thu Jan 01 01:00:00 CET 1970 - Auction ended with id 1");
	}
	/**
	 * Tests auction Started to String
	 */
	@Test
	public void testAuctionStartedToString() {
		AuctionStarted ae = new AuctionStarted("1","AUCTION_STARTED",0L,1L);
		assertEquals(ae.toString(), "AUCTION_STARTED: Thu Jan 01 01:00:00 CET 1970 - Auction started with id 1");
	}

	/**
	 * Tests AUCTION_SUCCESS_RATIO to String
	 */
	@Test
	public void testAuctionSuccessToString() {
		AuctionSuccessRatio ae = new AuctionSuccessRatio("1","AUCTION_SUCCESS_RATIO",0L,1L);
		assertEquals(ae.toString(), "AUCTION_SUCCESS_RATIO: Thu Jan 01 01:00:00 CET 1970 - Auction Success Ration: 1.0");
	}
	/**
	 * Tests auction time avg to String
	 */
	@Test
	public void testAuctionTimeAVGToString() {
		AuctionTimeAvg ae = new AuctionTimeAvg("1","AUCTION_TIME_AVG",0L,1L);
		assertEquals(ae.toString(), "AUCTION_TIME_AVG: Thu Jan 01 01:00:00 CET 1970 - Auction Time Average: 0 seconds");
	}
	/**
	 * Tests bidCountperMinute to String
	 */
	@Test
	public void testBidCountPerMinuteToString() {
		BidCountPerMinute ae = new BidCountPerMinute("1","BID_COUNT_PER_MINUTE",0L,1L);
		assertEquals(ae.toString(), "BID_COUNT_PER_MINUTE: Thu Jan 01 01:00:00 CET 1970 - Bids/Minute: 1.0");
	}
	/**
	 * Tests Bid Overbid to String
	 */
	@Test
	public void testBidOverbidToString() {
		BidOverbid ae = new BidOverbid("1","BID_OVERBID",0L,"USER", 1L, 0);
		assertEquals(ae.toString(), "BID_OVERBID: Thu Jan 01 01:00:00 CET 1970 - Bid was overbid on auction 1 with value 0.0");
	}
	/**
	 * Tests Bid Placed to String
	 */
	@Test
	public void testBidPlacedToString() {
		BidPlaced ae = new BidPlaced("1","BID_PLACED",0L,"USER", 1L, 0);
		assertEquals(ae.toString(), "BID_PLACED: Thu Jan 01 01:00:00 CET 1970 - Bid placed on auction 1 with value 0.0");
	}
	/**
	 * Tests Bid WON to String
	 */
	@Test
	public void testBidWonToString() {
		BidWon ae = new BidWon("1","BID_WON",0L,"USER", 1L, 0);
		assertEquals(ae.toString(), "BID_WON: Thu Jan 01 01:00:00 CET 1970 - Bid won on auction: 1 with value 0.0");
	}
	/**
	 * Tests Bid price Max to String
	 */
	@Test
	public void testBidPriceMaxToString() {
		BidPriceMax ae = new BidPriceMax("1","BID_PRICE_MAX", 1L, 0);
		assertEquals(ae.toString(), "BID_PRICE_MAX: Thu Jan 01 01:00:00 CET 1970 - Bid price max seen so far: 0.0");
	}
	/**
	 * Tests UserDisconnected to String
	 */
	@Test
	public void testBUserDisconnectedToString() {
		UserDisconnected ae = new UserDisconnected("1","USER_DISCONNECTED", 1L, "USER");
		assertEquals(ae.toString(), "USER_DISCONNECTED: Thu Jan 01 01:00:00 CET 1970 - User disconnected: USER");
	}
	/**
	 * Tests UserLogin to String
	 */
	@Test
	public void testUserLoginToString() {
		UserLogin ae = new UserLogin("1","USER_LOGIN", 1L, "USER");
		assertEquals(ae.toString(), "USER_LOGIN: Thu Jan 01 01:00:00 CET 1970 - User logged in: USER");
	}
	/**
	 * Tests UserLogout to String
	 */
	@Test
	public void testUserLogoutToString() {
		UserLogout ae = new UserLogout("1","USER_LOGOUT", 1L, "USER");
		assertEquals(ae.toString(), "USER_LOGOUT: Thu Jan 01 01:00:00 CET 1970 - User logged out: USER");
	}
	/**
	 * Tests User SESSION TIME AVG to String
	 */
	@Test
	public void testUserSessionAVGToString() {
		UserSessionTimeAvg ae = new UserSessionTimeAvg("1","USER_SESSION_TIME_AVG", 1L, 10);
		assertEquals(ae.toString(), "USER_SESSION_TIME_AVG: Thu Jan 01 01:00:00 CET 1970 - User Session Time Average: 0 seconds");
	}
	/**
	 * Tests User SESSION TIME MAX to String
	 */
	@Test
	public void testUserSessionMaxToString() {
		UserSessionTimeMax ae = new UserSessionTimeMax("1","USER_SESSION_TIME_AVG", 1L, 10);
		assertEquals(ae.toString(), "USER_SESSION_TIME_AVG: Thu Jan 01 01:00:00 CET 1970 - User Session Time Max: 0 seconds");
	}
	/**
	 * Tests User SESSION TIME AVG to String
	 */
	@Test
	public void testUserSessionMinToString() {
		UserSessionTimeMin ae = new UserSessionTimeMin("1","USER_SESSION_TIME_AVG", 1L, 10);
		assertEquals(ae.toString(), "USER_SESSION_TIME_AVG: Thu Jan 01 01:00:00 CET 1970 - User Session Time Min: 0 seconds");
	}
}
