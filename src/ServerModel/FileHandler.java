package ServerModel;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Properties;
import java.util.concurrent.ConcurrentHashMap;

import Exceptions.CannotCastToMapException;

/**
 * This class handles the saving to and reading from file.
 * 
 * @author Nanak Tattyrek
 * 
 * @param <K>
 *            Type of the Key to use
 * @param <T>
 *            Type of the Value to use
 */
public class FileHandler<K extends Serializable, T extends Serializable> {

	private File file;
	private String filename;
	private FileInputStream fileIn;
	private FileOutputStream fileOut;

	/**
	 * constructor, sets the filename to use for the program
	 * 
	 * @param filename
	 *            the filename to use
	 * @throws IOException
	 *             if any input/output operations fail
	 */
	public FileHandler(String filename) throws IOException {
		this.filename = filename;
		file = new File(filename);
		fileOut = new FileOutputStream(file);
		fileIn = new FileInputStream(file);
	}

	/**
	 * close streams
	 * 
	 * @throws IOException
	 *             if any input/output operations fail
	 */
	public void close() throws IOException {
		fileIn.close();
		fileOut.close();
	}

	/**
	 * Reads a single Object from file
	 * 
	 * @param key
	 *            the Key to use
	 * @return the read object
	 * @throws IOException
	 *             if any input/output operations fail
	 * @throws CannotCastToMapException
	 *             if the map can't be casted to ConcurrentHashMap
	 */
	public Object readObject(K key) throws IOException,
			CannotCastToMapException {
		ConcurrentHashMap<K, T> map;
		ObjectInputStream ois = new ObjectInputStream(fileIn);
		try {
			map = (ConcurrentHashMap<K, T>) ois.readObject();
		} catch (ClassNotFoundException e) {
			throw new CannotCastToMapException();
		}
		return map.get(key);
	}

	/**
	 * Writes a single Object to file
	 * 
	 * @param key
	 *            the Key to use
	 * @param value
	 *            the Value to use
	 * @return true if successful, false if unsuccessful
	 * @throws IOException
	 *             if any input/output operations fail
	 * @throws CannotCastToMapException
	 *             if the map can't be casted to ConcurrentHashMap
	 */
	public boolean writeObject(K key, T value) throws IOException,
			CannotCastToMapException { // TODO: key, value speichern
		ConcurrentHashMap<K, T> map;
		FileInputStream fileIn = new FileInputStream(file);
		ObjectInputStream ois = new ObjectInputStream(fileIn);

		try {
			map = (ConcurrentHashMap<K, T>) ois.readObject();
		} catch (ClassNotFoundException e) {
			return false;
		}
		map.put(key, value);
		writeMap(map);
		return true;
	}

	/**
	 * Saves a Map to file
	 * 
	 * @param map
	 *            the map to save
	 * @return true if successful, false if unsuccessful
	 * @throws IOException
	 *             if any input/output operations fail
	 */
	public boolean writeMap(ConcurrentHashMap<K, T> map) throws IOException {
		ObjectOutputStream ostream = new ObjectOutputStream(fileOut);
		try {
			ostream.writeObject(map);
			ostream.flush();
		} catch (FileNotFoundException e) {
			System.out.println("File not found.");
			e.printStackTrace();
			return false;
		} catch (IOException e) {
			System.out.println("Error with I/O processes");
			e.printStackTrace();
			return false;
		}
		return true;
	}

	/**
	 * Reads a Map from file
	 * 
	 * @return the ConcurrentHashMap which was read from file
	 * @throws IOException
	 *             if any input/output operations fail
	 * @throws CannotCastToMapException
	 *             if the map can't be casted to ConcurrentHashMap
	 */
	public ConcurrentHashMap<K, T> readAll() throws IOException,
			CannotCastToMapException {
		ConcurrentHashMap<K, T> map;
		ObjectInputStream ois = new ObjectInputStream(fileIn);
		try {
			map = (ConcurrentHashMap<K, T>) ois.readObject();
		} catch (ClassNotFoundException e) {
			throw new CannotCastToMapException();
		}
		return map;
	}

	/**
	 * deletes a file
	 * 
	 * @return true if successful, false if unsuccessful
	 */
	public boolean deleteFile() {
		file = new File(filename);
		if (file.delete()) {
			return true;
		} else {
			return false;
		}
	}
}
