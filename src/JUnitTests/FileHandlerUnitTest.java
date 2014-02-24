package JUnitTests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.util.concurrent.ConcurrentHashMap;

import model.LoginMessage;
import model.Message;

import org.junit.Before;
import org.junit.Test;

import Exceptions.CannotCastToMapException;
import ServerModel.FileHandler;
import billing.Bill;

/**
 * Tests all functions from filehandler
 * 
 * @author Nanak Tattyrek, Rudolf Krepela
 * @version 23.02.2014
 * @email ntattyrek@student.tgm.ac.at, rkrepela@student.tgm.ac.at
 * 
 */
public class FileHandlerUnitTest {
	private FileHandler<Integer, Message> fh;
	private ConcurrentHashMap<Integer, Message> hm;

	/**
	 * Gets executed before every test method creates a new FileHandler and
	 * ConcurrentHashMap an filles the map
	 */
	@Before
	public void setUp() {
		try {
			fh = new FileHandler<Integer, Message>("file.txt");
			hm = new ConcurrentHashMap<Integer, Message>();
		} catch (IOException e) {
		}
		hm.put(1, new LoginMessage("login1", "adresse", 10, 20));
		hm.put(2, new LoginMessage("login2", "dresse", 10, 20));
	}

	/**
	 * Tests if the readAll() method works as expected
	 */
	@Test
	public void testReadAll() {
		try {
			ConcurrentHashMap<Integer, Message> test = (ConcurrentHashMap<Integer, Message>) fh
					.readAll();
			assertEquals(test.get(1).getName(), "login1");
			assertEquals(test.get(2).getName(), "login2");
			assertNotNull(test);
		} catch (IOException e) {
		} catch (CannotCastToMapException e) {
		}
	}

	/**
	 * Tests if the readObject() and writeObject() methods work as expected
	 */
	@Test
	public void testWriteReadObject() {
		try {
			assertTrue(fh.writeObject(3, new LoginMessage("login3", "adresse",
					10, 20)));
			assertEquals(((Message) (fh.readObject(3))).getName(), "login3");
			assertEquals(((Message) (fh.readObject(1))).getName(), "login1");
		} catch (IOException e) {
		} catch (CannotCastToMapException e) {
		}
	}

	/**
	 * Tests if the writeMap() method works as expected
	 */
	@Test
	public void testWriteMap() {
		try {
			assertTrue(fh.writeMap(hm));
		} catch (IOException e) {
		}
	}

	/**
	 * Tests if a single object can be read out of a whole map that was saved
	 * before
	 */
	@Test
	public void writeReadBillConcurrentHashMap() {
		try {
			FileHandler<String, Bill> fh = new FileHandler<String, Bill>(
					"billtest.txt");
			Bill bill = new Bill("user", 1, 10.4, 1.2, 2);
			// writeObject fails
			// assertTrue(fh.writeObject("user", bill));
			ConcurrentHashMap<String, Bill> map = new ConcurrentHashMap<String, Bill>();
			map.put("user", bill);
			assertTrue(fh.writeMap(map));
			assertEquals(
					((Bill) (fh.readObject("user"))).toString(),
					"auction_ID	strike_price	fee_fixed	fee_variable	fee_total\n1		10,40		1,20		0,21		1,41		\n");
			assertTrue(fh.deleteFile());
		} catch (IOException | CannotCastToMapException e) {
			e.printStackTrace();
		}
	}

}