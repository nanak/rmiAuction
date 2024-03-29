package management;

import exceptions.CommandIsSecureException;
import exceptions.CommandNotFoundException;

/**
 * Class CommandFactory, which creates Commands matching the Userinput.
 * 
 * @author Michaeala Lipovits
 * @version 20140211
 */
public class CommandFactory {
	private String allowed="Allowed commands:\n!login <username> <password>\n!logout\n!steps\n!addStep <startPrice> <endPrice> <fixedPrice> <variablePricePercent>\n"
			+ "!removeStep <startPrice> <endPrice>\n!bill <userName>\n!subscribe <filterRegex>\n!unsubscribe <subscriptionID>\n"
			+ "!print\n!auto\n!hide";

	/**
	 * Method createCommand, which reads the first word of the array, and returns the matching command.
	 * Throws CommandNotFoundException if the Command cannot be found and ComandIsSecureException if the given Command is secure.
	 * 
	 * @param args String[] of the user input
	 * @return Command which matches the userinput
	 * @throws CommandNotFoundException if the input doesnt match any Command
	 * @throws CommandIsSecureException If The Commenad needs special privileges
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
			throw new CommandNotFoundException(allowed);
		}
	}
	/**
	 * Method createSecureCommand, which reads the first word of the array, and returns the matching secure command.
	 * Throws CommandNotFoundException if the Command cannot be found.
	 * 
	 * @param args String[] of the user input
	 * @return Command which matches the userinput
	 * @throws CommandNotFoundException if the input doesnt match any Command
	 * @throws CommandIsSecureException If the user needs special privileges
	 */
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
			throw new CommandNotFoundException(allowed);
		}
	}
}
