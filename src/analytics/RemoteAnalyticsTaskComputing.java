package analytics;

import java.rmi.Remote;
import java.rmi.RemoteException;

import event.Event;
import management.ClientInterface;

public interface RemoteAnalyticsTaskComputing extends Remote{

	 /**
	  * Created Subsrciption with callback
	  * @param filter
	  * @param clientId
	  * @param ci
	  * @return
	  */
	public String subscribe(String filter, ClientInterface ci) throws RemoteException;
	 /**
	  * Terminates a Subsrciption
	  * @param subscriptionId
	  * @return
	  */
	public String unsubscribe(String subscriptionId) throws RemoteException ;
	public void processEvent(Event e) throws RemoteException;
}
