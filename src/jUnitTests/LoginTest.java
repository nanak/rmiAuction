package jUnitTests;

import static org.junit.Assert.assertEquals;
import management.Login;

import org.junit.Before;
import org.junit.Test;

import exceptions.WrongNumberOfArgumentsException;

/**
 * Tests the class Login
 * 
 * @author Michaela Lipovits
 * @version 20140221
 */
public class LoginTest {
	private Login cmd;
	/**
	 * instantiated Login
	 */
	@Before
	public void setUp() {
		cmd =new Login();
		
	}
	
	/**
	 * executes login with only 2 arguments, so {@link WrongNumberOfArgumentsException} is thrown
	 * @throws WrongNumberOfArgumentsException
	 */
	@Test(expected=WrongNumberOfArgumentsException.class)
	public void loginWrongNumber() throws WrongNumberOfArgumentsException {
		String[] args= {"!login", "bob"};
		cmd.execute(args);
	}
	/**
	 * tests login with a correct input
	 * @throws WrongNumberOfArgumentsException
	 */
	@Test
	public void LoginOk() throws WrongNumberOfArgumentsException {
		String[] args= {"!login", "bob", "muhkuhli"};	
		cmd.execute(args);
	}
	/**
	 * tests if getname gets the correct username
	 * @throws WrongNumberOfArgumentsException
	 */
	@Test
	public void getNameTest() throws WrongNumberOfArgumentsException{
		String[] args= {"!login", "bob", "muhkuhli"};	
		cmd.execute(args);
		assertEquals("bob", cmd.getName());
	}
}
