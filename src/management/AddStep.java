package management;

import Exceptions.IllegalNumberOfArgumentsException;
import Exceptions.WrongInputException;

public class AddStep extends SecureCommand<String> {

	private Double startPrice;

	private Double endPrice;

	private Double fixedPrice;

	private Double variablePricePercent;

	@Override
	public String execute(String[] cmd) throws IllegalNumberOfArgumentsException, WrongInputException{
		if(cmd.length!=5){
			throw new IllegalNumberOfArgumentsException();
		}
		try{
			startPrice=Double.parseDouble(cmd[1]);
			endPrice=Double.parseDouble(cmd[2]);
			fixedPrice=Double.parseDouble(cmd[3]);
			variablePricePercent=Double.parseDouble(cmd[4]);
		}
		catch(NumberFormatException e){
			throw new WrongInputException();
		}
		if(endPrice==0){
			return "Step ["+startPrice+" INFINITY] successfully added";
		}
		else{
			return "Step ["+startPrice+" "+endPrice+"] successfully added";
		}
		
	}
	

}
