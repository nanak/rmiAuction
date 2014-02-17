package management;

import Exceptions.IllegalNumberOfArgumentsException;
import Exceptions.PriceStepIntervalOverlapException;
import Exceptions.WrongInputException;
import billing.BillingServerSecure;
import billing.BillingServerSecure;

public abstract class SecureCommand<T> implements Command<T> {

	private Login userData;

	private BillingServerSecure bss;

	public void setBillingServerSecure(BillingServerSecure bss) {
		this.bss=bss;
	}


	/**
	 * @throws WrongInputException 
	 * @throws IllegalNumberOfArgumentsException 
	 * @see management.Command<T>#execute()
	 */
	public T execute(String[] cmd) throws IllegalNumberOfArgumentsException, WrongInputException {
		return null;
	}

}
