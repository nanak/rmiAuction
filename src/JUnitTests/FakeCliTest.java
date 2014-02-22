package JUnitTests;

import static org.junit.Assert.*;
import loadtest.FakeCli;
import management.AddStep;

import org.junit.Before;
import org.junit.Test;

import billing.BillingServerSecure;

public class FakeCliTest {
	@Before
	public void setUp() {

	}
	@Test
	public void readTest(){
		FakeCli cli=new FakeCli("muh");
		assertEquals("muh", cli.readln());
	}
	@Test
	public void writeTest(){
		FakeCli cli=new FakeCli("");
		cli.write("hallo");
		assertEquals("hallo", cli.readln());
	}
	@Test
	public void outTest(){
		FakeCli cli=new FakeCli("");
		cli.out("test");
	}
	@Test
	public void outIDTest(){
		FakeCli cli=new FakeCli("");
		cli.out("ID: 0");
		assertEquals((double)0, (double)cli.getRandomID(), 0);
	}
	@Test
	public void outIDnullTest(){
		FakeCli cli=new FakeCli("");
		assertEquals((double)0, (double)cli.getRandomID(), 0);
	}
	@Test
	public void clientActiveTest(){
		FakeCli cli=new FakeCli("");
		cli.setClientsAlive(true);
		assertTrue(cli.isClientAlive());
	}
}	