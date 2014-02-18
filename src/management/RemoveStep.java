package management;

import billing.BillingServerSecure;
import Exceptions.IllegalNumberOfArgumentsException;
import Exceptions.WrongInputException;

public class RemoveStep extends SecureCommand<String> {

	private Double startPrice;

	private Double endPrice;
	private BillingServerSecure bss;

	@Override
	public String execute(String[] cmd) throws IllegalNumberOfArgumentsException, WrongInputException {
		if(cmd.length!=3){
			throw new IllegalNumberOfArgumentsException("Usage: !removeStep <startPrice> <endPrice>");
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
