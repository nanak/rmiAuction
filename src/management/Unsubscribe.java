package management;

import Exceptions.IllegalNumberOfArgumentsException;
import Exceptions.WrongInputException;

public class Unsubscribe extends SecureCommand<String> {

	@Override
	public String execute(String[] cmd) throws IllegalNumberOfArgumentsException, WrongInputException {
		int id;
		if(cmd.length!=2){
			throw new IllegalNumberOfArgumentsException();
		}
		try{
			id=Integer.parseInt(cmd[1]);
		}catch(NumberFormatException e){
			throw new WrongInputException();
		}
		return "subscription "+id+" terminated";
	}
	

}
