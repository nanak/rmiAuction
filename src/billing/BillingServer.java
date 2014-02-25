package billing;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.rmi.NoSuchObjectException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
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

	/**
	 * Initialieses the user + passwords
	 */
	public BillingServer() {
		user = loginMap();
	}

	/**
	 *  Executes a login.
	 *  Compares the given usernae + password with them from the list.
	 *  
	 *  @return	null if no matching password, Interface to BillingServerSecure if login mathces
	 */
	public IRemoteBillingServerSecure login(Login login) {

		//Tested login
		if (!Arrays.toString(user.get(login.getName())).equals(Arrays.toString(String.format("%040x", new BigInteger(1,login.getPw())).getBytes()))){
			System.out.println("invalid login atempt" + " " + Arrays.toString(user.get(login.getName())) + " " +Arrays.toString(String.format("%040x", new BigInteger(1,login.getPw())).getBytes()));
			return null;// Password not correct
			
		}
			
		Properties properties = new Properties();
		// neuen stream mit der messenger.properties Datei erstellen

		try {
			BufferedInputStream stream = new BufferedInputStream(
					new FileInputStream("registry.properties"));

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
			System.out.println("registry.properties File Fehlerhaft");
		}
		//TODO Mehr ausgaben
		return null;// Fehler bei der Serverlokalisierung
	}
	
	
	
	public boolean initRmi(BillingServer bs, RemoteBillingServerSecure bss, String billingServerName){
		
		 Properties properties = new Properties();
		 if(billingServerName == null){
			 return initRmi(bs,bss);
		 }
		 properties.put("rmi.billingServer", billingServerName);
		 File f = new File("registry.properties");
			if(!f.exists()){
					System.out.println("Properties File doesn't exist. Server shutting down.");
					return false;
			}
				
		 Properties p2 = new Properties();
		// neuen stream mit der messenger.properties Datei erstellen
		 try{
		BufferedInputStream stream = new BufferedInputStream(new FileInputStream(f));
			//TODO catch file not found exception
		p2.load(stream);
	
		stream.close();
		 properties.put("rmi.billingServerSecure", p2.getProperty("rmi.billingServerSecure"));
		 
		 
		 ir = new InitRMI(properties);
			ir.init();
			ir.rebind(bs, properties.getProperty("rmi.billingServer"));
         System.out.println("BillingServer bound");
			ir.rebind(bss, properties.getProperty("rmi.billingServerSecure"));
         System.out.println("BillingServerSecure bound");
		 
		 }catch(Exception e){
			 
		 }
		 
		return true;
	}
	
	
	/**
	 * Initialisiert den RMI-stub fuer den Billingserver
	 */
	 public boolean initRmi(BillingServer bs, RemoteBillingServerSecure bss){
		 try {
//			 System.out.println("init");
			 this.bs = bs;
			 this.bss = bss;
			 File f = new File("registry.properties");
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
            System.out.println("BillingServer bound");
			ir.rebind(bss, properties.getProperty("rmi.billingServerSecure"));
            System.out.println("BillingServerSecure bound");
			 
		 }catch(Exception e){
			 //TODO Handeln
			 e.printStackTrace();
		 }
		 return true;
	 }
	 
	 /**
	  * loginMap from Properties File
	  * @return	Map with username and hashed pw
	  */
	 public ConcurrentHashMap<String,byte[]> loginMap(){
		 
			Properties properties = new Properties();
			// neuen stream mit der messenger.properties Datei erstellen

			try {
				BufferedInputStream stream = new BufferedInputStream(
						new FileInputStream("user.properties"));

				properties.load(stream);
				stream.close();
			} catch (IOException e1) {

				System.out.println("user.properties konnte nicht geladen werden. Erzeuge neues user.properties File");
				properties = new Properties();
				
				try {
					byte[] bytesOfMessage;
					MessageDigest md;
					bytesOfMessage = "auctionpw".getBytes("UTF-8");
					md = MessageDigest.getInstance("MD5");
					byte[] thedigest = md.digest(bytesOfMessage);
					;
					properties.put("auction", String.format("%040x", new BigInteger(1, thedigest)));
					bytesOfMessage = "test".getBytes("UTF-8");
					md = MessageDigest.getInstance("MD5");
					thedigest = md.digest(bytesOfMessage);
					properties.put("test", String.format("%040x", new BigInteger(1, thedigest)));
					File f = new File("user.properties");
					if(f.exists())
						f.delete();
					f.createNewFile();
					PrintWriter pw = new PrintWriter (new FileOutputStream(f));
					properties.store(pw, null);
				} catch (NoSuchAlgorithmException e) {
					System.out.println("Should not possible to Reach");
					e.printStackTrace();
				} catch (UnsupportedEncodingException e) {
					System.out.println("Should not possible to Reach");
					e.printStackTrace();
				} catch (FileNotFoundException e) {
					System.out.println("Should not possible to Reach");
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			}
			ConcurrentHashMap<String,byte[]> ret = new ConcurrentHashMap<String,byte[]>();
			
			try {
				for (Object o : properties.keySet()){
					ret.put((String)o, ((String)properties.get(o)).getBytes());
				}
				
				return ret;
			} catch (ClassCastException e) {
				System.out.println("user.properties Fehlerhaft");
			}
			return null;
	 }
	 /**
	  * Saves a specific map to a file
	  * 
	  * @param user	userMap with name and password
	  */
	 private void saveUserMap (ConcurrentHashMap<String,byte[]> user){
		 Properties properties = new Properties();
		 for(String s : user.keySet()){
			 properties.put(s, new String(user.get(s)));
		 }
		 File f = new File("user.properties");
			try {
				if(f.exists())
					while(!f.delete());
				
					
				f.createNewFile();
				
				PrintWriter pw = new PrintWriter (new FileOutputStream(f));
				properties.store(pw, null);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	 }
	 /**
	  * Shuts down all Servers and unexports them from the registry
	  */
	 public void shutdown(){
		 try {
			saveUserMap(user);
			ir.unexport(bs);
			ir.unexport(bss);
		} catch (NoSuchObjectException|NullPointerException e) {
			// TODO Auto-generated catch block
//			e.printStackTrace();
		}
		 
	 }
	 

}