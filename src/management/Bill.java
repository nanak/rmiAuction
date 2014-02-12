package management;

import Exceptions.IllegalNumberOfArgumentsException;

public class Bill extends SecureCommand {

	private String user;

	@Override
	public String execute(String[] cmd) throws IllegalNumberOfArgumentsException {
		if(cmd.length!=2){
			throw new IllegalNumberOfArgumentsException();
		}
		this.user=cmd[1];
		return "Command: "+cmd[0]+" "+cmd[1];
	}

}
