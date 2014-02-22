package Exceptions;

public class CannotCastToMapException extends Exception{
	public CannotCastToMapException(){
		super("Can't cast to this type of Map");
	}
	public CannotCastToMapException(String msg){
		super("Can't cast to this type of Map " + msg);
	}
}
