package jUnitTests;

import management.Steps;

import org.junit.Before;
import org.junit.Test;

import exceptions.WrongNumberOfArgumentsException;
import billing.BillingServerSecure;

/**
 * Tests the {@link Steps} Class
 * @author Michaela Lipovits
 * @version 20140221
 */
public class StepsTest {
	private Steps cmd;
	private BillingServerSecure bss;
	/**
	 * Instantiates {@link Steps} and {@link BillingServerSecure}
	 */
	@Before
	public void setUp() {
		cmd =new Steps();
		bss=new BillingServerSecure();
	}
	
	/**
	 * executes {@link Steps} with a wron number of arguments, so {@link WrongNumberOfArgumentsException} is thrown.
	 * @throws WrongNumberOfArgumentsException
	 */
	@Test(expected=WrongNumberOfArgumentsException.class)
	public void stepsWrongNumber() throws WrongNumberOfArgumentsException {
		cmd.setBillingServerSecure(bss);
		String[] args= {"!steps", "muh", "1"};
		cmd.execute(args);
	}
	/**
	 * executes {@link Steps} with the correct input
	 * @throws WrongNumberOfArgumentsException
	 */
	@Test
	public void stepsOk() throws WrongNumberOfArgumentsException {
		cmd.setBillingServerSecure(bss);
		String[] args= {"!steps"};	
		cmd.execute(args);
	}

}
