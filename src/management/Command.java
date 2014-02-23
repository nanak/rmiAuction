package management;

import java.io.Serializable;

import Exceptions.WrongNumberOfArgumentsException;
import Exceptions.PriceStepIntervalOverlapException;
import Exceptions.WrongInputException;

/**
 * Interface Command, which contains the method execute for all implementing Commands.
 * @author Michaela Lipovits
 * @version 20140210
 * @param <T>
 */
public interface Command<T> extends Serializable{

	/**
	 * Method execute, which executes all neccessary steps for the Command.
	 * 
	 * @param cmd Command as a String[]
	 * @return T 
	 * @throws WrongInputException
	 * @throws WrongNumberOfArgumentsException
	 */
	public abstract T execute(String[] cmd) throws WrongInputException, WrongNumberOfArgumentsException;

}
