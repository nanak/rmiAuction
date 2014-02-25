package exceptions;

/**
 * Eexception is thrown if output from a File cannot be cast to a Map<K, T>
 * @author Daniel Reichmann
 *
 */
public class CannotCastToMapException extends Exception{
	public CannotCastToMapException(){
		super("Can't cast to this type of Map");
	}
	public CannotCastToMapException(String msg){
		super("Can't cast to this type of Map " + msg);
	}
}
