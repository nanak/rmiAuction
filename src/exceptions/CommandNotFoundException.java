package exceptions;
/**
 * This Exception is thrown if the given Command is a SecureCommand.
 *
 * @author Michaela Lipovits 
 * @version 20140217
 */
public class CommandNotFoundException extends Exception{
    /**
     * Constructor, which calls the superclasses constructor with 
     * the text of the exception.
     */
//    public CommandNotFoundException(){
//    	super("ERROR: This Command does not exist!");
//    }
    public CommandNotFoundException(String msg){
    	super("ERROR: This Command does not exist!\n"+msg);
    }
}