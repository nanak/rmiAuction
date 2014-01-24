package server;

import model.Message;
import server.Server;;

/**
 * Interface for all server functionalities
 * @author Tobias Schuschnig <tschuschnig@student.tgm.ac.at>
 * @version 2013-01-05 
 */
public interface ServerAction {
	/**
	 * In this Method the user request will be processed
	 * @param message contains every parameters for the work step
	 * @param server which should be used
	 * @return result of the operation which is handed over to the client via TCP to the client.
	 */
	public String doOperation(Message message,Server server);
}
