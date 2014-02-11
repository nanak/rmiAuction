package management;

import Exceptions.IllegalNumberOfArgumentsException;

public class Logout<T> extends SecureCommand<T> {
	

	@Override
	public T execute(String cmd) {
		String[] s;
		try{
			s=cmd.split("\\s+");
			// TODO check if this isnt totally stupid
			try {
				throw new IllegalNumberOfArgumentsException();
			} catch (IllegalNumberOfArgumentsException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		catch(ArrayIndexOutOfBoundsException e){
			
		}
		return null;
	}

}
