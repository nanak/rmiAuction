package connect;

import java.util.ArrayList;

import model.User;
/**
 * Represents methods a Notifer needs to notify Clients
 * 
 * @author Daniel Reichmann <dreichmann@student.tgm.ac.at>
 * @version 2014-01-07
 *
 */
public interface Notifier {
	/**
	 * Notifies Clients
	 * @param al		List with all Clients
	 * @param message	Message to Client as String
	 */
	public void notify(ArrayList<User> al, String message);
}
