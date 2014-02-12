package management;

import Exceptions.IllegalNumberOfArgumentsException;

public class Steps extends SecureCommand {

	@Override
	public String execute(String[] cmd) throws IllegalNumberOfArgumentsException {
		if(cmd.length!=1){
			throw new IllegalNumberOfArgumentsException();
		}
		return "no steps avaiable";
	}

}