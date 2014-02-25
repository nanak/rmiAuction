package rmi;

import java.net.MalformedURLException;
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
/**
 * Class that encapsulates some necessary RMI steps
 * @author Thomas Traxler <ttraxler@student.tgm.ac.at>
 * @version 2014-02-25
 */
public class InitRMI {
	
	private Properties p;
	private boolean init;
	private Registry registry;
	private Remote ro; //Remote Object, so it isn't deleted by garbage collector
	
	public static final int COULD_NOT_BIND_OR_LOCATE_REGISTRY = 370;
	public static final int REGISTRY_BOUND = 0;
	public static final int REMOTE_BOUND=0;
	
	/**
	 * Konstructor with a properties object
	 * @param p
	 */
	public InitRMI(Properties p){
		init=false;
		this.p=p;
	}

	/**
	 * Initialises this Object
	 * @return successstate (0 if successfull, see the static fields for other possibilities)
	 */
	public int init(){
		System.out.println("Init registry");
		registry = null;
        try{
//        	System.out.println("Getting registry");
            registry = LocateRegistry.createRegistry(Integer.parseInt(p.getProperty("rmi.port")));        	
        }catch( RemoteException | NumberFormatException e){
        	System.out.println("Could not create RMI registry, getting RMI registry");
        	try{
            	registry = LocateRegistry.getRegistry(Integer.parseInt(p.getProperty("rmi.port")));
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
	
	/**
	 * Rebinds the Remoteobject
	 * @param r Remoteobject
	 * @param rmiIdentifier 
	 * @return Successstate
	 * @throws RemoteException
	 */
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
	 * @throws MalformedURLException 
	 */
	public Remote lookup(String rmiIdentifier) throws AccessException, RemoteException, NotBoundException, MalformedURLException{
		return Naming.lookup("rmi://" + p.getProperty("rmi.registryURL") +":"+p.getProperty("rmi.port")+"/"+rmiIdentifier);
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
