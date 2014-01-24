package Client;
/**
 * This implementation defines the output via standard output
 * 
 * @author Dominik Valka <dvalka@student.tgm.ac.at>
 * @version 2014-01-07
 */
public class CLI implements UI{

	@Override
	public void out(String output) {
		System.out.println(output);
	}
	@Override
	public void outln(String output){
		System.out.print(output);
	}
}
