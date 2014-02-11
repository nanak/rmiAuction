package billing;

import java.rmi.Remote;

import management.SecureCommand;
import ServerModel.Auction;

public class RemoteBillingServerSecure implements Remote {
	public static final String SERVERNAME = "billingServerSecure";

	private BillingServerSecure bss;

	public <T> T executeSecureCommand (SecureCommand<T> sc) {
		return null;
	}

	public void billAuction(Auction auction) {

	}

}
