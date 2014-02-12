package ServerModel;

import java.util.Date;

public class Auction {
 
	private int id;
	 
	private double highestBid;
	 
	private User lastUser;
	 
	private User owner;
	 
	private String description;
	 
	private Date deadline;
	 
	public Auction(User owner, String description, String duration) {
	 
	}
	 
	public boolean isActive() {
		return false;
	}
	 
	public boolean bid(User user, double amount) {
		return false;
	}
	 
}
 
