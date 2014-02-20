package loadtest;

import java.io.ByteArrayInputStream;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;
import java.util.Timer;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import analytics.BidCountPerMinuteWatcher;
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
	private Timer bid;
	private Timer create;
	private Timer list;

	public FakeCli(int aucpM, int aucD, int update, int bidspM){
		starttime=System.currentTimeMillis();
		new CheckDuration(this,starttime);
		clientsAlive=true;
		is=new ByteArrayInputStream("".getBytes());
		in = new Scanner(is);
		new AuctionThread(aucpM, aucD, this);
		bid= new Timer();
		create=new Timer();
		list=new Timer();
		create.schedule(new AuctionThread(aucpM, aucD, this), 0, 60000/aucpM);
		bid.schedule(new BidThread(this,bidspM,starttime), 500,60000/bidspM); 
		list.schedule(new ListThread(this, update), 1000, update*1000);	
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
}
