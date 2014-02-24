package JUnitTests;

import management.Logout;

import org.junit.Before;
import org.junit.Test;

import Exceptions.WrongNumberOfArgumentsException;
import billing.BillingServerSecure;

/**
 * Tests the class Logout
 * @author Michaela Lipovits
 * @version 2014
 */
public class LogoutTest {
	private Logout cmd;
	private BillingServerSecure bss;
	/**
	 * instantiates logout
	 */
	@Before
	public void setUp() {
		cmd =new Logout();
		bss=new BillingServerSecure();
		
	}
	
	/**
	 * executes logout with 3 arguments, so {@link WrongNumberOfArgumentsException} is thrown.
	 * @throws WrongNumberOfArgumentsException
	 */
	@Test(expected=WrongNumberOfArgumentsException.class)
	public void logoutlWrongNumber() throws WrongNumberOfArgumentsException {
		cmd.setBillingServerSecure(bss);
		String[] args= {"!logout","bob","muh"};
		cmd.execute(args);
	}
	/**
	 * executes the logout with correct inpput
	 * @throws WrongNumberOfArgumentsException
	 */
	@Test
	public void logoutOk() throws WrongNumberOfArgumentsException {
		cmd.setBillingServerSecure(bss);
		String[] args= {"!logout", "bob"};	
		cmd.execute(args);
	}

}
