package management;

import Exceptions.IllegalNumberOfArgumentsException;
import Exceptions.PriceStepIntervalOverlapException;
import Exceptions.WrongInputException;

public interface Command<T> {

	public abstract T execute(String[] cmd) throws WrongInputException, IllegalNumberOfArgumentsException;

}
