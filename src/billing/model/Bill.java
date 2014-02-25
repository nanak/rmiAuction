package billing.model;

import java.io.Serializable;
import java.text.DecimalFormat;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

/**
 * saves the bill history for one user
 * 
 * @author Rudolf Krepela
 * @email rkrepela@student.tgm.ac.at
 * @version 12.02.2014
 *
 */
public class Bill implements Serializable{
	
	private List<BillingLine> billigLines;
	
	/**
	 * calls addBillingLine
	 * 
	 * @param user
	 * @param auctionID
	 * @param price
	 * @param fixedPrice
	 * @param variablePricePercent
	 */
	public Bill(String user, long auctionID, double price,double fixedPrice, double variablePricePercent) {
		this.billigLines=Collections.synchronizedList(new LinkedList<BillingLine>());
		addBillingLine(auctionID, price, fixedPrice, variablePricePercent);
	}


	/**
	 * shows the total history and the total price
	 */
	@Override
	public String toString() {
		DecimalFormat f = new DecimalFormat("#0.00");
		String r=String.format("%s\t%s\t%s\t%s\t%s","auction_ID","strike_price","fee_fixed","fee_variable","fee_total")+"\n";
		Iterator<BillingLine> bills=billigLines.iterator();
		BillingLine bill;
		while(bills.hasNext()){
			bill=bills.next();
			r=r+String.format("%s\t\t%s\t\t%s\t\t%s\t\t%s\t\t",""+bill.getAuctionID() ,
					f.format(bill.getPrice()),f.format(bill.getFixedPrice()),
					f.format(bill.getVariablePricePercent()/100*bill.getPrice()),
					f.format(bill.getFixedPrice()+bill.getVariablePricePercent()/100*bill.getPrice()))+"\n";
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
		this.billigLines.add(new BillingLine(auctionID, price, fixedPrice, variablePricePercent));
	}
	
}