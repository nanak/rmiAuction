package JUnitTests;

import static org.junit.Assert.*;

import java.io.FileNotFoundException;
import java.io.IOException;

import loadtest.Properties;
import management.AddStep;

import org.junit.Before;
import org.junit.Test;

import billing.BillingServerSecure;

public class PropertiesTest {
	private Properties p;

	@Before
	public void setUp() {
		p=new Properties();	
	}
	@Test
	public void setFromFileTest() throws IOException{
		p.setFromFile("loadtest.properties");
	}
	@Test(expected=FileNotFoundException.class)
	public void setFromFileErrorTest() throws IOException{
		p.setFromFile("iwo/loadtest.properties");
	}
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
