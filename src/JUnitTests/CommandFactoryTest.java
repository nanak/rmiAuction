package JUnitTests;

import static org.junit.Assert.assertTrue;
import management.CommandFactory;
import management.Login;

import org.junit.Before;
import org.junit.Test;

import Exceptions.CommandIsSecureException;
import Exceptions.CommandNotFoundException;

/**
 * Tests the CommandFactory Class
 * @author Michaela Lipovits
 * @version 20140220
 */
/**
 * @author Michaela Lipovits
 * @version 2014
 */
public class CommandFactoryTest {
	private CommandFactory cf;
	/**
	 * Instatiates a CommandFactory.
	 */
	@Before
	public void setUp() {
		cf =new CommandFactory();
	}
	
	/**
	 * creates the command login succesfully
	 * @throws CommandNotFoundException
	 * @throws CommandIsSecureException
	 */
	@Test
	public void createCommandLogin() throws CommandNotFoundException, CommandIsSecureException{
		String[] args={"!login","test"};
		assertTrue(cf.createCommand(args) instanceof Login);
	}
	/**
	 * Try to create a secureCommand in createCommand, so {@link CommandIsSecureException} is thrown.
	 * @throws CommandNotFoundException
	 * @throws CommandIsSecureException
	 */
	@Test(expected=CommandIsSecureException.class)
	public void createCommandSecureExcpetion() throws CommandNotFoundException, CommandIsSecureException{
		String[] args={"!steps"};
		cf.createCommand(args);
	}
	/**
	 * Try to create a nonexistant command in createCommand, so {@link CommandNotFoundException} is thrown.
	 * @throws CommandNotFoundException
	 * @throws CommandIsSecureException
	 */
	@Test(expected=CommandNotFoundException.class)
	public void createCommandNotFound() throws CommandNotFoundException, CommandIsSecureException{
		String[] args={"!bla"};
		cf.createCommand(args);
	}
	/**
	 * Try to create a nonexistant secureCommand, so {@link CommandNotFoundException} is thrown.
	 * @throws CommandNotFoundException
	 * @throws CommandIsSecureException
	 */
	@Test(expected=CommandNotFoundException.class)
	public void createSecureCommandNotFound() throws CommandNotFoundException, CommandIsSecureException{
		String[] args={"!bla"};
		cf.createSecureCommand(args);
	}
	/**
	 * creates the secureCommand addStep.
	 * @throws CommandNotFoundException
	 */
	@Test
	public void createSecureAddStep() throws CommandNotFoundException{
		String[] args={"!addStep"};
		cf.createSecureCommand(args);
	}
	/**
	 * creates the secureCommand bill
	 * @throws CommandNotFoundException
	 */
	@Test
	public void createSecureBill() throws CommandNotFoundException{
		String[] args={"!bill"};
		cf.createSecureCommand(args);
	}
	/**
	 * creates the secureCommand steps
	 * @throws CommandNotFoundException
	 */
	@Test
	public void createSecureSteps() throws CommandNotFoundException{
		String[] args={"!steps"};
		cf.createSecureCommand(args);
	}
	/**
	 * creates the secureCommand removeSteps
	 * @throws CommandNotFoundException
	 */
	@Test
	public void createSecureRemoveSteps() throws CommandNotFoundException{
		String[] args={"!removeStep"};
		cf.createSecureCommand(args);
	}
	/**
	 * creates the secureCommand logout
	 * @throws CommandNotFoundException
	 */
	@Test
	public void createSecureLogout() throws CommandNotFoundException{
		String[] args={"!logout"};
		cf.createSecureCommand(args);
	}
}
