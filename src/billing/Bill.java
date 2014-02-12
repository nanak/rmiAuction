package billing;

import java.util.LinkedList;

public class Bill{
	
	private String user;
	private LinkedList<Long> auctionID;
	private LinkedList<Double> price;
	
	public Bill(String user, long auctionID, double price){
		this.user=user;
		this.auctionID=new LinkedList<Long>();
		this.auctionID.add(auctionID);
		this.price= new LinkedList<Double>();
		this.price.add(price);
	}
	
	@Override
	public String toString() {
		String r="Bill for "+user+" ";
	}

	public void addBillingLine(long auctionID, double price){
		this.auctionID.add(auctionID);
		this.price.add(price);
	}

	public String getUser() {
		return user;
	}
	
}