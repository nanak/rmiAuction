package management;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import Exceptions.IllegalNumberOfArgumentsException;

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
	 * Execute der Loginklasse, uebernimmt aus den Parametern der Eingabe Loginname und Passwort
	 * cmd[1] = Login
	 * cmd[2] = passwort
	 * Passwort wird gehasht gesendet.
	 */
	@Override
	public String execute(String[] cmd) throws IllegalNumberOfArgumentsException{
		if(cmd.length!=3){
			throw new IllegalNumberOfArgumentsException();
		}
		this.name=cmd[1];
		
		//Passwordhashing
		byte[] bytesOfMessage;
		MessageDigest md;
		try {
			bytesOfMessage = cmd[2].getBytes("UTF-8");
			md = MessageDigest.getInstance("MD5");
			byte[] thedigest = md.digest(bytesOfMessage);
			this.pw = thedigest;
		} catch (NoSuchAlgorithmException | UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return name+" successfully logged in";
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
