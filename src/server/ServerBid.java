package server;

import java.util.ArrayList;
import java.util.Date;
import java.util.UUID;

import model.Auction;
import model.BidMessage;
import model.Message;
import model.User;
import Event.BidOverbid;
import Event.BidPlaced;

/**
 * This class is responsible for the a functionality of the server.
 * If the client bids on an auction this class is called via the RequestHandler.
 * @author Tobias Schuschnig <tschuschnig@student.tgm.ac.at>
 * @version 2014-01-05
 */
public class ServerBid implements ServerAction {

	/**
	 * In this method the functionality of bid is implemented
	 * @param message contains every parameters for the work step
	 * @param server which should be used
	 * @return result of the operation which is handed over to the client via TCP to the client.
	 */
	@Override
	public String doOperation(Message message, Server server) {
		BidMessage bid = (BidMessage) message;
		User bidder = null;
		//Get Bidder
		bidder = server.getUser().get(bid.getName());
//		for(int i=0;i < server.getUser().size();i++) { //searches for the user who bidded
//			if(bid.getName().equals(server.getUser().get(i).getName())) {
//				bidder = server.getUser().get(i);
//			}
//		}
		if (bidder == null) { //If the user doesn't exists the operation is canceled
			return "This User doesn't exists!";
		}
		//get Auction on which is bid
		Auction auction = server.getAuction().get(bid.getId());
		if(auction == null)
			return "There is no Auction with this ID!"; //Errormessage if the auction doesn't exists
		Date d = new Date();
		BidPlaced bpl = new BidPlaced("", "BID_PLACED", d.getTime(), bid.getName(), bid.getId(), bid.getAmount());
		if(!auction.isFinished()){
			if(auction.getOwner().getName().equals(bid.getName())){
				return "You cannot bid on your own auction!";
			}

			
			else if(auction.getHighestBid() < bid.getAmount()){
				User lastUser;
				try{
					lastUser = server.getUser().get(auction.getLastUser().getName());
				}catch(NullPointerException e){
					lastUser=null;
				}
				auction.setHighestBid(bid.getAmount());
				auction.setLastUser(bidder);
				
				if (lastUser != null) { //If there is a previous bidder he gets notified
					ArrayList<User> al = new ArrayList();
					al.add(lastUser);
					server.notify(al,"You have been overbid on '" +
							auction.getDescription()+"' with the ID: "+
							auction.getId()+".");
				}
				server.notify(bpl);
				BidOverbid bo = new BidOverbid(UUID.randomUUID().toString(), "BID_OVERBID", d.getTime(), bid.getName(), bid.getId(), bid.getAmount());
				server.notify(bo);
				//Informs if the bid was succesfully
				//return "You successfully bid with "+bid.getAmount()+" on '"
				//	+server.getAuction().get(i).getDescription()+"'.";

				return "You successfully bid with "+auction.getHighestBid()+" on '"
				+auction.getDescription()+"'.";
			}
			else{
				server.notify(bpl);
				return "Your bid must be higher then the current bid! The current bid is: "+
						auction.getHighestBid();
			}
		}
		else
			return "The auction '" + auction.getDescription()+"' is already over you can not bid on this auction anymore!";

//		for(int i=0;i< server.getAuction().size();i++) {
//			if(bid.getId() == server.getAuction().get(i).getId()) {
//				if(server.getAuction().get(i).isFinished() == false) {
//					if(server.getAuction().get(i).getOwner().getName().equals(bid.getName())){
//						return "You cannot bid on your own auction!";
//					}
//					else if(server.getAuction().get(i).getHighestBid() < bid.getAmount() ) { //checks if the bid is higher and if the auction is over
//						User lastUser;
//						Auction hilf = server.getAuction().get(i);
//						hilf.setHighestBid(bid.getAmount());
//						lastUser = server.getAuction().get(i).getLastUser();
//						hilf.setLastUser(bidder);
//						server.getAuction().set(i,hilf);
//
//						if (lastUser != null) { //If there is a previous bidder he gets notified
//							ArrayList<User> al = new ArrayList();
//							al.add(lastUser);
//							server.notify(al,"You have been overbid on '" +
//									server.getAuction().get(i).getDescription()+"' with the ID: "+
//									server.getAuction().get(i).getId()+".");
//						}
//
//						//Informs if the bid was succesfully
//						//return "You successfully bid with "+bid.getAmount()+" on '"
//						//	+server.getAuction().get(i).getDescription()+"'.";
//
//						return "You successfully bid with "+server.getAuction().get(i).getHighestBid()+" on '"
//						+server.getAuction().get(i).getDescription()+"'.";
//					}
//					else { //If the bid his lower then the current bid than the operation is canceled and a error message is produced
//						return "Your bid must be higher then the current bid! The current bid is: "+
//						server.getAuction().get(i).getHighestBid();
//					}
//				}
//				else {
//					return "The auction '"+server.getAuction().get(i).getDescription()+"' is allready over you can not bid on this auction anymore!";
//				}
//			}
//		}
//		return "There is no Auction with this ID!"; //Errormessage if the auction doesn't exists
	}
}
