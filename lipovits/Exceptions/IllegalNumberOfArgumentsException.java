package Exceptions;
/**
 * This Exception is thrown if a command is followed by an invalid
 * number of arguments.
 *
 * @author Michaela Lipovits <mlipovits@student.tgm.ac.at>
 * @version 20140203
 */
public class IllegalNumberOfArgumentsException extends Exception{
    /**
     * Constructor, which calls the superclasses constructor with 
     * the text of the exception.
     */
    public IllegalNumberOfArgumentsException(){
  	super("Wrong number of arguments given!");
    }
}