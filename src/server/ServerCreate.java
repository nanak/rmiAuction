package server;


import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.UUID;

import event.AuctionStarted;
import model.Auction;
import model.CreateMessage;
import model.Message;
import model.User;

/**
 * This class is responsible for a functionality off the server.
 * If the client creates an auction this class is called via the RequestHandler.
 * @author Tobias Schuschnig <tschuschnig@student.tgm.ac.at>
 * @version 2014-01-05 
 */
public class ServerCreate implements ServerAction{

	/**
	 * In this Method the functionality of create auction is implemented
	 * @param message contains every parameters for the work step
	 * @param server which should be used
	 * @return result of the operation which is handed over to the client via TCP to the client.
	 */
	@Override
	public String doOperation(Message message, Server server) {
		CreateMessage create = (CreateMessage) message;
		User creater = null;
		//Get creater
		creater = server.getUser().get(create.getName());
		if (creater == null) { //if the user doesn't exists the operation is canceled with an error message
			return "This User doesn't exists please log in first!";
		}
		//adds the auction to the list
		Auction hilf = new Auction(creater, create.getDesc(),  create.getDuration() , server.getAuction().size() );
		server.getAuction().put(server.getAuction().size(), hilf);
		//Create ArrayList to notify users
		Iterator<String> it = server.getUser().keySet().iterator();
		ArrayList<User> al = new ArrayList();
		while(it.hasNext()){
			al.add(server.getUser().get(it.next()));
		}
		Date d = new Date();
		AuctionStarted as = new AuctionStarted(UUID.randomUUID().toString(), "AUCTION_STARTED", d.getTime(), (long)hilf.getId());
		server.notify(as);
		server.notify(al,"An auction '"+hilf.getDescription()+"' with the ID: "
				+hilf.getId()+" has been created and will end on "
				+hilf.getDeadline()+".");
		return "You have succesfully created a new auction!";	
	}

}
