package billing.model;

import java.io.Serializable;

/**
 * Represents a line in a bill
 * 
 * @author Rudolf Krepela
 * @email rkrepela@student.tgm.ac.at
 * @version 12.02.2014
 *
 */
public class BillingLine implements Serializable{
	
	private Long auctionID;
	private Double price,fixedPrice,variablePricePercent;
	
	/**
	 * Collects all attributes for a billing line
	 * 
	 * @param user		Name of the user the bill is written for
	 * @param auctionID	Id of the auction
	 * @param price		StrikePrice
	 * @param fixedPrice	FixedPrice the user has to pay
	 * @param variablePricePercent VariablePrice the user has to pay
	 */
	public BillingLine(long auctionID, double price,double fixedPrice, double variablePricePercent) {
		this.auctionID=auctionID;
		this.price=price;
		this.fixedPrice=fixedPrice;
		this.variablePricePercent=variablePricePercent;
	}

	/**
	 * 
	 * @return
	 */
	public Long getAuctionID() {
		return auctionID;
	}

	/**
	 * 
	 * @return
	 */
	public Double getPrice() {
		return price;
	}

	/**
	 * 
	 * @return
	 */
	public Double getFixedPrice() {
		return fixedPrice;
	}

	/**
	 * 
	 * @return
	 */
	public Double getVariablePricePercent() {
		return variablePricePercent;
	}
	
}