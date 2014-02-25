package jUnitTests;

import management.Bill;

import org.junit.Before;
import org.junit.Test;

import exceptions.WrongNumberOfArgumentsException;
import billing.BillingServerSecure;

/**
 * Tests the Bill class.
 * 
 * @author Michaela Lipovits
 * @version 20140220
 */
public class BillTest {
	private Bill cmd;
	private BillingServerSecure bss;
	/**
	 * instantiates Bill. Creates a billingServerSecure for the execution of the command.
	 */
	@Before
	public void setUp() {
		cmd =new Bill();
		bss=new BillingServerSecure();
	}
	
	/**
	 * Try to create a bill with too many arguments, so it throws {@link WrongNumberOfArgumentsException}.
	 * 
	 * @throws WrongNumberOfArgumentsException
	 */
	@Test(expected=WrongNumberOfArgumentsException.class)
	public void billWrongNumber() throws WrongNumberOfArgumentsException {
		cmd.setBillingServerSecure(bss);
		String[] args= {"!bill", "bob", "1"};
		cmd.execute(args);
	}
	/**
	 * successfully executes the bill-command
	 * 
	 * @throws WrongNumberOfArgumentsException
	 */
	@Test
	public void billOk() throws WrongNumberOfArgumentsException {
		cmd.setBillingServerSecure(bss);
		String[] args= {"!bill", "bob"};	
		cmd.execute(args);
	}

}
