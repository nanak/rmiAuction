package management;

import exceptions.WrongInputException;
import exceptions.WrongNumberOfArgumentsException;
import billing.BillingServerSecure;

/**
 * Class RemoveStep, which implemets SecureCommad, removes steps.
 * @author Michaela Lipovits
 * @version 20140210
 */
public class RemoveStep extends SecureCommand<String> {

	private Double startPrice;

	private Double endPrice;
	private BillingServerSecure bss;

	@Override
	public String execute(String[] cmd) throws WrongNumberOfArgumentsException, WrongInputException {
		if(cmd.length!=3){
			throw new WrongNumberOfArgumentsException("Usage: !removeStep <startPrice> <endPrice>");
		}
		try{
			startPrice=Double.parseDouble(cmd[1]);
			endPrice=Double.parseDouble(cmd[2]);
		}
		catch(NumberFormatException e){
			throw new WrongInputException("Usage: !removeStep <startPrice> <endPrice>");
		}
		
		boolean success=bss.deletePriceStep(startPrice, endPrice);
		if(success){
			return "Price step ["+startPrice+" "+endPrice+"] successfully removed";
		}
		else{
			return "ERROR: Price step ["+startPrice+" "+endPrice+"] does not exist";
		}
	}
	public void setBillingServerSecure(BillingServerSecure bss) {
		this.bss=bss;
	}

}
