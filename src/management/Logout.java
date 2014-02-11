package management;

import Exceptions.IllegalNumberOfArgumentsException;

public class Logout extends SecureCommand {
	

	@Override
	public String execute(String[] cmd) throws IllegalNumberOfArgumentsException {
		if(cmd.length!=1){
			throw new IllegalNumberOfArgumentsException();
		}
		// TODO name add
		return "successfully logged out";
	}

}
