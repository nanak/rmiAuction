package billing;

/**
 * collects all attributes for a billing line
 * 
 * @author Rudolf Krepela
 * @email rkrepela@student.tgm.ac.at
 * @version 12.02.2014
 *
 */
public class BillingLine{
	
	private Long auctionID;
	private Double price,fixedPrice,variablePricePercent;
	
	/**
	 * collects all attributes for a billing line
	 * 
	 * @param user
	 * @param auctionID
	 * @param price
	 * @param fixedPrice
	 * @param variablePricePercent
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