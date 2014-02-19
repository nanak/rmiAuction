package billing;

import java.text.DecimalFormat;
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
	private LinkedList<Double> fixedPrice;
	private LinkedList<Double> variablePricePercent;
	
	public Bill(String user, long auctionID, double price,double fixedPrice, double variablePricePercent) {
		this.user=user;
		this.auctionID=new LinkedList<Long>();
		this.price= new LinkedList<Double>();
		this.fixedPrice= new LinkedList<Double>();
		this.variablePricePercent= new LinkedList<Double>();
		addBillingLine(auctionID, price, fixedPrice, variablePricePercent);
	}


	/**
	 * shows the total history and the total price
	 */
	@Override
	public String toString() {
		DecimalFormat f = new DecimalFormat("#0.00");
		String r=String.format("%s\t%s\t%s\t%s\t%s","auction_ID","strike_price","fee_fixed","fee_variable","fee_total")+"\n";
		Iterator<Long> a=auctionID.iterator();
		Iterator<Double> b=price.iterator();
		Iterator<Double> c=fixedPrice.iterator();
		Iterator<Double> d=variablePricePercent.iterator();
		Double p,var,fix;
		while(b.hasNext()){
			p=b.next();
			var=c.next();
			fix=d.next();
			r=r+String.format("%s\t\t%s\t\t%s\t\t%s\t\t%s\t\t",""+a.next() ,f.format(p),f.format(fix),f.format(var/100*p),f.format(fix+var/100*p))+"\n";
		}
		return r;
	}

	/**
	 * saves a billing line
	 * @param auctionID
	 * @param price
	 * @param step 
	 */
	public void addBillingLine(long auctionID, double price, double fixedPrice, double variablePricePercent){
		this.auctionID.add(auctionID);
		this.price.add(price);
		this.fixedPrice.add(fixedPrice);
		this.variablePricePercent.add(variablePricePercent);
	}

	public String getUser() {
		return user;
	}
	
}