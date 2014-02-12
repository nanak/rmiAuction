package billing;

import java.rmi.NotBoundException;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.concurrent.ConcurrentHashMap;

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
	private ConcurrentHashMap<String,byte[]> user;
	
	public BillingServer (ConcurrentHashMap<String,byte[]> user){
		this.user = user;
	}

	/**
	 *  
	 */
	public RemoteBillingServerSecure login(Login login) {
		
		//TODO Login testen
		if(user.get(login.getName())!= login.getPw())
			return null;//Password not correct
		
		Registry registry;
		try {
			registry = LocateRegistry.getRegistry("URL");//TODO URL
			RemoteBillingServerSecure bss = (RemoteBillingServerSecure) registry.lookup(RemoteBillingServerSecure.SERVERNAME);
			return bss;
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NotBoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return null;//Fehler bei der Serverlokalisierung
	}

}
