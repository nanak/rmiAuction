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
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Properties;
import java.util.Scanner;
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
		ConcurrentHashMap <String, byte[]> user = loginMap();
		BillingServer bs = new BillingServer (user);
		BillingServerSecure bss = new BillingServerSecure();
		RemoteBillingServerSecure rbss = new RemoteBillingServerSecure(bss);
		if(bs.initRmi(bs, rbss)){
	//		saveUserMap(user);
			Scanner in=new Scanner(System.in);
			//Shutting down
			in.nextLine();
			System.out.println("Server ending!");
		}//If Enter Button pressed, Server will end
		saveUserMap(loginMap());
		bs.shutdown();

	}
	
	 
	 /**
	  * loginMap from Properties File
	  * @return
	  */
	 public static ConcurrentHashMap<String,byte[]> loginMap(){
		 
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
