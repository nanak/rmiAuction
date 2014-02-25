package analytics.exceptions;

/**
 * Exception is thrown when an Auction ends which hasn't started for the Analytics Server
 * @author Daniel Reichmann
 *
 */
public class AuctionEndedButNotStartedException extends Exception{
	public AuctionEndedButNotStartedException(){
		super("An Auction has ended which hasn't even started. Maybe you loaded from a File.");
	}
}
