package jUnitTests;

import static org.junit.Assert.assertEquals;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import client.CLI;
/**
 * Tests all functions of the CLI
 * 
 * @author Nanak Tattyrek
 * @version 23.02.2014
 * @email ntattyrek@student.tgm.ac.at
 *
 */
public class CLITest {

	private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();

	private CLI cli;

	public CLITest(){
		cli = new CLI();
	}


	/**
	 * gets executed before every test method
	 * sets the sysout to a printstream so System.out.println() can be tested
	 */
	@Before
	public void setUpStreams() {
	    System.setOut(new PrintStream(outContent));
	}

	/**
	 * gets executed after every test method
	 */
	@After
	public void cleanUpStreams() {
//	    System.setOut(null);
	}

	/**
	 * tests if the out() method works properly
	 */
	@Test
	public void testOut() {
		String newline = System.getProperty("line.separator");
	    cli.out("test");
	    assertEquals("test"+newline, outContent.toString());
	}
	
	/**
	 * tests if the outLn() method works properly
	 */
	@Test
	public void testOutLn() {
	    cli.outln("test");
	    assertEquals("test", outContent.toString());
	}
	
	/**
	 * tests if the readLn() works properly
	 */
	@Test
	public void testReadLn(){
		ByteArrayInputStream in = new ByteArrayInputStream("test".getBytes());
		System.setIn(in);
		assertEquals("test", cli.readln());
		System.setIn(System.in);
	}
}
