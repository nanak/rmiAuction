package billing;

import java.rmi.Remote;

import management.Login;

/**
 * The billing server provides RMI methods to manage the bills of the auction system.
 *  To secure the access to this administrative service,
 *   the server is split up into a BillingServer RMI object 
 *   (which basically just provides login capability)
 *    and a BillingServerSecure which provides the actual functionality.
 *    
 * @author Rudolf Krepela
 * @email rkrepela@student.tgm.ac.at
 * @version 11.02.2014
 *
 */
public class BillingServer  implements Remote {
	public static final String SERVERNAME = "billingServer";

	/**
	 *  
	 */
	public RemoteBillingServerSecure login(Login login) {
		return null;
	}

}
