package management;

import java.io.Serializable;

import Exceptions.IllegalNumberOfArgumentsException;
import Exceptions.PriceStepIntervalOverlapException;
import Exceptions.WrongInputException;

public interface Command<T> extends Serializable{

	public abstract T execute(String[] cmd) throws WrongInputException, IllegalNumberOfArgumentsException;

}
