package management;

import java.util.concurrent.ConcurrentLinkedQueue;

//import analytics.AnalyticTaskComputing;
//import billing.RemoteBillingServerSecure;

public class ManagmentClient implements ClientInterface {

	private boolean printAutomatic;

//	private UI ui;

	private CommandFactory cf;

	private BillingServer bs;

//	private AnalyticTaskComputing atc;

//	private RemoteBillingServerSecure rsbs;

	private ConcurrentLinkedQueue<String> events;

	public static void main(String[] args) {

	}


	/**
	 * @see management.ClientInterface#notify(java.lang.String)
	 */
	public void notify(String s) {

	}

}
