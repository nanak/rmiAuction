package billing.model;

import java.io.Serializable;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;

import exceptions.IllegalValueException;


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
public class PriceStep implements Serializable{
	private double startPrice, endPrice, fixedPrice, variablePricePercent;
	private String[] desc= {"Min_Price", "Max_Price", "Max_Price", "Fee_Variable"};
	private DecimalFormat f;

	/**
	 * Constructor
	 * @param startPrice
	 * @param endPrice
	 * @param fixedPrice
	 * @param variablePricePercent
	 * @throws IllegalValueException
	 */
	public PriceStep(double startPrice,double endPrice,double fixedPrice,double variablePricePercent) throws IllegalValueException{
		if(startPrice<0||endPrice<0||fixedPrice<0||variablePricePercent<0)throw new IllegalValueException("values below zero");
		if (startPrice >= endPrice && endPrice != 0)throw new IllegalValueException("endprice must be bigger than startprice");
		this.endPrice=endPrice;
		this.startPrice=startPrice;
		this.fixedPrice=fixedPrice;
		this.variablePricePercent=variablePricePercent;
		DecimalFormatSymbols otherSymbols = new DecimalFormatSymbols();
		otherSymbols.setDecimalSeparator('.');
		otherSymbols.setGroupingSeparator('.');
		f = new DecimalFormat("#0.00", otherSymbols);
	}

/**
 * creates a meta description string that contains all variable names for output
 * @return
 */
	public String getVariableNames(){
		return String.format("%s\t%s\t%s\t%s",desc[0], desc[1], desc[2],desc[3]);
	}
	
	/**
	 * writes the values in a well formated line
	 */
	@Override
	public String toString() { 
		String endp="";
		if(endPrice==0){
			endp="INFINITY";
			return String.format("%s\t\t%s\t%s\t\t%s", f.format(startPrice),endp,f.format(fixedPrice),f.format(variablePricePercent));
		}
		endp=f.format(endPrice);
		return String.format("%s\t\t%s\t\t%s\t\t%s", f.format(startPrice),endp,f.format(fixedPrice),f.format(variablePricePercent));
	}

	public double getFixedPrice() {
		return fixedPrice;
	}

	public double getVariablePricePercent() {
		return variablePricePercent;
	}

}