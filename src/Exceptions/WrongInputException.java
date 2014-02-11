package Exceptions;
/**
 * This Exception is thrown if the input consists of one or more invald signs.
 * One or more arguments can be invalid.
 *
 * @author Michaela Lipovits
 * @version 20140203
 */
public class WrongInputException extends Exception{
    /**
     * Constructor, which calls the superclasses constructor with 
     * the text of the exception.
     */
    public WrongInputException(){
  	super("One or more arguments are invalid!");
    }
}