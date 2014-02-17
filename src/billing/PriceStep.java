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
	private String[] desc= {"startPrice", "endPrice", "fixedPrice", "variablePricePercent"};
	
	public PriceStep(double startPrice,double endPrice,double fixedPrice,double variablePricePercent) throws IllegalValueException{
		if(startPrice<0||endPrice<0||fixedPrice<0||variablePricePercent<0)throw new IllegalValueException("values below zero");
		if (startPrice >= endPrice && endPrice != 0)throw new IllegalValueException("endprice must be bigger than startprice");
		this.endPrice=endPrice;
		this.startPrice=startPrice;
		this.fixedPrice=fixedPrice;
		this.variablePricePercent=variablePricePercent;
	}

/**
 * creates a meta description string that contains all variable names for output
 * @return
 */
	public String getVariableNames(){
		return String.format("|%s | %s | %s | %s|",desc[0], desc[1], desc[2],desc[3]);
	}
	
	/**
	 * writes a line |---| with the right number of '-' for table output
	 * @return
	 */
	public String getHeadLine(){
		String s="";
		String r="|";
		for(int i=0;i<desc.length;i++){
			 s=s+desc[i];
		}
		for(int i=0;i<s.length()+desc.length*3-3;i++){
			r=r+"-";
		}
		return r+"|";
	}
	
	/**
	 * writes the values in a well formated line
	 */
	@Override
	public String toString() {
		return String.format("|      %.4g|     %.4g|      %.4g |                %.4g|", startPrice,endPrice,fixedPrice,variablePricePercent);
	}

}