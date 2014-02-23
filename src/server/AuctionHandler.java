package server;

import java.util.ArrayList;
import java.util.Date;
import java.util.UUID;

import model.User;
import Event.AuctionEnded;
import Event.BidWon;

/**
 * Checks if an Auction is over and notifies the Users about it.
 * @author Tobias Schuschnig <tschuschnig@student.tgm.ac.at>
 * @version 2013-01-05
 */
public class AuctionHandler implements Runnable {
	//private ArrayList<Auction> auction; //TODO useless because the auctions musst be on the 
	//newest version
	private Server server;

	/**
	 * Konstructor with the used server as a parameter.
	 * @param server the Server where the work is done.
	 */
	public AuctionHandler(Server server) {
		this.server = server;
	}
	
	/**
	 * Checks with the help of a thread if an auction reached its deadline.
	 */
	@Override
	public void run() {
		while(server.isActive()) {
			try {
				Thread.sleep(500);
			} catch (InterruptedException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			Date now = new Date();
			//System.out.println(now);
			try{
			for(int i=0;i< server.getAuction().size();i++) { //Goes through all auctions
				//Checks if the auction is over
				if(server.getAuction().get(i).getDeadline().compareTo(now) <= 0 && 
						server.getAuction().get(i).isFinished() == false) { 
					server.getAuction().get(i).setFinished(true);
					//Auction Ended
					Date d = new Date();
					AuctionEnded ae = new AuctionEnded(UUID.randomUUID().toString(), "AUCTION_ENDED", d.getTime(), i);
					server.notify(ae);
					//System.out.println(server.getAuction().get(i).getDescription()+ " is over.");

					//Checks if somebody bidded on this auction to notfiy the right persons
					if(server.getAuction().get(i).getLastUser() != null) {

//						//Notifys all Users except the one who has won the aution
//						for(int ii = 0; ii < server.getUser().size(); ii++) {
//							if((server.getUser().get(ii).getName().equals(server.getAuction().get(i).getLastUser().getName()))==false) {
//								al.add(server.getUser().get(ii));
//								//TODO why does the user woh bidded also gets this specific message.
//							}
//						}
//						server.notify(al,wert);
						
						ArrayList<User> al = new ArrayList();
						
						//Notifys the owner who has won the auction
						String wert = "The auction '"+server.getAuction().get(i).getDescription()+
								"' has ended. "+server.getAuction().get(i).getLastUser().getName()+" won with "+
								server.getAuction().get(i).getHighestBid()+". \n";
						al.add(server.getAuction().get(i).getOwner());
						server.notify(al,wert);
						
						//Notifys the user who has won the auction
						String wert1 = "The auction '"+server.getAuction().get(i).getDescription()+
								"' has ended. You won with "+
								server.getAuction().get(i).getHighestBid()+". \n";
						
						al = new ArrayList();
						al.add(server.getAuction().get(i).getLastUser());
						server.notify(al, wert1);
						//Auction Won
						BidWon bw = new BidWon(UUID.randomUUID().toString(), "BID_WON", d.getTime(), server.getAuction().get(i).getLastUser().getName(), i, server.getAuction().get(i).getHighestBid());
						server.notify(bw);
					}
					else {
						//The end of an auction if nobody has bidden. 
						ArrayList<User> al = new ArrayList();
						al.add(server.getAuction().get(i).getOwner());
						//Auction Ended
						
						server.notify(al,"The auction '"
								+server.getAuction().get(i).getDescription()+"' has ended. Nobody bidded.");
					}
					//Bill auction
					server.billAuction(server.getAuction().get(i));
					
				}
			}
			}catch(Exception e){
				//Error has no consequences in the programm -> no action
			}
		}
	}
}
