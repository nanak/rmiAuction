/**
 * 
 */
package model.test;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Date;

import model.Auction;
import model.User;

import org.junit.Before;
import org.junit.Test;

/**
 * @authorTobias Schuschnig <tschuschnig@student.tgm.ac.at>
 *
 */
public class AuctionTest {
	private Auction auction;
	
	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		User owner = new User();
		owner.setActive(true);
		owner.setAdresse("123");
		owner.setName("owner");
		owner.setMessages(new ArrayList<String>());
		owner.setTcpPort(123);
		owner.setUdpPort(123);
		auction = new Auction(owner,"auction",10L);
		auction = new Auction(owner,"auction",10L,0);
	}

	/**
	 * Test method for {@link model.Auction#isActive()}.
	 */
	@Test
	public void testIsActive() {
		assertEquals(auction.isActive(),false);
	}

	/**
	 * Test method for {@link model.Auction#getId()}.
	 */
	@Test
	public void testGetId() {
		assertEquals(auction.getId(),0);
	}

	/**
	 * Test method for {@link model.Auction#setId(int)}.
	 */
	@Test
	public void testSetId() {
		auction.setId(1);
		assertEquals(auction.getId(),1);
	}


	/**
	 * Test method for {@link model.Auction#getLastUser()}.
	 */
	@Test
	public void testGetLastUser() {
		assertEquals(auction.getLastUser(),null);
	}

	/**
	 * Test method for {@link model.Auction#setLastUser(model.User)}.
	 */
	@Test
	public void testSetLastUser() {
		User lastuser = new User();
		lastuser.setActive(true);
		lastuser.setAdresse("123");
		lastuser.setMessages(new ArrayList<String>());
		lastuser.setName("lastUser");
		lastuser.setTcpPort(123);
		lastuser.setUdpPort(123);
		auction.setLastUser(lastuser);
		assertEquals(auction.getLastUser(),lastuser);
	}

	/**
	 * Test method for {@link model.Auction#getOwner()}.
	 */
	@Test
	public void testGetOwner() {
		assertEquals(auction.getOwner().getName(),"owner");
	}

	/**
	 * Test method for {@link model.Auction#setOwner(model.User)}.
	 */
	@Test
	public void testSetOwner() {
		User owner = new User();
		owner.setActive(true);
		owner.setAdresse("123");
		owner.setName("owner2");
		owner.setMessages(new ArrayList<String>());
		owner.setTcpPort(123);
		owner.setUdpPort(123);
		auction.setOwner(owner);
		assertEquals(auction.getOwner().getName(),"owner2");
	}

	/**
	 * Test method for {@link model.Auction#getDescription()}.
	 */
	@Test
	public void testGetDescription() {
		assertEquals(auction.getDescription(), "auction");
	}

	/**
	 * Test method for {@link model.Auction#setDescription(java.lang.String)}.
	 */
	@Test
	public void testSetDescription() {
		auction.setDescription("auction2");
		assertEquals(auction.getDescription(),"auction2");
	}

	/**
	 * Test method for {@link model.Auction#getDeadline()}.
	 */
	@Test
	public void testGetDeadline() {
		Date date = auction.getDeadline();
		assertEquals(auction.getDeadline(),date);
	}

	/**
	 * Test method for {@link model.Auction#setDeadline(java.util.Date)}.
	 */
	@Test
	public void testSetDeadline() {
		auction.setDeadline(auction.getDeadline());
		Date date = auction.getDeadline();
		assertEquals(auction.getDeadline(),date);
	}

	/**
	 * Test method for {@link model.Auction#isFinished()}.
	 */
	@Test
	public void testIsFinished() {
		auction.setFinished(true);
		assertEquals(auction.isFinished(),true);
	}

	/**
	 * Test method for {@link model.Auction#setFinished(boolean)}.
	 */
	@Test
	public void testSetFinished() {
		auction.setFinished(true);
		assertEquals(auction.isFinished(),true);;
	}

}
