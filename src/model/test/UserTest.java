/**
 * 
 */
package model.test;

import static org.junit.Assert.*;

import java.util.ArrayList;

import model.Auction;
import model.User;

import org.junit.Before;
import org.junit.Test;

/**
 * @author Tobias Schuschnig <tschuschnig@student.tgm.ac.at>
 *
 */
public class UserTest {
	private User owner;

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		owner = new User();
		owner.setActive(true);
		owner.setAdresse("123");
		owner.setName("owner");
		owner.setMessages(new ArrayList<String>());
		owner.setTcpPort(123);
		owner.setUdpPort(123);
	}

	/**
	 * Test method for {@link model.User#getName()}.
	 */
	@Test
	public void testGetName() {
		assertEquals(owner.getName(),"owner");
	}


	/**
	 * Test method for {@link model.User#getAdresse()}.
	 */
	@Test
	public void testGetAdresse() {
		assertEquals(owner.getAdresse(),"123");
	}

	/**
	 * Test method for {@link model.User#getUdpPort()}.
	 */
	@Test
	public void testGetUdpPort() {
		assertEquals(owner.getUdpPort(),123);
	}

	/**
	 * Test method for {@link model.User#getTcpPort()}.
	 */
	@Test
	public void testGetTcpPort() {
		assertEquals(owner.getTcpPort(),123);
	}

	/**
	 * Test method for {@link model.User#isActive()}.
	 */
	@Test
	public void testIsActive() {
		assertEquals(owner.isActive(),true);
	}

	/**
	 * Test method for {@link model.User#setActive(boolean)}.
	 */
	@Test
	public void testSetActive() {
		owner.setActive(false);
		assertEquals(owner.isActive(),false);
	}

	/**
	 * Test method for {@link model.User#getMessages()}.
	 */
	@Test
	public void testGetMessages() {
		assertEquals(owner.getMessages(),new ArrayList<String>());
	}

	/**
	 * Test method for {@link model.User#equals(java.lang.Object)}.
	 */
	@Test
	public void testEqualsObject() {
		User owner2 = new User();
		owner2.setActive(true);
		owner2.setAdresse("123");
		owner2.setName("owner2");
		owner2.setMessages(new ArrayList<String>());
		owner2.setTcpPort(123);
		owner2.setUdpPort(123);
		Boolean bool = owner.equals(owner2);
		assertEquals(bool,false);
	}

}
