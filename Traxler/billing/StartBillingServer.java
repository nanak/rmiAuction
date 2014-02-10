package billing;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;


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
