package ServerModel;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.Properties;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 
 * @author Nanak
 * 
 * @param <K>
 * @param <T>
 */
public class FileHandler<K, T> {

	private File file;
	private String filename;

	public FileHandler() {
		this.filename = "file.txt";
	}

	public boolean writeObject(K key, T value) {
		ObjectOutputStream out;
		try {
			file = new File(filename);
			out = new ObjectOutputStream(new FileOutputStream(file));
			out.writeObject(value);
			out.flush();
			out.close();
		} catch (FileNotFoundException e) {
			System.out.println("Error with specified file");
			e.printStackTrace();
			return false;
		} catch (IOException e) {
			System.out.println("Error with I/O processes");
			e.printStackTrace();
			return false;
		}
		return true;
	}

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
