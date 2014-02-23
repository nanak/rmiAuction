package ServerModel;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.AbstractMap;
import java.util.concurrent.ConcurrentHashMap;

import Exceptions.CannotCastToMapException;

/**
 * This class handles the saving to and reading from file.
 * 
 * @author Nanak Tattyrek, Rudolf Krepela
 * @version 23.02.2014
 * @email ntattyrek@student.tgm.ac.at, rkrepela@student.tgm.ac.at
 * 
 * @param <K>
 *            Type of the Key to use
 * @param <T>
 *            Type of the Value to use
 */
public class FileHandler<K extends Serializable, T extends Serializable> {

	private File file;
	/**
	 * constructor, sets the filename to use for the program
	 * 
	 * @param filename
	 *            the filename to use
	 * @throws IOException
	 *             if any input/output operations fail
	 */
	public FileHandler(String filename) throws IOException {
		file = new File(filename);
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
	 *             if the map can't be casted to AbstractMap
	 */
	public Object readObject(K key) throws IOException,
			CannotCastToMapException {
		if (file.exists() && file.length() != 0) {
			AbstractMap<K, T> map;
			ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file));
			try {
				map = (AbstractMap<K, T>) ois.readObject();
				ois.close();
			} catch (ClassNotFoundException e) {
				ois.close();
				throw new CannotCastToMapException();
			}
			return map.get(key);
		} else {
			file.createNewFile();
			return null;
		}
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
	 *             if the map can't be casted to AbstractMap
	 */
	public boolean writeObject(K key, T value) throws IOException,
			CannotCastToMapException {
		AbstractMap<K, T> map;
		if (!file.exists())
			file.createNewFile();
		ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file));
		try {
			map = (AbstractMap<K, T>) ois.readObject();
		} catch (ClassNotFoundException e) {
			ois.close();
			return false;
		}
		map.put(key, value);
		writeMap(map);
		ois.close();
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
	public boolean writeMap(AbstractMap<K, T> map) throws IOException {
		if (!file.exists())
			file.createNewFile();
		ObjectOutputStream ostream = new ObjectOutputStream(new FileOutputStream(file));
		ostream.writeObject(map);
		ostream.flush();
		ostream.close();
		return true;
	}

	/**
	 * Reads a Map from file
	 * 
	 * @return the AbstractMap which was read from file
	 * @throws IOException
	 *             if any input/output operations fail
	 * @throws CannotCastToMapException
	 *             if the map can't be casted to AbstractMap
	 */
	public AbstractMap<K, T> readAll() throws IOException,
			CannotCastToMapException {
		AbstractMap<K, T> map;
		if (file.exists() && file.length() != 0) {
			ObjectInputStream ois = new ObjectInputStream( new FileInputStream(file));
			try {
				map = (AbstractMap<K, T>) ois.readObject();
				ois.close();
				return map;
			} catch (ClassNotFoundException e) {
				ois.close();
				throw new CannotCastToMapException();
			}
		} else {
			file.createNewFile();
			return new ConcurrentHashMap<K,T>();
		}

	}

	 /**
	 * deletes a file
	 *
	 * @return true if successful, false if unsuccessful
	 */
	 public boolean deleteFile() {
		 if(file.exists())return file.delete();
		 return false;
	 }
}
