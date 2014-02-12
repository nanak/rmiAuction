package billing;

import java.rmi.Remote;

import management.SecureCommand;
import ServerModel.Auction;

/**
 * BillingServer RMI object (which basically just provides login capability)
 * @author Rudolf Krepela
 * @email rkrepela@student.tgm.ac.at
 * @version 11.02.2014
 *
 */
public class RemoteBillingServerSecure implements Remote {
	public static final String SERVERNAME = "billingServerSecure";

	private BillingServerSecure bss;

	public <T> T executeSecureCommand (SecureCommand<T> sc) {
		return null;
	}

	public void billAuction(Auction auction) {

	}

}
