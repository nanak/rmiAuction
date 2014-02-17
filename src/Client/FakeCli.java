package Client;

import java.io.ByteArrayInputStream;
import java.util.Scanner;

/**
 * This implementation defines the input via InputStream
 * 
 * @author Michaela Lipovits
 * @version 2014-02-16
 */
public class FakeCli implements UI{
	private Scanner in;
	private ByteArrayInputStream is;

	public FakeCli(String cmd){
		is=new ByteArrayInputStream(cmd.getBytes());
		in = new Scanner(is);
		
	}
	public void write(String cmd){
		is=new ByteArrayInputStream(cmd.getBytes());
		in = new Scanner(is);
	}
	@Override
	public void out(String output) {
		System.out.println(output);
	}
	@Override
	public void outln(String output){
		System.out.println(output);
	}
	@Override
	public String readln(){
		return in.nextLine();
	}
}
