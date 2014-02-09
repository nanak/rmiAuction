package loadtest;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.Scanner;

import javax.swing.JTable.PrintMode;

/**
 * Class LoadTest which starts the Loadtests.
 * 
 * @author Michaela Lipovits
 * @version 20140209
 */
public class LoadTest {

	private Properties properties;

	/**
	 * Method which reads and creates a System Descrtiption.
	 * 
	 * @return boolean Flag, if create was successful
	 */
	public boolean createSystemDescription() {
		return false;
	}

	public static void main(String[] args) {
		Properties p = new Properties();
		p.setFromFile("/home/mlipovits/GitRepos/rmiAuction/lipovits/loadtest/loadtest.properties");
//		System.out.println(p.toString());
		
		
		//CODESNIPPET FAKE CLI
		
		String text= "!login muh\n!bid miau";
  		ByteArrayInputStream in=new ByteArrayInputStream(text.getBytes());
  		printMyStuff(in);


	}
	public static void printMyStuff(ByteArrayInputStream input){
		InputStream stdin = System.in;
		try {
  			System.setIn(input);
  			Scanner scanner = new Scanner(System.in);
  			System.out.println(scanner.nextLine());
  			System.out.println(scanner.nextLine());
		} finally {
		}
	}

}
