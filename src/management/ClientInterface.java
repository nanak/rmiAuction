package management;

import java.rmi.Remote;

import Event.Event;

public interface ClientInterface extends Remote {

	public abstract void notify(Event e);

}
