package Exceptions;

import java.rmi.RemoteException;

/**
 * This Exception is thrown if
 * the provided price interval collides (overlaps) with an existing price step
 * (in this case the user would have to delete the other price step first)
 *
 * @author Rudolf Krepela
 * @email rkrepela@student.tgm.ac.at
 * @version 11.02.2014
 *
 */
public class PriceStepIntervalOverlapException extends RemoteException{
    /**
     * Constructor, which calls the superclasses constructor with 
     * the text of the exception.
     */
    public PriceStepIntervalOverlapException(){
  	super("The provided price interval overlaps with an existing price step (delete the other price step first)!");
    }
}