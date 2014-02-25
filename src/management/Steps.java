package management;

import exceptions.WrongNumberOfArgumentsException;
import billing.BillingServerSecure;

/**
 * Class Steps, which is a SecureCommand, lists all steps.
 * @author Michaela Lipovits
 * @version 2014
 */
public class Steps extends SecureCommand<String> {
	private BillingServerSecure bss;

	/**
	 * @see management.SecureCommand#execute(java.lang.String[])
	 */
	@Override
	public String execute(String[] cmd) throws WrongNumberOfArgumentsException {
		if(cmd.length!=1){
			throw new WrongNumberOfArgumentsException("Usage: !steps");
		}
		String ps=bss.getPriceSteps();
		if(ps.equals("")){
			return "no steps avaiable";
		}
		else{
			return ps;
		}
	}
	/*
	 * (non-Javadoc)
	 * @see management.SecureCommand#setBillingServerSecure(billing.BillingServerSecure)
	 */
	public void setBillingServerSecure(BillingServerSecure bss) {
		this.bss=bss;
	}
}
