package management;

import Exceptions.*;

/**
 * Class CommandFactory, which creates Commands matching the Userinput.
 * 
 * @author Michaeala Lipovits
 * @version 20140211
 */
public class CommandFactory {

	/**
	 * Method createCommand, which reads the first word of the array, and returns the matching command.
	 * Throws CommandNotFoundException if the Command cannot be found.
	 * 
	 * @param args String[] of the user input
	 * @return Command which matches the userinput
	 * @throws CommandNotFoundException if the input doesnt match any Command
	 * @throws CommandIsSecureException 
	 */
	
	public Login createCommand(String[] args) throws CommandNotFoundException, CommandIsSecureException{
		if(args[0].equals("!login")){
			return new Login();
		}
		else if(args[0].equals("!logout")||args[0].equals("!addStep")||args[0].equals("!steps")||args[0].equals("!removeStep")||args[0].equals("!bill")){
			throw new CommandIsSecureException();
		}
		else{
			//if command is not one of the above, an exception is thrown
			throw new CommandNotFoundException();
		}
	}
	public SecureCommand createSecureCommand(String[] args) throws CommandNotFoundException{
		if(args[0].equals("!addStep")){
			return new AddStep();
		}
		else if(args[0].equals("!logout")){
			return new Logout();
		}
		else if(args[0].equals("!steps")){
			return new Steps();
		}
		else if(args[0].equals("!removeStep")){
			return new RemoveStep();
		}
		else if(args[0].equals("!bill")){
			return new Bill();
		}
		else{
			//if command is not one of the above, an exception is thrown
			throw new CommandNotFoundException();
		}
	}
}
