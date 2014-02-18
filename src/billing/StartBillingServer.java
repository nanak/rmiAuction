package billing;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.UnsupportedEncodingException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Properties;
import java.util.concurrent.ConcurrentHashMap;


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
		BillingServer bs = new BillingServer (loginTestMap());
	}
	
	/**
	 * Initialisiert den RMI-stub fuer den Billingserver
	 */
	 private static void initRmi(BillingServer bs, RemoteBillingServerSecure bss){
		 try {
			// neues Properties Objekt eerstellen
				Properties properties = new Properties();
				// neuen stream mit der messenger.properties Datei erstellen
				BufferedInputStream stream = new BufferedInputStream(new FileInputStream("Server.properties"));

				
				properties.load(stream);
			
				stream.close();
	            BillingServer stub =
	                (BillingServer) UnicastRemoteObject.exportObject(bs, 0);
	            Registry registry = null;
	            try{
		            registry = LocateRegistry.getRegistry();
	            }catch(RemoteException re){
	            	try{
	            		registry = LocateRegistry.createRegistry(Integer.parseInt(properties.getProperty("rmi.port")));
	            	}catch(Exception e){//TODO Einschraenken
	            		System.out.println("Could not bind or locate Registry, stopping Programm");
	            		System.exit(370);
	            	}
	            }
	            if (registry == null){
	            	System.out.println("Could not bind or locate Registry, stopping Programm");
            		System.exit(370);
	            }
	            registry.rebind(properties.getProperty("rmi.billingServer"), stub);
	            System.out.println("BillingServer bound");
	            

	            RemoteBillingServerSecure stubSecure =
	                (RemoteBillingServerSecure) UnicastRemoteObject.exportObject(bss, 0);
	            registry.rebind(properties.getProperty("rmi.billingServerSecure"), stub);
	            System.out.println("BillingServerSecure bound");
	        }catch (Exception e){
	        	//TODO Handling
	        }
	 }
	 
	 /**
	  * loginMap for testingpurposes
	  * @return
	  */
	 private static ConcurrentHashMap<String,byte[]> loginTestMap(){
		 byte[] bytesOfMessage;
			MessageDigest md;
			try {
				bytesOfMessage = "test".getBytes("UTF-8");
				md = MessageDigest.getInstance("MD5");
				byte[] thedigest = md.digest(bytesOfMessage);
				ConcurrentHashMap<String,byte[]> ret = new ConcurrentHashMap<String,byte[]>();
				ret.put("test", thedigest);
				return ret;
			} catch (NoSuchAlgorithmException | UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return null;
	 }
}
