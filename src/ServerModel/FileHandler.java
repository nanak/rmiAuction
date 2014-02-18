package ServerModel;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Properties;
import java.util.concurrent.ConcurrentHashMap;

/**
 * This class handles the saving to and reading from file.
 * @author Nanak Tattyrek
 * 
 * @param <K> Type of the Key to use
 * @param <T> Type of the Value to use
 */
public class FileHandler<K, T> {

	private File file;
	private String filename;
	private ObjectInputStream in;
	private FileInputStream fileIn;
	private FileOutputStream fileOut;

	/**
	 * default constructor
	 */
	public FileHandler() {
		this.filename = "file.txt";
		file = new File(filename);
	}
	
	/**
	 * constructor, sets the filename to use for the program
	 * @param filename the filename to use
	 */
	public FileHandler(String filename){
		this.filename = filename;
		file = new File(filename);
		if(file.exists()){
			try {
				this.in=new ObjectInputStream(new FileInputStream(file));
				fileIn = new FileInputStream(file);
				fileOut = new FileOutputStream(file);
			} catch (FileNotFoundException e) {
				System.out.println("Error with specified file");
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * close ObjectInputStream
	 */
	public void close(){
		try {
			in.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Reads a single Object from file
	 * @param key the Key to use
	 * @param value the Value to use
	 * @return the read object
	 */
	public Object readObject(K key, T value) { // TODO: Key, value lesen
		ConcurrentHashMap<K, T> map = new ConcurrentHashMap<K, T>();
		Properties prop = new Properties();
		try {
			prop.load(new FileInputStream(filename));
		} catch (FileNotFoundException e) {
			System.out.println("File not found.");
			e.printStackTrace();
		} catch (IOException e) {
			System.out.println("Error with I/O processes");
			e.printStackTrace();
		}
		prop.putAll(map);
		return map.get(key);
	}

	/**
	 * Writes a single Object to file
	 * @param key the Key to use
	 * @param value the Value to use
	 * @return true if successful, false if unsuccessful
	 */
	public boolean writeObject(K key, T value) { // TODO: key, value speichern
		ConcurrentHashMap<K, T> map = new ConcurrentHashMap<K, T>();
		Properties prop1 = new Properties();
		Properties prop2 = new Properties();
		try {
			prop1.load(new FileInputStream(filename));
			prop1.putAll(map);
			map.put(key, value);
			
			for (ConcurrentHashMap.Entry<K, T> entry : map.entrySet()) {
				prop2.put(entry.getKey(), entry.getValue());
			}
			prop2.store(new FileOutputStream(filename), null);
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
	 * Saves a Map to file
	 * @param map the map to save
	 * @return true if successful, false if unsuccessful
	 */
	public boolean writeMap(ConcurrentHashMap<K, T> map) {
		Properties prop = new Properties();
		for (ConcurrentHashMap.Entry<K, T> entry : map.entrySet()) {
			prop.put(entry.getKey(), entry.getValue());
		}
		try {
			prop.store(new FileOutputStream(filename), null);
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
	 * @return the ConcurrentHashMap which was read from file
	 */
	public ConcurrentHashMap<K, T> readAll() {
		ConcurrentHashMap<K, T> map = new ConcurrentHashMap<K, T>();
		Properties prop = new Properties();
		try {
			prop.load(new FileInputStream(filename));
		} catch (FileNotFoundException e) {
			System.out.println("File not found.");
			e.printStackTrace();
		} catch (IOException e) {
			System.out.println("Error with I/O processes");
			e.printStackTrace();
		}
		prop.putAll(map);
		return map;
	}

	/**
	 * deletes a file
	 * @return true if successful, false if unsuccessful
	 */
	public boolean deleteFile() {
		try {
			file = new File(filename);
			if (file.delete()) {
				return true;
			} else {
				return false;
			}
		} catch (Exception e) {
			return false;
		}
	}
}
