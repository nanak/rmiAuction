package JUnitTests;

import management.AddStep;
import management.Bill;
import management.Steps;

import org.junit.Before;
import org.junit.Test;

import Exceptions.PriceStepIntervalOverlapException;
import Exceptions.WrongInputException;
import Exceptions.WrongNumberOfArgumentsException;
import billing.BillingServerSecure;

public class StepsTest {
	private Steps cmd;
	private BillingServerSecure bss;
	@Before
	public void setUp() {
		cmd =new Steps();
		bss=new BillingServerSecure();
		
	}
	
	@Test(expected=WrongNumberOfArgumentsException.class)
	public void stepsWrongNumber() throws WrongNumberOfArgumentsException {
		cmd.setBillingServerSecure(bss);
		String[] args= {"!steps", "muh", "1"};
		cmd.execute(args);
	}
	@Test
	public void stepsOk() throws WrongNumberOfArgumentsException {
		cmd.setBillingServerSecure(bss);
		String[] args= {"!steps"};	
		cmd.execute(args);
	}

}
