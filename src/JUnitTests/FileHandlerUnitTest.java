package ServerModel;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.util.concurrent.ConcurrentHashMap;

import model.LoginMessage;
import model.Message;

import org.junit.Test;

import Exceptions.CannotCastToMapException;

public class FileHandlerUnitTest {
	private FileHandler<Integer, Message> fh;
	private ConcurrentHashMap<Integer, Message> hm;

	public FileHandlerUnitTest(){
		try {
			fh = new FileHandler("file.txt");
			hm = new ConcurrentHashMap<>();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		hm.put(1, new LoginMessage());
		hm.put(2, new LoginMessage());
	}
	
	@Test
	public void testWriteMap(){
		try {
			assertTrue(fh.writeMap(hm));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Test
	public void testReadAll(){
		try {
			assertNotNull(fh.readAll());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (CannotCastToMapException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Test
	public void testWriteObject(){
		try {
			assertTrue(fh.writeObject(3, new LoginMessage()));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (CannotCastToMapException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Test
	public void testReadObject(){
		try {
			assertNotNull(fh.readObject(2));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (CannotCastToMapException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
//	@Test
//	public void testDeleteFile() {
//		assertTrue(fh.deleteFile());
//	}

}