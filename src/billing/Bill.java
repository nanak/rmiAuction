package billing;

import java.util.Iterator;
import java.util.LinkedList;

/**
 * saves the bill history for one user
 * 
 * @author Rudolf Krepela
 * @email rkrepela@student.tgm.ac.at
 * @version 12.02.2014
 *
 */
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
		Iterator<Long> a=auctionID.iterator();
		Iterator<Double> b=price.iterator();
		while(b.hasNext()){
			r=r+"\n"+"ID: "+a.next().toString()+" Price: "+b.next().toString();
		}
		return r;
	}

	/**
	 * saves a billing line
	 * @param auctionID
	 * @param price
	 */
	public void addBillingLine(long auctionID, double price){
		this.auctionID.add(auctionID);
		this.price.add(price);
	}

	public String getUser() {
		return user;
	}
	
}