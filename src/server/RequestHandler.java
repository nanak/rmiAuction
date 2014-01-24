package server;

import model.*;

/**
 * In this class the Request is checked an progressed to the right server where the 
 * functionality is implemented. 
 * @author Tobias Schuschnig <tschuschnig@student.tgm.ac.at>
 * @version 2013-01-05
 */
public class RequestHandler {
	private ServerAction serverAction; //TODO should this server be used or should their be an new
	//object
	
	/**
	 * Uberprueft welche Message uebergeben wurde und ruft dann den entsprechenden Server auf
	 * Verify wich Message was handed over and calls the server where the functionality is
	 * implemented.
	 * @param message contains every parameters for the work step.
	 * @param server which should be used
	 * @return result of the operation which is handed over to the client via TCP.
	 */
	public String execute(Message message,Server server) {
		String wert = "";
		//TODO are all messages correct progressed
		if(message instanceof BidMessage) { //Message = BidMessage
			ServerBid bid = new ServerBid();
			wert = bid.doOperation(message, server);
		}
		else if(message instanceof CreateMessage) {  //Message = CreateMessage
			ServerCreate create = new ServerCreate();
			wert = create.doOperation(message, server);
			//TODO the create operation has no result
		}
		else if(message instanceof ListMessage) { //Message = ListMessage
			ServerList list = new ServerList();
			wert = list.doOperation(message, server);
		}
		else if(message instanceof LoginMessage) { //Message = LoginMessage
			ServerLogin login = new ServerLogin();
			wert = login.doOperation(message, server);
		}
		else if(message instanceof LogoutMessage) { //Message = LogoutMessage
			ServerLogout logout = new ServerLogout();
			wert = logout.doOperation(message, server);
		}
		return wert;
	}
}
