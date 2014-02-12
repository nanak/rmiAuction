package management;

import Exceptions.IllegalNumberOfArgumentsException;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Login implements Command<Object>{

	private String name;

	private byte[] pw;//Hashes Password

	public Login(String name, String pw) {
		this.name = name;
		
	}

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
