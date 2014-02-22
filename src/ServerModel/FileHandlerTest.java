package ServerModel;

import java.io.IOException;
import java.util.concurrent.ConcurrentHashMap;

import Exceptions.CannotCastToMapException;

import model.LoginMessage;
import model.Message;

/**
 * simple filehandler test class
 * @author Nanak Tattyrek
 *
 */
public class FileHandlerTest {
	private static FileHandler<Integer, Message> fh;
	
	public static void main(String[] args) {
		try {
			fh = new FileHandler("file.txt");
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		ConcurrentHashMap<Integer, Message> hm = new ConcurrentHashMap<>();
		hm.put(1, new LoginMessage());
		hm.put(2, new LoginMessage());
		
		try {
			fh.writeMap(hm);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		try {
			System.out.println(fh.readAll().toString());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (CannotCastToMapException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		try {
			fh.writeObject(3, new LoginMessage());
			fh.writeObject(1, new LoginMessage());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (CannotCastToMapException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		try {
			System.out.println(fh.readAll().toString());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (CannotCastToMapException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		try {
			System.out.println(fh.readObject(2).toString());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (CannotCastToMapException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		System.out.println(fh.deleteFile());
	}
}
