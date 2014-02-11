package management;

import Exceptions.IllegalNumberOfArgumentsException;

public class Login implements Command<Object>{

	private String name;

	private String pw;


	@Override
	public String execute(String cmd) {
		//Split the command by space/s. Command has to consist of exactly 2 arguments otherwise it's invalid.
		String[] s=null;
		try{
			s=cmd.split("\\s+");
		}catch (ArrayIndexOutOfBoundsException e){
			// TODO check why does he want this t4ry catch around a throw exception from me??
			try {
				throw new IllegalNumberOfArgumentsException();
			} catch (IllegalNumberOfArgumentsException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
		if(s.length!=3){
			try {
				throw new IllegalNumberOfArgumentsException();
			} catch (IllegalNumberOfArgumentsException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return null;
	}

}
