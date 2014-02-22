package JUnitTests;

import static org.junit.Assert.*;
import management.AddStep;
import management.Bill;
import management.Login;

import org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

import Exceptions.PriceStepIntervalOverlapException;
import Exceptions.WrongInputException;
import Exceptions.WrongNumberOfArgumentsException;
import billing.BillingServerSecure;

public class LoginTest {
	private Login cmd;
	@Before
	public void setUp() {
		cmd =new Login();
		
	}
	
	@Test(expected=WrongNumberOfArgumentsException.class)
	public void loginWrongNumber() throws WrongNumberOfArgumentsException {
		String[] args= {"!login", "bob"};
		cmd.execute(args);
	}
	@Test
	public void LoginOk() throws WrongNumberOfArgumentsException {
		String[] args= {"!login", "bob", "muhkuhli"};	
		cmd.execute(args);
	}
	@Test
	public void getNameTest() throws WrongNumberOfArgumentsException{
		String[] args= {"!login", "bob", "muhkuhli"};	
		cmd.execute(args);
		assertEquals("bob", cmd.getName());
	}
}
