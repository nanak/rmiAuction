package loadtest;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Properties {

	private int clients;

	private int auctionsPerMin;

	private int auctionDuration;

	private int updateIntervalSec;

	private int bidsPerMin;

	public void setClients(int number) {

	}

	public void setAuctionsPerMin(int auctions) {

	}

	public void setAuctionDuration(int duration) {

	}

	public void setUpdateIntervalSec(int interval) {

	}

	public void setBidsPerMin(int bids) {

	}

	public String toString() {
		return "Clients: "+clients+"\nAuctions per minute: "+auctionsPerMin+"\nAuction duration: "+
				auctionDuration+"\nUpdate interval in seconds: "+updateIntervalSec+
				"\nBids per minute: "+bidsPerMin;

	}
	public void setFromFile(String path){
		BufferedReader br = null;
		int i=0;
		int[] val=new int[5];
		try {
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
					if(s[1].contains("*")){
						numb=s[1].split("\\*");
						n1=Integer.parseInt(numb[0].replaceAll("\\s+",""));
						n2=Integer.parseInt(numb[1].replaceAll("\\s+",""));
						val[i]=n1*n2;
					}
					else
						val[i]=Integer.parseInt(s[1].replaceAll("\\s+",""));
					i++;
				}
			}
		} catch (IOException | ArrayIndexOutOfBoundsException e) {
			System.err.println("An error reading the properties file occured:");
			e.printStackTrace();
		} finally {
			try {
				if (br != null)br.close();
			} catch (IOException ex) {
				System.err.println("An error closing file occured:");
				ex.printStackTrace();
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


}
