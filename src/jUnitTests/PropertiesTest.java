package jUnitTests;

import static org.junit.Assert.assertEquals;

import java.io.FileNotFoundException;
import java.io.IOException;

import loadtest.Properties;

import org.junit.Before;
import org.junit.Test;

/**
 * Tests the Properties class
 * 
 * @author Michaela Lipovits
 * @version 20140221
 */
public class PropertiesTest {
	private Properties p;

	/**
	 * instantiates Properties
	 */
	@Before
	public void setUp() {
		p=new Properties();	
	}
	/**
	 * tests setFromFile with an existant path
	 * @throws IOException
	 */
	@Test
	public void setFromFileTest() throws IOException{
		p.setFromFile("loadtest.properties");
	}
	/**
	 * tests setFromFile with a non-existant path, so {@link FileNotFoundException} is thrown.
	 * @throws IOException
	 */
	@Test(expected=FileNotFoundException.class)
	public void setFromFileErrorTest() throws IOException{
		p.setFromFile("iwo/loadtest.properties");
	}
	/**
	 * tests all setters and getters
	 */
	@Test
	public void setAllTest(){
		p.setAuctionDuration(10);
		p.setAuctionsPerMin(10);
		p.setBidsPerMin(10);
		p.setClients(10);
		p.setUpdateIntervalSec(10);
		assertEquals("Clients: 10\nAuctions per minute: 10\nAuction duration: 10\nUpdate interval in seconds: 10\nBids per minute: 10", p.toString());
		assertEquals((double)10, (double)p.getAuctionDuration(), 0);
		assertEquals((double)10, (double)p.getAuctionsPerMin(), 0);
		assertEquals((double)10, (double)p.getBidsPerMin(), 0);
		assertEquals((double)10, (double)p.getClients(), 0);
		assertEquals((double)10, (double)p.getUpdateIntervalSec(), 0);
	}

}
