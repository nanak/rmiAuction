package Exceptions;
/**
 * This Exception is thrown if the input consists of one or more invald signs.
 * One or more arguments can be invalid.
 *
 * @author Michaela Lipovits <mlipovits@student.tgm.ac.at>
 * @version 20140203
 */
public class WrongInutException extends Exception{
    /**
     * Constructor, which calls the superclasses constructor with 
     * the text of the exception.
     */
    public WrongInutException(){
  	super("One or more arguments are invalid!");
    }
}