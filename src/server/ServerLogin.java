package server;

import java.util.ArrayList;
import java.util.Date;
import java.util.UUID;

import Event.UserLogin;
import model.LoginMessage;
import model.Message;
import model.User;

/**
 * This class is responsible for a functionality off the server.
 * Is the login server for the users this class is called via the RequestHandler.
 * @author Tobias Schuschnig <tschuschnig@student.tgm.ac.at>
 * @version 2014-01-05
 */
public class ServerLogin implements ServerAction {
	
	/**
	 * In this Method the functionality of login is implemented
	 * @param message contains every parameters for the work step
	 * @param server which should be used
	 * @return result of the operation which is handed over to the client via TCP to the client.
	 */
	@Override
	public String doOperation(Message message, Server server) {
		Date d = new Date();
		UserLogin ul = new UserLogin(UUID.randomUUID().toString(), "USER_LOGIN", d.getTime(), message.getName());
		LoginMessage bid = (LoginMessage) message;
		String ret="";
		User loger = null;

		loger = server.getUser().get(bid.getName());
//		for(int i=0;i < server.getUser().size();i++) { //searches if the user exists
//			if(bid.getName().equals(server.getUser().get(i).getName())) {
//				loger = server.getUser().get(i);
//			}
//		}
		if(loger == null) { //if the user doesn't exists it is created
			loger = new User();
			loger.setName(bid.getName());
			loger.setAdresse(bid.getAdresse()); 
			loger.setTcpPort(bid.getTcpPort()); 
			loger.setUdpPort(bid.getUdpPort()); 
			loger.setActive(true);
			loger.setMessages(new ArrayList<String>());
			server.getUser().put(bid.getName(), loger);
			server.notify(ul);
			return "Successfully suscribed and logged in as: "+loger.getName();
		}
		else if (loger != null && loger.isActive()==false){ //if the user exists active is set true
			loger.setAdresse(bid.getAdresse()); 
			loger.setTcpPort(bid.getTcpPort()); 
			loger.setUdpPort(bid.getUdpPort()); 
			loger.setActive(true);
			if(loger.getMessages().size() != 0){
				for(String s : loger.getMessages())
					ret += s + "\n";
			}
			else
				ret ="No Messages";
			server.notify(ul);
			return "Successfully logged in as: "+loger.getName()+"\nUnread messages: "+ret;
		}
		return "This User is allready logged in please log out first!";
		
	}
}
