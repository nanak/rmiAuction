package management;

import Exceptions.IllegalNumberOfArgumentsException;
import Exceptions.WrongInputException;

public class RemoveStep extends SecureCommand {

	private Double startPrice;

	private Double endPrice;

	@Override
	public Object execute(String[] cmd) throws IllegalNumberOfArgumentsException, WrongInputException {
		if(cmd.length!=3){
			throw new IllegalNumberOfArgumentsException();
		}
		try{
			startPrice=Double.parseDouble(cmd[1]);
			endPrice=Double.parseDouble(cmd[2]);
		}
		catch(NumberFormatException e){
			throw new WrongInputException();
		}
		boolean success=true;
		if(success){
			return "Price step ["+startPrice+" "+endPrice+"] successfully removed";
		}
		else{
			return "ERROR: Price step ["+startPrice+" "+endPrice+"] does not exist";
		}
	}

}
