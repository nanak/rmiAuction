package analytics.exceptions;
/**
 * Exception is thrown when a user logs out on the server, but he hasn't logged in in this session
 * 
 * @author Daniel Reichmann
 * @version 2014-02-24
 *
 */
public class InvalidUserLogoutException extends Exception{
	public InvalidUserLogoutException(){
		super("User which hasn't logged in logged out. -> User logged in in previous session. \nNo Statistics");
	}
}
