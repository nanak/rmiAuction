package management;

import java.io.Serializable;
import java.rmi.Remote;
import java.rmi.RemoteException;

import event.Event;
/**
 * Interface needs to be implemented by Clients in order to allow callbacks
 * @author Daniel
 *
 */
public interface ClientInterface extends Remote, Serializable {

	/**
	 * Notifies the Client
	 * @param e	NotificationEvent
	 * @throws RemoteException	Client is not available anymore
	 */
	public void notify(Event e) throws RemoteException;

}
