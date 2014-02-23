package JUnitTests;

import static org.junit.Assert.*;
import management.AddStep;

import org.junit.Before;
import org.junit.Test;

import Exceptions.PriceStepIntervalOverlapException;
import Exceptions.WrongInputException;
import Exceptions.WrongNumberOfArgumentsException;
import billing.BillingServerSecure;

public class AddStepTest {
	private AddStep cmd;
	private BillingServerSecure bss;
	@Before
	public void setUp() {
		cmd =new AddStep();
		bss=new BillingServerSecure();
		
	}
	
	@Test(expected=WrongInputException.class)
	public void addStepWrongInput() throws WrongInputException, WrongNumberOfArgumentsException {
		cmd.setBillingServerSecure(bss);
		String[] args= {"!addStep", "muh", "1", "2", "3"};
		cmd.execute(args);
	}
	@Test(expected=WrongNumberOfArgumentsException.class)
	public void addStepWrongNumber() throws WrongInputException, WrongNumberOfArgumentsException {
		cmd.setBillingServerSecure(bss);
		String[] args= {"!addStep", "1", "1", "2"};
		cmd.execute(args);
	}
//	@Test(expected=PriceStepIntervalOverlapException.class)
//	public void addStepOverlap() throws WrongNumberOfArgumentsException, WrongInputException {
//		cmd.setBillingServerSecure(bss);
//		String[] args= {"!addStep", "0", "10", "5", "10"};
//		String[] args2= {"!addStep", "0", "11", "5", "6"};
//		cmd.execute(args);
//		cmd.execute(args2);
//	}
	@Test
	public void addStepEndpriceZero() throws WrongNumberOfArgumentsException, WrongInputException {
		cmd.setBillingServerSecure(bss);
		String[] args= {"!addStep", "0", "0", "5", "10"};
		cmd.execute(args);
	}
}
