package connect;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import server.Server;
import server.UserHandler;

/**
 * WWaits for incoming TCP Connections.
 * If a Connection is established, the server forwards to Socket to
 * a Dispatcher Thread (UserHandler)
 * 
 * 
 * @author Daniel Reichmann <dreichmann@student.tgm.ac.at>
 * @version 10-12-2013
 *
 */
public class ReceiveConnection implements Runnable{
	private Server server;
	private int tcpPort;
	private ExecutorService pool;
	
	/**
	 * Constructor
	 * @param tcp		TCP Port 
	 * @param serv		Server
	 */
	public ReceiveConnection(int tcp, Server serv){
		tcpPort = tcp;
		server = serv;
		pool = Executors.newCachedThreadPool();
	}
	
	/**
	 * Receives a connection an dispatches it
	 */
	@Override
	public void run(){
		ServerSocket ss = null;
		try {
			ss = new ServerSocket(tcpPort);
			ss.setSoTimeout(5000);
		} catch (IOException e) {
//			System.out.println("Could not listen on specififc port\nExit with enter.");
			server.setActive(false);
			return;
		}
//		System.out.println("Server is listening");
		while(server.isActive()){
			Socket client = null;
			try {
				client = ss.accept();
				
				pool.submit(new UserHandler(client, server));
								
			} catch (IOException e) {			}
			
		}
		shutdownAndAwaitTermination(pool);
		try {
			if(ss!=null)
				ss.close();
		} catch (IOException e) {		}
	}
	
	/**
	 * Terminates all Threads of a ThreadPool. Waits 10 Seconds before finally kill all running processes
	 * 
	 * @author Oracle http://docs.oracle.com/javase/7/docs/api/java/util/concurrent/ExecutorService.html
	 * @param pool		ExecutorService to be shut down
	 */
	private void shutdownAndAwaitTermination(ExecutorService pool) {
		   pool.shutdown(); // Disable new tasks from being submitted
		   try {
		     // Wait a while for existing tasks to terminate
		     if (!pool.awaitTermination(10, TimeUnit.SECONDS)) {
		       pool.shutdownNow(); // Cancel currently executing tasks
		       // Wait a while for tasks to respond to being cancelled
		       if (!pool.awaitTermination(10, TimeUnit.SECONDS))
		           System.err.println("Pool did not terminate");
		     }
		   } catch (InterruptedException ie) {
		     // (Re-)Cancel if current thread also interrupted
		     pool.shutdownNow();
		     // Preserve interrupt status
		     Thread.currentThread().interrupt();
		   }
		 }
}
