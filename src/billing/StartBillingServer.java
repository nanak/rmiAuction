package billing;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;


/**
 * main class and startpoint for the billing server
 *    
 * @author Rudolf Krepela, Thomas Traxler
 * @email rkrepela@student.tgm.ac.at, ttraxler@student.tgm.ac.at
 * @version 11.02.2014
 *
 */
public class StartBillingServer {

	public static void main(String[] args) {
	}
	
	/**
	 * Initialisiert den RMI-stub fuer den Billingserver
	 */
	 private static void initRmi(BillingServer bs, RemoteBillingServerSecure bss){
		 try {
	            BillingServer stub =
	                (BillingServer) UnicastRemoteObject.exportObject(bs, 0);
	            Registry registry = LocateRegistry.getRegistry();
	            registry.rebind(BillingServer.SERVERNAME, stub);
	            System.out.println("BillingServer bound");
	            

	            RemoteBillingServerSecure stubSecure =
	                (RemoteBillingServerSecure) UnicastRemoteObject.exportObject(bss, 0);
	            registry.rebind(RemoteBillingServerSecure.SERVERNAME, stub);
	            System.out.println("BillingServerSecure bound");
	        }catch (Exception e){
	        	//TODO Handling
	        }
	 }
}
