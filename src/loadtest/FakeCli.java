package loadtest;

import java.io.ByteArrayInputStream;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

import Client.UI;

/**
 * This implementation defines the input via InputStream.
 * The Outputs are saved in an ArrayList for later access.
 * 
 * @author Michaela Lipovits
 * @version 2014-02-16
 */
public class FakeCli implements UI{
	private Scanner in;
	private ByteArrayInputStream is;
	private ArrayList<Integer> a;
	private Random r = new Random();
	private String[] saveLines;
	private String[] saveIDs;
	private boolean clientsAlive;
	private String lastOutput;
	private ArrayList<String> outputs;
	private ArrayList<String> outputsM;

	/**
	 * Constructor, which sets the input to a ByteArrayInputStream wich saves the given String.
	 * 
	 * @param cmd String which shall be the input
	 */
	public FakeCli(String cmd) {
		outputs=new ArrayList<String>();
		outputsM=new ArrayList<String>();
		is=new ByteArrayInputStream(cmd.getBytes());
		in = new Scanner(is);
	}
	/**
	 * This method provides the capability of writing a mew String to the InputStream.
	 * 
	 * @param cmd String which shall be the input
	 */
	public void write(String cmd){
		is=new ByteArrayInputStream(cmd.getBytes());
		in = new Scanner(is);
	}
	/**
	 * This method saves the data to an arraylist, and saves the ID's 
	 * to another ArrayList, if the output start with "ID:" (if !list is called by a client)
	 */
	@Override
	public void out(String output) {
		this.lastOutput=output;
		outputs.add(output);
		if(output.startsWith("ID:")){
			a=new ArrayList<Integer>();
			saveLines=output.split("\n");
			for(int i=0; i<saveLines.length;i++){
				saveIDs=saveLines[i].split("\\s+");
				a.add(Integer.parseInt(saveIDs[1]));
			}
		}
	}
	@Override
	public void outM(String output){
		System.out.println(output);
		outputsM.add(output);
	}
	/* (non-Javadoc)
	 * @see Client.UI#outln(java.lang.String)
	 */
	@Override
	public void outln(String output){
		//do nothing
	}
	/* (non-Javadoc)
	 * @see Client.UI#readln()
	 */
	@Override
	public String readln(){
		return in.nextLine();
	}
	/**
	 * Takes a random ID out of the ArrayList if there are elements in it.
	 * If the ArrayList is null, 0 is retured.
	 * @return random ID or 0 is ArrayList is null
	 */
	public int getRandomID(){
		if(a!=null){
			return r.nextInt(a.size());
		}else{
			return 0;
		}
		
	}
	public boolean isClientAlive(){
		return clientsAlive;
	}
	public void setClientsAlive(boolean clientsAlive) {
		this.clientsAlive = clientsAlive;
	}
	public String getLastOutput() {
		return outputs.get(outputs.size()-1);
	}
	public String getLastOutputM() {
		return outputsM.get(outputsM.size()-1);
	}
	public String getPreLastOutputM() {
		return outputsM.get(outputsM.size()-2);
	}
	public String getOutputBeforeEnd() {
		return outputs.get(outputs.size()-2);
	}
	public String getOutputOnIndex(int i) {
		return outputs.get(i);
	}
	
}
