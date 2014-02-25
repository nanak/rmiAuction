package management;

import exceptions.WrongInputException;
import exceptions.WrongNumberOfArgumentsException;
import billing.BillingServerSecure;

public abstract class SecureCommand<T> implements Command<T> {

	private Login userData;

	private BillingServerSecure bss;

	/**
	 * Sets the BillingServer secure
	 * @param bss
	 */
	public void setBillingServerSecure(BillingServerSecure bss) {
		this.bss=bss;
	}


	/**
	 * Executes a Secure command
	 * @param cmd Command from Userinput
	 * @throws WrongInputException 
	 * @throws WrongNumberOfArgumentsException 
	 * @see management.Command<T>#execute()
	 */
	public T execute(String[] cmd) throws WrongNumberOfArgumentsException, WrongInputException {
		return null;
	}

}
