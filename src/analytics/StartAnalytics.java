package analytics;

import java.util.Scanner;

/**
 * Starts the Analytics Server and shuts it down, it a key is pressed
 * 
 * @author Daniel Reichmann
 * @version 2012-02-23
 *
 */
public class StartAnalytics {
	public static void main(String[] args) {
		AnalyticsServer as = new AnalyticsServer();
		Scanner in = new Scanner(System.in);
		in.nextLine();
		as.shutdown();
	}
}
