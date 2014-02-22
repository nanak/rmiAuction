package JUnitTests;

import management.CommandFactory;
import management.Login;
import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import Exceptions.CommandIsSecureException;
import Exceptions.CommandNotFoundException;

public class CommandFactoryTest {
	private CommandFactory cf;
	@Before
	public void setUp() {
		cf =new CommandFactory();
	}
	
	@Test
	public void createCommandLogin() throws CommandNotFoundException, CommandIsSecureException{
		String[] args={"!login","muh"};
		assertEquals(new Login(), cf.createCommand(args));
	}
	@Test(expected=CommandIsSecureException.class)
	public void createCommandSecureExcpetion() throws CommandNotFoundException, CommandIsSecureException{
		String[] args={"!steps"};
		cf.createCommand(args);
	}
	@Test(expected=CommandNotFoundException.class)
	public void createCommandNotFound() throws CommandNotFoundException, CommandIsSecureException{
		String[] args={"!bla"};
		cf.createCommand(args);
	}
	@Test(expected=CommandNotFoundException.class)
	public void createSecureCommandNotFound() throws CommandNotFoundException, CommandIsSecureException{
		String[] args={"!bla"};
		cf.createSecureCommand(args);
	}
	@Test
	public void createSecureAddStep() throws CommandNotFoundException{
		String[] args={"!addStep"};
		cf.createSecureCommand(args);
	}
	@Test
	public void createSecureBill() throws CommandNotFoundException{
		String[] args={"!bill"};
		cf.createSecureCommand(args);
	}
	@Test
	public void createSecureSteps() throws CommandNotFoundException{
		String[] args={"!steps"};
		cf.createSecureCommand(args);
	}
	@Test
	public void createSecureRemoveSteps() throws CommandNotFoundException{
		String[] args={"!removeStep"};
		cf.createSecureCommand(args);
	}
	@Test
	public void createSecureLogout() throws CommandNotFoundException{
		String[] args={"!logout"};
		cf.createSecureCommand(args);
	}
}
