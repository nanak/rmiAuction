package management;

import Exceptions.IllegalNumberOfArgumentsException;

public class Subscribe extends SecureCommand<String> {

	@Override
	public String execute(String[] cmd) throws IllegalNumberOfArgumentsException {
		if(cmd.length!=2){
			throw new IllegalNumberOfArgumentsException();
		}
		return "Created subscription with ID null for events using filter "+cmd[1];
	}
	

}
