package JUnitTests;

import static org.junit.Assert.assertEquals;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import Client.CLI;

public class CLITest {

	private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
	private CLI cli;

	public CLITest(){
		cli = new CLI();
	}

	@Test
	public void testReadLn(){
		ByteArrayInputStream in = new ByteArrayInputStream("test".getBytes());
		System.setIn(in);
		assertEquals("test", cli.readln());
		System.setIn(System.in);
	}
}
