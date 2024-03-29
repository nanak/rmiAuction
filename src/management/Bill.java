package management;

import exceptions.WrongNumberOfArgumentsException;
import billing.BillingServerSecure;

/**
 * This class provides a method to create a bill
 * 
 * @author Michaela Lipovits
 * @version 20140216
 */
public class Bill extends SecureCommand<String> {

	private String user;
	private BillingServerSecure bss;

	/*
	 * (non-Javadoc)
	 * @see management.SecureCommand#execute(java.lang.String[])
	 */
	@Override
	public String execute(String[] cmd) throws WrongNumberOfArgumentsException {
		if(cmd.length!=2){
			throw new WrongNumberOfArgumentsException("Usage: !bill <userName>");
		}
		this.user=cmd[1];
		return bss.getBill(user);
	}
	/*
	 * (non-Javadoc)
	 * @see management.SecureCommand#setBillingServerSecure(billing.BillingServerSecure)
	 */
	public void setBillingServerSecure(BillingServerSecure bss) {
		this.bss=bss;
	}
}
