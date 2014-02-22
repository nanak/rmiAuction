package JUnitTests;

import management.AddStep;
import management.Bill;
import management.Logout;

import org.junit.Before;
import org.junit.Test;

import Exceptions.PriceStepIntervalOverlapException;
import Exceptions.WrongInputException;
import Exceptions.WrongNumberOfArgumentsException;
import billing.BillingServerSecure;

public class LogoutTest {
	private Logout cmd;
	private BillingServerSecure bss;
	@Before
	public void setUp() {
		cmd =new Logout();
		bss=new BillingServerSecure();
		
	}
	
	@Test(expected=WrongNumberOfArgumentsException.class)
	public void logoutlWrongNumber() throws WrongNumberOfArgumentsException {
		cmd.setBillingServerSecure(bss);
		String[] args= {"!logout","bob","muh"};
		cmd.execute(args);
	}
	@Test
	public void logoutOk() throws WrongNumberOfArgumentsException {
		cmd.setBillingServerSecure(bss);
		String[] args= {"!logout", "bob"};	
		cmd.execute(args);
	}

}
