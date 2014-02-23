package loadtest;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

/**
 * Properties class which can set properties for loadtesting and can also read those properties from a file.
 * 
 * @author Michaela Lipovits
 * @version 20140209
 */

public class Properties {

	private int clients;

	private int auctionsPerMin;

	private int auctionDuration;

	private int updateIntervalSec;

	private int bidsPerMin;

	/**
	 * Setter Method for the Amount of Clients.
	 * 
	 * @param number
	 */
	public void setClients(int number) {
		this.clients=number;
	}

	/**
	 * Setter Method for the auctions per minute.
	 * 
	 * @param auctions
	 */
	public void setAuctionsPerMin(int auctions) {
		this.auctionsPerMin=auctions;
	}

	/**
	 * Setter Method fot the duration of auctions.
	 * @param duration
	 */
	public void setAuctionDuration(int duration) {
		this.auctionDuration=duration;
	}

	/**
	 * Setter Method for the update interval.
	 * 
	 * @param interval
	 */
	public void setUpdateIntervalSec(int interval) {
		this.updateIntervalSec=interval;
	}

	/**
	 * Setter Method for the bids per minute.
	 * 
	 * @param bids
	 */
	public void setBidsPerMin(int bids) {
		this.bidsPerMin=bids;
	}

	/**
	 * Method toString which returns all data saved as a String.
	 * 
	 * @return String Informations
	 */
	public String toString() {
		return "Clients: "+clients+"\nAuctions per minute: "+auctionsPerMin+"\nAuction duration: "+
				auctionDuration+"\nUpdate interval in seconds: "+updateIntervalSec+
				"\nBids per minute: "+bidsPerMin;

	}
	/**
	 * Method setFromFile, which reads the file from a given path and saves all relevant data.
	 * 
	 * @param path Path to File
	 * @throws IOException 
	 */
	public void setFromFile(String path) throws IOException{
		BufferedReader br = null;
		int i=0;
		int[] val=new int[5];
		String sCurrentLine;
		br = new BufferedReader(new FileReader(path));
		String[] s;
		String[] numb;
		int n1,n2;
		while ((sCurrentLine = br.readLine()) != null) {
			if(sCurrentLine.startsWith("#")){
				//comments shall be ignored
			}
			else{
				// = and : are used, split String on either of those
				s = sCurrentLine.split("(=)|(\\:)");
				//if two numbers have a mulilicaton sign inbetween
				try{
					if(s[1].contains("*")){
						numb=s[1].split("\\*");
						n1=Integer.parseInt(numb[0].replaceAll("\\s+",""));
						n2=Integer.parseInt(numb[1].replaceAll("\\s+",""));
						val[i]=n1*n2;
					}
					else
						val[i]=Integer.parseInt(s[1].replaceAll("\\s+",""));
				}catch(Exception e){
					//ignore-mode on
				}	
				i++;
			}
		}
		try{
			//save the values
			this.clients=val[0];
			this.auctionsPerMin=val[1];
			this.auctionDuration=val[2];
			this.updateIntervalSec=val[3];
			this.bidsPerMin=val[4];
		}
		catch(ArrayIndexOutOfBoundsException e){
			System.err.println("An Error occured reading the properties file. Wrong number of Values!");
			e.printStackTrace();
		}
	}
	public int getClients() {
		return clients;
	}

	public int getAuctionsPerMin() {
		return auctionsPerMin;
	}

	public int getAuctionDuration() {
		return auctionDuration;
	}

	public int getUpdateIntervalSec() {
		return updateIntervalSec;
	}

	public int getBidsPerMin() {
		return bidsPerMin;
	}

}
