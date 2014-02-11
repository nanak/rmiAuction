package Exceptions;

import java.rmi.RemoteException;

/**
 * This Exception is thrown if the input consists of one or more negative values.
 * One or more arguments can be invalid.
 *
 * @author Rudolf Krepela
 * @email rkrepela@student.tgm.ac.at
 * @version 11.02.2014
 *
 */
public class IllegalValueException extends RemoteException{
    /**
     * Constructor, which calls the superclasses constructor with 
     * the text of the exception.
     */
    public IllegalValueException(){
  	super("One or more arguments are invalid(values below zero)!");
    }
}