package billing;

import java.rmi.Remote;
import java.rmi.RemoteException;

import management.Login;

/**
 * Needs to be implemented by a BillingServer in order to allow logins.
 * @author Daniel Reichmann
 *
 */
public interface RemoteBillingServer extends Remote{
	/**
	 * Provides a login-interface in order to get the RemoteBillingServer
	 * @param login	Login-Command with username and password
	 * @return	Reference to the BillingServerSecure
	 * 
	 * @throws RemoteException	If the Server is not available anymore, this exception is thrown
	 */
		public IRemoteBillingServerSecure login(Login login) throws RemoteException;
}
