package management;

import billing.BillingServerSecure;
import Exceptions.WrongNumberOfArgumentsException;
import Exceptions.PriceStepIntervalOverlapException;
import Exceptions.WrongInputException;

/**
 * This class provides a method to add steps.
 * @author Michaela Lipovits
 * @version 20140216
 */
public class AddStep extends SecureCommand<String> {

	private Double startPrice;

	private Double endPrice;

	private Double fixedPrice;

	private Double variablePricePercent;
	private BillingServerSecure bss;

	@Override
	public String execute(String[] cmd) throws WrongNumberOfArgumentsException, WrongInputException{
		if(cmd.length!=5){
			throw new WrongNumberOfArgumentsException("Usage: !addStep <startPrice> <endPrice> <fixedPrice> <variablePricePercent>");
		}
		try{
			startPrice=Double.parseDouble(cmd[1]);
			endPrice=Double.parseDouble(cmd[2]);
			fixedPrice=Double.parseDouble(cmd[3]);
			variablePricePercent=Double.parseDouble(cmd[4]);
			try {
				bss.createPriceStep(startPrice, endPrice, fixedPrice, variablePricePercent);
			} catch (PriceStepIntervalOverlapException e) {
				e.printStackTrace();
			}
			
		}
		catch(NumberFormatException e){
			throw new WrongInputException("Usage: !addStep <startPrice> <endPrice> <fixedPrice> <variablePricePercent>");
		}
		if(endPrice==0){
			return "Step ["+startPrice+" INFINITY] successfully added";
		}
		else{
			return "Step ["+startPrice+" "+endPrice+"] successfully added";
		}
		
	}
	public void setBillingServerSecure(BillingServerSecure bss) {
		this.bss=bss;
	}
	

}
