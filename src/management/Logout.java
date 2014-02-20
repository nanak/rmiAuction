package management;

import billing.BillingServerSecure;
import Exceptions.WrongNumberOfArgumentsException;

public class Logout extends SecureCommand<String> {
	
	private BillingServerSecure bss;
	@Override
	public String execute(String[] cmd) throws WrongNumberOfArgumentsException {
		if(cmd.length!=2){
			throw new WrongNumberOfArgumentsException("Usage: !logout");
		}
		return cmd[1]+" successfully logged out";
	}
	public void setBillingServerSecure(BillingServerSecure bss) {
		this.bss=bss;
	}
}
