package JUnitTests;

import management.AddStep;
import management.RemoveStep;

import org.junit.Before;
import org.junit.Test;

import Exceptions.WrongInputException;
import Exceptions.WrongNumberOfArgumentsException;
import billing.BillingServerSecure;

/**
 * Tests Class {@link RemoveStep}
 * @author Michaela Lipovits
 * @version 2014
 */
public class RemoveStepTest {
	private RemoveStep cmd;
	private BillingServerSecure bss;
	/**
	 * instantiantes {@link RemoveStep}
	 */
	@Before
	public void setUp() {
		cmd =new RemoveStep();
		bss=new BillingServerSecure();
		
	}
	
	/**
	 * executes {@link RemoveStep} with one argument being a sting instead of in, so {@link WrongInputException} is thrown.
	 * @throws WrongInputException
	 * @throws WrongNumberOfArgumentsException
	 */
	@Test(expected=WrongInputException.class)
	public void removeStepWrongInput() throws WrongInputException, WrongNumberOfArgumentsException {
		cmd.setBillingServerSecure(bss);
		String[] args= {"!removeStep", "muh", "1"};
		cmd.execute(args);
	}
	/**
	 * executes {@link RemoveStep} with too many Arguments, so {@link WrongNumberOfArgumentsException} is thrown.
	 * @throws WrongInputException
	 * @throws WrongNumberOfArgumentsException
	 */
	@Test(expected=WrongNumberOfArgumentsException.class)
	public void removeStepWrongNumber() throws WrongInputException, WrongNumberOfArgumentsException {
		cmd.setBillingServerSecure(bss);
		String[] args= {"!removeStep", "1", "1", "2"};
		cmd.execute(args);
	}
	/**
	 * Executes {@link RemoveStep} with correct input, but step does not exist
	 * @throws WrongInputException
	 * @throws WrongNumberOfArgumentsException
	 */
	@Test
	public void removeStepNonExistant() throws WrongInputException, WrongNumberOfArgumentsException {
		cmd.setBillingServerSecure(bss);
		String[] args= {"!removeStep", "1", "10"};
		cmd.execute(args);
	}
	/**
	 * Removes a step which is created first
	 * @throws WrongInputException
	 * @throws WrongNumberOfArgumentsException
	 */
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
