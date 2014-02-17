package management;

import Exceptions.IllegalNumberOfArgumentsException;

public class Login implements Command<String>{

	private String name;

	private String pw;

	@Override
	public String execute(String[] cmd) throws IllegalNumberOfArgumentsException{
		if(cmd.length!=3){
			throw new IllegalNumberOfArgumentsException();
		}
		this.name=cmd[1];
		this.pw=cmd[2];
		return name+" successfully logged in";
	}

}
