package Client;
import java.io.ByteArrayInputStream;
import java.util.Random;

import Client.FakeCli;
public class TestFakeCli {
	private static final String ALPHA_NUMERIC_STRING = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
	public static String randomAlphaNumeric(int count) {
			StringBuilder builder = new StringBuilder();
			while (count-- != 0) {
				int character = (int)(Math.random()*ALPHA_NUMERIC_STRING.length());
				builder.append(ALPHA_NUMERIC_STRING.charAt(character));
			}
			return builder.toString();
	}
	public static void main(String[] args){
		String cmd=randomAlphaNumeric(7);
		FakeCli c=new FakeCli(cmd);
		System.out.println(c.readln());
		c.write(randomAlphaNumeric(7));
		System.out.println(c.readln());
	}
	public static String randomString(final int length) {
	    Random r = new Random(); // perhaps make it a class variable so you don't make a new one every time
	    StringBuilder sb = new StringBuilder();
	    for(int i = 0; i < length; i++) {
	        char c = (char)(r.nextInt((int)(Character.MAX_VALUE)));
	        sb.append(c);
	    }
	    return sb.toString();
	}
}
