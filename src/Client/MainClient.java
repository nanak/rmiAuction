package Client;
/**
 * This class starts the client
 * Ends it when enter is pressed
 * @author Dominik Valka <dvalka@student.tgm.ac.at>
 * @version 2014-01-09
 */
public class MainClient {

	public static void main(String[] args) {
		if(args.length!=3){
			System.out.println("Wrong arguments\nServer-IP TCP-Port UDP-Port");
			System.exit(0);		//If there are no 3 arguments, program exits immediately
		}
		try{
		String host=args[0];
		int tcpPort=Integer.parseInt(args[1]);
		int udpPort=Integer.parseInt(args[2]);	//Save arguments
		Client c=new Client(host,tcpPort,udpPort);
		c.run();		//Start Client
		}catch(NumberFormatException e){
			System.out.println("Port(s) is/are not numeric");
		}catch(Exception e){
			System.out.println("Can not connect to Server");
		}
	}
	
}
