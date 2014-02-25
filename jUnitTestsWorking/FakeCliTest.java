package jUnitTests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import loadtest.FakeCli;

import org.junit.Before;
import org.junit.Test;

/**
 * Tests the class FakeCli
 * @author Michaela Lipovits
 * @version 20140220
 */
public class FakeCliTest {
	@Before
	public void setUp() {

	}
	/**
	 * tests if the input is read correctly
	 */
	@Test
	public void readTest(){
		FakeCli cli=new FakeCli("muh");
		assertEquals("muh", cli.readln());
	}
	/**
	 * tests if the write method works correctly
	 */
	@Test
	public void writeTest(){
		FakeCli cli=new FakeCli("");
		cli.write("hallo");
		assertEquals("hallo", cli.readln());
	}
	/**
	 * tests the out method
	 */
	@Test
	public void outTest(){
		FakeCli cli=new FakeCli("");
		cli.out("test");
	}
	/**
	 * tests if out saves id's correctly.
	 */
	@Test
	public void outIDTest(){
		FakeCli cli=new FakeCli("");
		cli.out("ID: 0");
		assertEquals((double)0, (double)cli.getRandomID(), 0);
	}
	/**
	 * Tests if out return 0 is arraylist is empty
	 */
	@Test
	public void outIDnullTest(){
		FakeCli cli=new FakeCli("");
		assertEquals((double)0, (double)cli.getRandomID(), 0);
	}
	/**
	 * tests if the set/getClientActive work
	 */
	@Test
	public void clientActiveTest(){
		FakeCli cli=new FakeCli("");
		cli.setClientsAlive(true);
		assertTrue(cli.isClientAlive());
	}
}	