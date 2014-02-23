package billing;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
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
		ConcurrentHashMap <String, byte[]> user = loginMap();
		BillingServer bs = new BillingServer (user);
		BillingServerSecure bss = new BillingServerSecure();
		RemoteBillingServerSecure rbss = new RemoteBillingServerSecure(bss);
		bs.initRmi(bs, rbss);
//		saveUserMap(user);
		Scanner in=new Scanner(System.in);
		//Shutting down
		in.nextLine();
		System.out.println("Server ending!");		//If Enter Button pressed, Server will end
		bs.shutdown();
	}
	
	
	 /**
	  * loginMap for testingpurposes
	  * @return
	  */
	 public static ConcurrentHashMap<String,byte[]> loginTestMap(){
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
	 
	 /**
	  * loginMap from Properties File
	  * @return
	  */
	 private static ConcurrentHashMap<String,byte[]> loginMap(){
		 
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
					properties.put("auction", new String(thedigest));
					File f = new File("user.properties");
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
	 
	 private static void saveUserMap (ConcurrentHashMap<String,byte[]> user){
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
}
