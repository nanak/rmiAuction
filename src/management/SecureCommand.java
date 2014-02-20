package management;

import Exceptions.WrongNumberOfArgumentsException;
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
	 * @throws WrongNumberOfArgumentsException 
	 * @see management.Command<T>#execute()
	 */
	public T execute(String[] cmd) throws WrongNumberOfArgumentsException, WrongInputException {
		return null;
	}

}
