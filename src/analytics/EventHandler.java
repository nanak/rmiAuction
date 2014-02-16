package analytics;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;

import Event.Event;
import Event.UserDisconnected;
import Event.UserEvent;
import Event.UserLogin;
import Event.UserLogout;
import Event.UserSessionTimeAvg;
import Event.UserSessionTimeMax;
import Event.UserSessionTimeMin;


/**
 * Takes events from the AnalyticsServer and processes them
 * @author Daniel
 *
 */
public class EventHandler implements Runnable{
	private AnalyticsServer as;
	
	//Auctions
	private int auctionCount=0; //Count all auctions
	private int auctionTimeSum = 0; //
	private HashSet<Integer> bidAuctions; //Saves all auction on which were bid. -> synchronized!
	
	
	//User
	private long userTimeMin = 0;
	private double userTimeMax = 0;
	private long userTimeTotal = 0;
	private long userTimeAVG = 0;
	private long userSessionsTotal = 0; //Saves how many user were loggend in (In Order to allow login/logout counting)
	private ConcurrentHashMap<String, UserEvent> logedInUser; //Saves all currently loggedIn User
	
	//Bid
	private int bidtotal= 0;
	private int bidMax = 0;
	private Date running; //Saves the time to calculate how long the System is running
	
	/**
	 * Creates the EventHandler and sets the analyticsServer
	 * 
	 * @param a	AnalyticsServer where all the Events are
	 */
	public EventHandler(AnalyticsServer a){
		as = a;
		running = new Date();
		
	}
	
	/**
	 * Performs every 60 seconds a processing of all Events
	 */
	public void run(){
		while(true){
//			Thread.sleep(60000);
			while(as.getIncomingEvents().isEmpty()){
				
				Event event = null;
				try {
					event = as.getIncomingEvents().take();
					as.getDispatchedEvents().put(event); //Put them into the dispatcher
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				/**
				 * Check types
				 */
				//UserEvent
				if(event instanceof UserEvent){
					UserEvent uevent = (UserEvent)event;
					//Check if new user has loggend in, Put them inot the list
					if(uevent instanceof UserLogin){
						userSessionsTotal++;
						if(logedInUser.contains(uevent.getUsername())){
							System.out.println("User already logged in. Check auction-server");
						}
						else
							logedInUser.put(uevent.getUsername(), uevent);
					}
					//Check if user LoggedOut or Disconnected
					else if(uevent instanceof UserLogout || uevent instanceof UserDisconnected){
						//Get matching UserLogin
						UserEvent ul = logedInUser.get(((UserEvent) event).getUsername());
						logedInUser.remove(ul); //Delete him from list
						if(ul == null)
							System.out.println("User logged out, but didn't log in -> Check your server");
						else{
							//Calculate SessionTime
							long sessionTime = uevent.getTimestamp() - ul.getTimestamp();
							if(sessionTime > userTimeMax){
								userTimeMax = sessionTime;
								//New Event
								java.util.Date date= new java.util.Date();
								UserSessionTimeMax umaxEvent = new UserSessionTimeMax( "" +UUID.randomUUID().getMostSignificantBits(), "USER_SESSIONTIME_MAX",date.getTime(), userTimeMax);
								//Try to push
								try {
									as.getDispatchedEvents().put(umaxEvent);
								} catch (InterruptedException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
							}
							if(sessionTime < userTimeMin ){
								userTimeMin = sessionTime;
								//New Event
								java.util.Date date= new java.util.Date();
								UserSessionTimeMin uminEvent = new UserSessionTimeMin( "" +UUID.randomUUID().getMostSignificantBits(), "USER_SESSIONTIME_MIN",date.getTime(), userTimeMin);
								//Try to push
								try {
									as.getDispatchedEvents().put(uminEvent);
								} catch (InterruptedException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
							}
							
							userTimeTotal += sessionTime;
							userTimeAVG = userTimeTotal/userSessionsTotal;
							
							//Generate new Event for average
							//New Event
							java.util.Date date= new java.util.Date();
							UserSessionTimeAvg umaxEvent = new UserSessionTimeAvg( "" +UUID.randomUUID().getMostSignificantBits(), "USER_SESSIONTIME_AVG",date.getTime(), userTimeAVG);
							//Try to push event into Queue
							try {
								as.getDispatchedEvents().put(umaxEvent);
							} catch (InterruptedException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
							
						}
					}
				}
			}
		}
	}
}
