package Exceptions;

import java.rmi.RemoteException;

/**
 * Exception is thrown when AnalyticServer tries to notify a client, but it is not
 * reachable anymore
 * 
 * @author Daniel Reichmann
 * @version 2014-02-23
 *
 */
public class ClientNotAvailableAnymoreException extends RemoteException{

	public ClientNotAvailableAnymoreException(){
		super("Client is not available via Callback anymore");
	}
}
