package server;

import java.util.Scanner;
import connect.ReceiveConnection;
/**
 * This class starts a Server with the number of the TCP Port as argument
 * @author Dominik Valka <dvalka@student.tgm.ac.at>
 * @version 2014-01-13
 */
public class ServerStart {

	public static void main(String[] args) {
		Server s = new Server();		//Generate Server object
		int port=5000;		//Default port set
		try{
			port=Integer.parseInt(args[0]);		//If possible port will be set
		}catch(NumberFormatException e){
			System.out.println("Submitted Port is not a acceptable number!\nAutomatically choose port 5000");
		}catch(Exception e){ 
			System.out.println("No or too many arguments submitted\n 5000 is used as Port");
		}
		s.setTcpPort(port);		//Set Server Port
		ReceiveConnection r = new ReceiveConnection(port, s);		//Establish Connection
		Thread t = new Thread(r);
		t.start();		//Generate and start Thread
		Scanner in=new Scanner(System.in);
		in.nextLine();
		System.out.println("Server ends in 5sec");		//If Enter Button pressed, Server will end
		s.setActive(false);
		in.close();
	}

}
