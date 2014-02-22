package Exceptions;
/**
 * This Exception is thrown if the given Command does not exist.
 *
 * @author Michaela Lipovits 
 * @version 20140203
 */
public class CommandIsSecureException extends Exception{
    /**
     * Constructor, which calls the superclasses constructor with 
     * the text of the exception.
     */
    public CommandIsSecureException(){
    	super("ERROR: This command is secure. You have to log in first!");
    }
    public CommandIsSecureException(String msg){
    	super("ERROR: This command is secure. You have to log in first!\n"+msg);
    }
}