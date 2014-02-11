package billing;

import Exceptions.IllegalValueException;


/**
 * 
 *  Administrators can change price steps:
 *  Auction Price (start->end price),	Auction Fee (Fixed Part),	Auction Fee (Variable Part)
 *  
 * @author Rudolf Krepela
 * @email rkrepela@student.tgm.ac.at
 * @version 11.02.2014
 *
 */
public class PriceStep{
	
	private double startPrice, endPrice, fixedPrice, variablePricePercent;
	
	public PriceStep(double startPrice,double endPrice,double fixedPrice,double variablePricePercent) throws IllegalValueException{
		if(startPrice<0||endPrice<0||fixedPrice<0||variablePricePercent<0)throw new IllegalValueException("values below zero");
		if (startPrice >= endPrice && endPrice != 0)throw new IllegalValueException("endprice must be bigger than startprice");
		this.endPrice=endPrice;
		this.startPrice=startPrice;
		this.fixedPrice=fixedPrice;
		this.variablePricePercent=variablePricePercent;
	}

	@Override
	public String toString() {
		return "PriceStep [startPrice=" + startPrice + ", endPrice="
				+ endPrice + ", fixedPrice=" + fixedPrice
				+ ", variablePricePercent=" + variablePricePercent + "]";
	}

}