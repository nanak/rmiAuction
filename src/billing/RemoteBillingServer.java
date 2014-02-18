package billing;

import java.rmi.Remote;
import java.rmi.RemoteException;

import management.Login;

public interface RemoteBillingServer extends Remote{
		public IRemoteBillingServerSecure login(Login login) throws RemoteException;
}
