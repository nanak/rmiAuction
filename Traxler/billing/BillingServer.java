package billing;

import java.rmi.Remote;

import management.Login;

public class BillingServer  implements Remote {
	public static final String SERVERNAME = "billingServer";

	/**
	 *  
	 */
	public RemoteBillingServerSecure login(Login login) {
		return null;
	}

}
