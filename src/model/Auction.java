package model;

import java.util.Date;
/**
 * Saves important data for an auction
 * @author Tobias Schuschnig <tschuschnig@student.tgm.ac.at>
 *
 */
public class Auction {
	private int id;
	private double highestBid;
	private User lastUser,owner;
	private String description;
	private Date deadline;
	private boolean finished;
	
	public Auction(User owner,String description,Long duration){
		highestBid=0.0;
		this.owner=owner;
		this.description=description;
		deadline=new Date();
		deadline.setTime(deadline.getTime()+duration*1000);
		finished = false;
	}
	
	public Auction(User owner,String description,Long duration, int id){
		highestBid=0.0;
		this.owner=owner;
		this.description=description;
		deadline=new Date();
		deadline.setTime(deadline.getTime()+duration*1000);
		this.id = id;
		finished = false;
	}
	
	public boolean isActive(){
		return false;
	}
	public boolean bid(User user,double amount){
		return false;
	}
	/**
	 * @return the id
	 */
	public int getId() {
		return id;
	}
	/**
	 * @param id the id to set
	 */
	public void setId(int id) {
		this.id = id;
	}
	/**
	 * @return the highestBid
	 */
	public double getHighestBid() {
		return highestBid;
	}
	/**
	 * @param highestBid the highestBid to set
	 */
	public void setHighestBid(double highestBid) {
		this.highestBid = highestBid;
	}
	/**
	 * @return the lastUser
	 */
	public User getLastUser() {
		return lastUser;
	}
	/**
	 * @param lastUser the lastUser to set
	 */
	public void setLastUser(User lastUser) {
		this.lastUser = lastUser;
	}
	/**
	 * @return the owner
	 */
	public User getOwner() {
		return owner;
	}
	/**
	 * @param owner the owner to set
	 */
	public void setOwner(User owner) {
		this.owner = owner;
	}
	/**
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}
	/**
	 * @param description the description to set
	 */
	public void setDescription(String description) {
		this.description = description;
	}
	/**
	 * @return the deadline
	 */
	public Date getDeadline() {
		return deadline;
	}
	/**
	 * @param deadline the deadline to set
	 */
	public void setDeadline(Date deadline) {
		this.deadline = deadline;
	}

	/**
	 * @return the finished
	 */
	public boolean isFinished() {
		return finished;
	}

	/**
	 * @param finished the finished to set
	 */
	public void setFinished(boolean finished) {
		this.finished = finished;
	}
}
