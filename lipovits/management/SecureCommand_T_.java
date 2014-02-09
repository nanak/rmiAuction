package management;

//import billing.BillingServerSecure;

public abstract class SecureCommand<T> implements Command<T> {

	private Login userData;

//	private BillingServerSecure bss;

//	public void setBillingServerSecure(BillingServerSecure bss) {

//	}


	/**
	 * @see management.Command<T>#execute()
	 */
	public T execute() {
		return null;
	}

}
