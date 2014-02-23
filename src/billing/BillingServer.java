package billing;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.math.BigInteger;
import java.rmi.NoSuchObjectException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Arrays;
import java.util.Properties;
import java.util.concurrent.ConcurrentHashMap;

import management.Login;
import rmi.InitRMI;

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
	private InitRMI ir; //For export and unexporting objects
	private BillingServer bs;
	private RemoteBillingServerSecure bss;

	public BillingServer(ConcurrentHashMap<String, byte[]> user) {
		this.user = user;
	}

	/**
	 *  
	 */
	public IRemoteBillingServerSecure login(Login login) {
		System.out.println(login.getName());
//		for (Iterator iterator = user.keySet().iterator(); iterator.hasNext();) {
//			String type = (String) iterator.next();
//			System.out.println(type);
//		}
		// TODO Login testen
		if (!Arrays.toString(user.get(login.getName())).equals(Arrays.toString(String.format("%040x", new BigInteger(1,login.getPw())).getBytes()))){
			System.out.println("invalid login atempt" + " " + Arrays.toString(user.get(login.getName())) + " " +Arrays.toString(String.format("%040x", new BigInteger(1,login.getPw())).getBytes()));
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
//			System.out.println("Registry");
			registry = LocateRegistry.getRegistry(
					properties.getProperty("rmi.registryURL"),
					Integer.parseInt(properties.getProperty("rmi.port")));
//			System.out.println("Got registry");
			IRemoteBillingServerSecure bss = (IRemoteBillingServerSecure) registry
					.lookup(properties.getProperty("rmi.billingServerSecure"));
//			System.out.println("Billingserver Secure");
			return bss;
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NotBoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NumberFormatException nfe) {
			System.out.println("server.properties File Fehlerhaft");
		}
		//TODO Mehr ausgaben
		return null;// Fehler bei der Serverlokalisierung
	}
	
	/**
	 * Initialisiert den RMI-stub fuer den Billingserver
	 */
	 public boolean initRmi(BillingServer bs, RemoteBillingServerSecure bss){
		 try {
//			 System.out.println("init");
			 this.bs = bs;
			 this.bss = bss;
			 File f = new File("Server.properties");
				if(!f.exists()){
						System.out.println("Properties File doesn't exist. Server shutting down.");
						return false;
				}
					
			 Properties properties = new Properties();
			// neuen stream mit der messenger.properties Datei erstellen
			BufferedInputStream stream = new BufferedInputStream(new FileInputStream(f));
				//TODO catch file not found exception
			properties.load(stream);
		
			stream.close();
//			System.out.println("Stuck in init");
			ir = new InitRMI(properties);
			ir.init();
			ir.rebind(bs, properties.getProperty("rmi.billingServer"));
//            System.out.println("BillingServer bound");
			ir.rebind(bss, properties.getProperty("rmi.billingServerSecure"));
//            System.out.println("BillingServerSecure bound");
			 
		 }catch(Exception e){
			 //TODO Handeln
			 e.printStackTrace();
		 }
		 return true;
	 }
	 /**
	  * Shuts down all Servers and unexports them from the registry
	  */
	 public void shutdown(){
		 try {
			ir.unexport(bs);
			ir.unexport(bss);
		} catch (NoSuchObjectException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		 
	 }
	 

}