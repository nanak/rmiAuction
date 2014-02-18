package loadtest;

import java.io.ByteArrayInputStream;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import Client.UI;

/**
 * This implementation defines the input via InputStream.
 * This class also starts the threads which write the input and provides to method to shutdown all threads.
 * 
 * @author Michaela Lipovits
 * @version 2014-02-16
 */
public class FakeCli implements UI{
	private Scanner in;
	private ByteArrayInputStream is;
	private long starttime;
	private ArrayList<Integer> a;
	private Random r = new Random();
	private String[] saveLines;
	private String[] saveIDs;
	private boolean clientsAlive;
	private ExecutorService pool;

	public FakeCli(int aucpM, int aucD, int update, int bidspM){
		starttime=System.currentTimeMillis();
		new CheckDuration(this,starttime);
		clientsAlive=true;
		is=new ByteArrayInputStream("".getBytes());
		in = new Scanner(is);
		
		// TODO ask why i should use a thread pool here?
//		pool= Executors.newCachedThreadPool();
//		pool.submit(new AuctionThread(aucpM, aucD, this));
//		pool.submit(new ListThread(this, update));
//		pool.submit(new BidThread(this,bidspM,starttime));
		new AuctionThread(aucpM, aucD, this);
		new BidThread(this,bidspM,starttime);
		new ListThread(this, update);
		
	}
	public FakeCli(String cmd) {
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
	public void outln(String output){
		//do nothing
	}
	@Override
	public String readln(){
		return in.nextLine();
	}
	public int getRandomID(){
		return r.nextInt(a.size());
		
	}
	public boolean isClientAlive(){
		return clientsAlive;
	}
	public void setClientsAlive(boolean clientsAlive) {
		this.clientsAlive = clientsAlive;
	}
}
