package management;

import billing.BillingServerSecure;
import Exceptions.IllegalNumberOfArgumentsException;

public class Steps extends SecureCommand<String> {
	private BillingServerSecure bss;

	@Override
	public String execute(String[] cmd) throws IllegalNumberOfArgumentsException {
		if(cmd.length!=1){
			throw new IllegalNumberOfArgumentsException("Usage: !steps");
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
