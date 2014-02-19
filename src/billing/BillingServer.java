package billing;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Arrays;
import java.util.Properties;
import java.util.concurrent.ConcurrentHashMap;
import management.Login;

/**
 * The billing server provides RMI methods to manage the bills of the auction
 * system. To secure the access to this administrative service, the server is
 * split up into a BillingServer RMI object (which basically just provides login
 * capability) and a BillingServerSecure which provides the actual
 * functionality.
 * 
 * @author Rudolf Krepela
 * @email rkrepela@student.tgm.ac.at
 * @version 11.02.2014
 * 
 */
public class BillingServer implements RemoteBillingServer {
	private ConcurrentHashMap<String, byte[]> user;

	public BillingServer(ConcurrentHashMap<String, byte[]> user) {
		this.user = user;
	}

	/**
	 *  
	 */
	public IRemoteBillingServerSecure login(Login login) {
		System.out.println("login");
		// TODO Login testen
		if (!Arrays.toString(user.get(login.getName())).equals(Arrays.toString(login.getPw()))){
			System.out.println("incvalid " + " " + Arrays.toString(login.getPw()) + " " + Arrays.toString(user.get(login.getName())));
			return null;// Password not correct
			
		}
			
		Properties properties = new Properties();
		// neuen stream mit der messenger.properties Datei erstellen

		try {
			BufferedInputStream stream = new BufferedInputStream(
					new FileInputStream("Server.properties"));

			properties.load(stream);
			stream.close();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		Registry registry;
		try {
			System.out.println("Registry");
			registry = LocateRegistry.getRegistry(
					properties.getProperty("rmi.registryURL"),
					Integer.parseInt(properties.getProperty("rmi.port")));
			System.out.println("Got registry");
			IRemoteBillingServerSecure bss = (IRemoteBillingServerSecure) registry
					.lookup(properties.getProperty("rmi.billingServerSecure"));
			System.out.println("Billingserver Secure");
			return bss;
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NotBoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NumberFormatException nfe) {
			System.out.println("Properties File Fehlerhaft");
		}
		//TODO Mehr ausgaben
		return null;// Fehler bei der Serverlokalisierung
	}

}