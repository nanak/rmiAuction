package jUnitTests;

import management.AddStep;

import org.junit.Before;
import org.junit.Test;

import exceptions.WrongInputException;
import exceptions.WrongNumberOfArgumentsException;
import billing.BillingServerSecure;

/**
 * Test the Class AddStep
 * 
 * @author Michaela Lipovits
 * @version 20140220
 */
public class AddStepTest {
	private AddStep cmd;
	private BillingServerSecure bss;
	/**
	 * Instantiates AddStep. creates a new BillingServerSecure so the Command can be tested.
	 */
	@Before
	public void setUp() {
		cmd =new AddStep();
		bss=new BillingServerSecure();
		
	}
	
	/**
	 * Adds a step with a string instead of a int, so {@link WrongInputException} is thrown.
	 * @throws WrongInputException
	 * @throws WrongNumberOfArgumentsException
	 */
	@Test(expected=WrongInputException.class)
	public void addStepWrongInput() throws WrongInputException, WrongNumberOfArgumentsException {
		cmd.setBillingServerSecure(bss);
		String[] args= {"!addStep", "muh", "1", "2", "3"};
		cmd.execute(args);
	}
	/**
	 * Adds a step with only 3 ints instead of four, so {@link WrongNumberOfArgumentsException} is thrown.
	 * 
	 * @throws WrongInputException
	 * @throws WrongNumberOfArgumentsException
	 */
	@Test(expected=WrongNumberOfArgumentsException.class)
	public void addStepWrongNumber() throws WrongInputException, WrongNumberOfArgumentsException {
		cmd.setBillingServerSecure(bss);
		String[] args= {"!addStep", "1", "1", "2"};
		cmd.execute(args);
	}

	/**
	 * Adds a step where the endprice is zero.
	 * 
	 * @throws WrongNumberOfArgumentsException
	 * @throws WrongInputException
	 */
	@Test
	public void addStepEndpriceZero() throws WrongNumberOfArgumentsException, WrongInputException {
		cmd.setBillingServerSecure(bss);
		String[] args= {"!addStep", "0", "0", "5", "10"};
		cmd.execute(args);
	}
	/**
	 * Adds a step wher the endprice is not zero.
	 * @throws WrongNumberOfArgumentsException
	 * @throws WrongInputException
	 */
	@Test
	public void addStepOk() throws WrongNumberOfArgumentsException, WrongInputException {
		cmd.setBillingServerSecure(bss);
		String[] args= {"!addStep", "0", "5", "5", "10"};
		cmd.execute(args);
	}
}
