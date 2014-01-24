package model;
/**
 * MEssage which is sent when user bids
 * @author Tobias Schuschnig <tschuschnig@student.tgm.ac.at>
 *
 */
public class BidMessage implements Message{
	private String name;
	private int id;
	private double amount;
	
	public BidMessage() {
	}
	
	public BidMessage(String name,int id,double amount){
		this.name=name;
		this.id=id;
		this.amount=amount;
	}
	@Override
	public String getName() {
		return name;//war vorher null -huang
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
	 * @return the amount
	 */
	public double getAmount() {
		return amount;
	}

	/**
	 * @param amount the amount to set
	 */
	public void setAmount(double amount) {
		this.amount = amount;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}
	
}
