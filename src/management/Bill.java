package management;

import billing.BillingServerSecure;
import Exceptions.IllegalNumberOfArgumentsException;

public class Bill extends SecureCommand {

	private String user;
	private BillingServerSecure bss;

	@Override
	public String execute(String[] cmd) throws IllegalNumberOfArgumentsException {
		if(cmd.length!=2){
			throw new IllegalNumberOfArgumentsException();
		}
		this.user=cmd[1];
		return bss.getBill(user);
	}
	public void setBillingServerSecure(BillingServerSecure bss) {
		this.bss=bss;
	}
}
