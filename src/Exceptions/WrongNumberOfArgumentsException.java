package Exceptions;
/**
 * This Exception is thrown if a command is followed by an invalid
 * number of arguments.
 *
 * @author Michaela Lipovits
 * @version 20140203
 */
public class WrongNumberOfArgumentsException extends Exception{
    /**
     * Constructor, which calls the superclasses constructor with 
     * the text of the exception.
     */
    public WrongNumberOfArgumentsException(){
    	super("Wrong number of arguments given!");
    }
    public WrongNumberOfArgumentsException(String msg){
    	super("Wrong number of arguments given!\n"+msg);
    }
}