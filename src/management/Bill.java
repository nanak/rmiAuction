package management;

import billing.BillingServerSecure;
import Exceptions.IllegalNumberOfArgumentsException;

/**
 * This class provides a method to create a bill
 * 
 * @author Michaela Lipovits
 * @version 20140216
 */
public class Bill extends SecureCommand {

	private String user;
	private BillingServerSecure bss;

	@Override
	public String execute(String[] cmd) throws IllegalNumberOfArgumentsException {
		if(cmd.length!=2){
			throw new IllegalNumberOfArgumentsException();
		}
		this.user=cmd[1];
		//return bss.getBill(user);
		return "bill not implemented yet";
	}
	public void setBillingServerSecure(BillingServerSecure bss) {
		this.bss=bss;
	}
}
