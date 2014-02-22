package JUnitTests;

import management.AddStep;
import management.Bill;

import org.junit.Before;
import org.junit.Test;

import Exceptions.PriceStepIntervalOverlapException;
import Exceptions.WrongInputException;
import Exceptions.WrongNumberOfArgumentsException;
import billing.BillingServerSecure;

public class BillTest {
	private Bill cmd;
	private BillingServerSecure bss;
	@Before
	public void setUp() {
		cmd =new Bill();
		bss=new BillingServerSecure();
	}
	
	@Test(expected=WrongNumberOfArgumentsException.class)
	public void billWrongNumber() throws WrongNumberOfArgumentsException {
		cmd.setBillingServerSecure(bss);
		String[] args= {"!bill", "bob", "1"};
		cmd.execute(args);
	}
	@Test
	public void billOk() throws WrongNumberOfArgumentsException {
		cmd.setBillingServerSecure(bss);
		String[] args= {"!bill", "bob"};	
		cmd.execute(args);
	}

}
