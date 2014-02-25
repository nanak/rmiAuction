package management;

import java.io.Serializable;
import java.rmi.Remote;
import java.rmi.RemoteException;

import event.Event;

public interface ClientInterface extends Remote, Serializable {

	public void notify(Event e) throws RemoteException;

}
