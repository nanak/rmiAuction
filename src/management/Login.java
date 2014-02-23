package management;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import Exceptions.WrongNumberOfArgumentsException;

/**
 * Class login, which sets username and hashes the password.
 * 
 * @author Michaela Lipovits
 * @version 20140216
 */
public class Login implements Command<String>{

	private String name;

	private byte[] pw;//Hashed Password

	
	/**
	 * Execute of the Loginclass, takes the userinput of the username and the password.
	 * @param cmd cmd[1] = Login cmd[2] = password
	 * @return Password is sent hashed.
	 */
	@Override
	public String execute(String[] cmd) throws WrongNumberOfArgumentsException{
		if(cmd.length!=3){
			throw new WrongNumberOfArgumentsException("Usage: !login <username> <password>");
		}
		this.name=cmd[1];
		
		//Passwordhashing
		byte[] bytesOfMessage;
		MessageDigest md;
		try {
			System.out.println(cmd[2]);
			bytesOfMessage = cmd[2].getBytes("UTF-8");
			md = MessageDigest.getInstance("MD5");
			byte[] thedigest = md.digest(bytesOfMessage);
			this.pw = thedigest;
		} catch (NoSuchAlgorithmException | UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return "Password hashed";
	}
	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @return the pwhash
	 */
	public byte[] getPw() {
		return pw;
	}
}
