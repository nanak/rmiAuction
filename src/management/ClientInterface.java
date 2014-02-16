package management;

import Event.Event;

public interface ClientInterface extends Remote {

	public abstract void notify(Event e);

}
