package management;

import billing.BillingServerSecure;
import Exceptions.IllegalNumberOfArgumentsException;

public class Logout extends SecureCommand<String> {
	
	private BillingServerSecure bss;
	@Override
	public String execute(String[] cmd) throws IllegalNumberOfArgumentsException {
		if(cmd.length!=1){
			throw new IllegalNumberOfArgumentsException();
		}
		// TODO name add && logout someone?
		return "successfully logged out";
	}
	public void setBillingServerSecure(BillingServerSecure bss) {
		this.bss=bss;
	}
}
