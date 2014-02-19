package billing;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.UnsupportedEncodingException;
import java.net.BindException;
import java.rmi.ConnectException;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Properties;
import java.util.Scanner;
import java.util.concurrent.ConcurrentHashMap;

import rmi.InitRMI;


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
		BillingServerSecure bss = new BillingServerSecure();
		RemoteBillingServerSecure rbss = new RemoteBillingServerSecure(bss);
		initRmi(bs, rbss);
		new Scanner(System.in).nextLine();
		//TODO initRMI
	}
	
	/**
	 * Initialisiert den RMI-stub fuer den Billingserver
	 */
	 private static void initRmi(BillingServer bs, RemoteBillingServerSecure bss){
		 try {
			 
			 Properties properties = new Properties();
			// neuen stream mit der messenger.properties Datei erstellen
			BufferedInputStream stream = new BufferedInputStream(new FileInputStream("Server.properties"));
				//TODO catch file not found exception
			properties.load(stream);
		
			stream.close();
			InitRMI ir = new InitRMI(properties);
			ir.init();
			ir.rebind(bs, properties.getProperty("rmi.billingServer"));
            System.out.println("BillingServer bound");
			ir.rebind(bss, properties.getProperty("rmi.billingServerSecure"));
            System.out.println("BillingServerSecure bound");
			 
		 }catch(Exception e){
			 //TODO Handeln
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
