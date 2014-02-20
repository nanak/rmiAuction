package management;

import billing.BillingServerSecure;
import Exceptions.WrongNumberOfArgumentsException;

public class Steps extends SecureCommand<String> {
	private BillingServerSecure bss;

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
	public void setBillingServerSecure(BillingServerSecure bss) {
		this.bss=bss;
	}
}
