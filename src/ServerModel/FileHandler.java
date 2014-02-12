package ServerModel;

import java.io.File;
import java.util.concurrent.ConcurrentHashMap;

public class FileHandler<K,T> {
 
	private File file;
	 
	public boolean writeObject(K key, T value) {
		return false;
	}
	 
	public boolean writeMap(ConcurrentHashMap<K,T> map) {
		return false;
	}
	 
	public ConcurrentHashMap<K,T> readAll() {
		return null;
	}
	 
	public boolean deleteFile() {
		return false;
	}
	 
}
 
