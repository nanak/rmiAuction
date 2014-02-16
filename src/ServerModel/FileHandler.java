package ServerModel;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
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
		} catch (FileNotFoundException ex) {
			System.out.println("Error with specified file");
			ex.printStackTrace();
			return false;
		} catch (IOException ex) {
			System.out.println("Error with I/O processes");
			ex.printStackTrace();
			return false;
		}
		return true;
	}

	public boolean writeMap(ConcurrentHashMap<K, T> map) {
		return false;
	}

	public ConcurrentHashMap<K, T> readAll() {
		return null;
	}

	public boolean deleteFile() {
		try{
		file = new File(filename);
		if(file.delete()){
			return true;
		}
		else{
			return false;
		}
		}
		catch(Exception e){
			return false;
		}
	}
}
