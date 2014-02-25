package management;

import exceptions.WrongNumberOfArgumentsException;
import billing.BillingServerSecure;

/**
 * Class logout, which logs the user out.
 * 
 * @author Michaela Lipovits
 * @version 20140216
 */
public class Logout extends SecureCommand<String> {
	
	private BillingServerSecure bss;
	/**
	 * Executes the Logout.
	 * @param cmd cmd[1]=username
	 */
	@Override
	public String execute(String[] cmd) throws WrongNumberOfArgumentsException {
		if(cmd.length!=2){
			throw new WrongNumberOfArgumentsException("Usage: !logout");
		}
		return cmd[1]+" successfully logged out";
	}
	/*
	 * (non-Javadoc)
	 * @see management.SecureCommand#setBillingServerSecure(billing.BillingServerSecure)
	 */
	public void setBillingServerSecure(BillingServerSecure bss) {
		this.bss=bss;
	}
}
