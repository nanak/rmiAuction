package billing.model;

import java.io.Serializable;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

/**
 * Saves the bill history for one user.
 * Contains several billingLines
 * 
 * @author Rudolf Krepela
 * @email rkrepela@student.tgm.ac.at
 * @version 12.02.2014
 *
 */
public class Bill implements Serializable{
	
	private List<BillingLine> billigLines;
	private DecimalFormat f; //Formats the Decimal Output
	
	/**
	 * Creates a new Bill for a user
	 * 
	 * @param user		Username
	 * @param auctionID	Auction which the user has to pay for
	 * @param price	StrikePrice of the auction
	 * @param fixedPrice	Fixed price the user has to pay
	 * @param variablePricePercent	Calculated percentage price
	 */
	public Bill(String user, long auctionID, double price,double fixedPrice, double variablePricePercent) {
		this.billigLines=Collections.synchronizedList(new LinkedList<BillingLine>());
		addBillingLine(auctionID, price, fixedPrice, variablePricePercent);
		DecimalFormatSymbols otherSymbols = new DecimalFormatSymbols();
		otherSymbols.setDecimalSeparator('.');
		otherSymbols.setGroupingSeparator('.'); 
		f = new DecimalFormat("#0.00", otherSymbols);
	}


	/**
	 * Shows the total history and the total price, well formatted
	 */
	@Override
	public String toString() {
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
	 * Saves an additional billing line(auction user has to pay for)
	 * @param auctionID	Auction the user has to pay for
	 * @param price	strikeprice of the auction
	 * @param fixedPrice	FixedPrice the user has to pay
	 * @param variablePricePercent Variable percentage which is added to the price
	 */
	public void addBillingLine(long auctionID, double price, double fixedPrice, double variablePricePercent){
		this.billigLines.add(new BillingLine(auctionID, price, fixedPrice, variablePricePercent));
	}
	
}