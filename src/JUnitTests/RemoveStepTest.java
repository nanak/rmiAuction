package JUnitTests;

import management.AddStep;
import management.RemoveStep;

import org.junit.Before;
import org.junit.Test;

import Exceptions.PriceStepIntervalOverlapException;
import Exceptions.WrongInputException;
import Exceptions.WrongNumberOfArgumentsException;
import billing.BillingServerSecure;

public class RemoveStepTest {
	private RemoveStep cmd;
	private BillingServerSecure bss;
	@Before
	public void setUp() {
		cmd =new RemoveStep();
		bss=new BillingServerSecure();
		
	}
	
	@Test(expected=WrongInputException.class)
	public void removeStepWrongInput() throws WrongInputException, WrongNumberOfArgumentsException {
		cmd.setBillingServerSecure(bss);
		String[] args= {"!removeStep", "muh", "1"};
		cmd.execute(args);
	}
	@Test(expected=WrongNumberOfArgumentsException.class)
	public void removeStepWrongNumber() throws WrongInputException, WrongNumberOfArgumentsException {
		cmd.setBillingServerSecure(bss);
		String[] args= {"!removeStep", "1", "1", "2"};
		cmd.execute(args);
	}
	@Test
	public void removeStepNonExistant() throws WrongInputException, WrongNumberOfArgumentsException {
		cmd.setBillingServerSecure(bss);
		String[] args= {"!removeStep", "1", "10"};
		cmd.execute(args);
	}
	@Test
	public void removeStepOk() throws WrongInputException, WrongNumberOfArgumentsException {
		cmd.setBillingServerSecure(bss);
		AddStep add=new AddStep();
		add.setBillingServerSecure(bss);
		String[] argsAdd= {"!addStep", "0", "10", "5", "10"};
		add.execute(argsAdd);
		String[] args= {"!removeStep", "0", "10"};
		cmd.execute(args);
	}

}
