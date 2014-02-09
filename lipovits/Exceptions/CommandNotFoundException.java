package exceptions;
/**
 * This Exception is thrown if the given Command does not exist.
 *
 * @author Michaela Lipovits 
 * @version 20140203
 */
public class CommandNotFoundException extends Exception{
    /**
     * Constructor, which calls the superclasses constructor with 
     * the text of the exception.
     */
    public CommandNotFoundException(){
  	super("This Command does not exist!");
    }
}