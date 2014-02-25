package rmi;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.rmi.AccessException;
import java.rmi.Naming;
import java.rmi.NoSuchObjectException;
import java.rmi.NotBoundException;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.Properties;

public class InitRMI {
	
	private Properties p;
	private boolean init;
	private Registry registry;
	private Remote ro; //Remote Object, so it isn't deleted by garbage collector
	
	public static final int COULD_NOT_BIND_OR_LOCATE_REGISTRY = 370;
	public static final int REGISTRY_BOUND = 0;
	public static final int REMOTE_BOUND=0;
	
	
	public InitRMI(Properties p){
		init=false;
		this.p=p;
	}
	
//	public InitRMI() throws IOException{
//		init=false;
//		p  = new Properties();
//		BufferedInputStream stream = new BufferedInputStream(new FileInputStream("registry.properties"));	
//		p.load(stream);
//		stream.close();
//	}
	
	public int init(){
		System.out.println("Init registry");
		registry = null;
        try{
//        	System.out.println("Getting registry");
            registry = LocateRegistry.createRegistry(Integer.parseInt(p.getProperty("rmi.port")));        	
        }catch( RemoteException | NumberFormatException e){
        	System.out.println("Could not create RMI registry, getting RMI registry");
        	try{
            	registry = LocateRegistry.getRegistry(p.getProperty("rmi.registryURL"),Integer.parseInt(p.getProperty("rmi.port")));
        	}catch(Exception xe){//TODO Einschraenken
        		return COULD_NOT_BIND_OR_LOCATE_REGISTRY;
        	}
        }
        if (registry == null){
        	return COULD_NOT_BIND_OR_LOCATE_REGISTRY;
        }
		init=true;
		return REGISTRY_BOUND;
	}
	
	
	public int rebind(Remote r, String rmiIdentifier) throws RemoteException{
		if(!init)
			init();
		ro= UnicastRemoteObject.exportObject(r, 0);
		registry.rebind(rmiIdentifier, ro);
		
		return REMOTE_BOUND;
	}
	
	/**
	 * Looksup a Server and gets a Remote object
	 * @param rmiIdentifier		Unique Name in registry
	 * @return	Remote Object which matches the identifier
	 * 
	 * @throws AccessException	Access to Registry not allowed
	 * @throws RemoteException	Could not get Object
	 * @throws NotBoundException	Object not in registry
	 */
	public Remote lookup(String rmiIdentifier) throws AccessException, RemoteException, NotBoundException{
		return Naming.lookup("rmi://" + p.getProperty("rmi.registryURL") +":"+p.getProperty("rmi.port")+"/"rmiIdentifier);
	}
	
	/**
	 * Unexports an Object in order for a clean shutdown
	 * @param r		Object to be exported
	 * @exception NoSuchObjectException	Object no in Registry
	 */
	public void unexport(Remote r) throws NoSuchObjectException{
		UnicastRemoteObject.unexportObject(r, true);
	}

}
