package analytics;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;

import Event.AuctionEnded;
import Event.AuctionEvent;
import Event.AuctionStarted;
import Event.AuctionSuccessRatio;
import Event.AuctionTimeAvg;
import Event.BidCountPerMinute;
import Event.BidEvent;
import Event.BidPlaced;
import Event.BidPriceMax;
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
//	private int auctionCount=0; //Count all auctions
	private ConcurrentHashMap<Long,AuctionStarted> auctions; //Saves all started auctions
	private HashSet<Long> bidAuctionIDs; //Saves auctions on which were bid.
	private long auctionTimeAVG = 0L;
	private long auctionTimeSUM= 0L;
	private long auctionsEnded = 0L; //Counts ended auctions
	private static long auctionSuccessfull =0L; //Counts auctionSuccessFull
	
	//User
	private long userTimeMin = Long.MAX_VALUE;
	private double userTimeMax = 0L;
	private long userTimeTotal = 0L;
	private long userTimeAVG = 0L;
	private long userSessionsTotal = 0L; //Saves how many user were loggend in (In Order to allow login/logout counting)
	private ConcurrentHashMap<String, UserEvent> logedInUser; //Saves all currently loggedIn User
	
	//Bid
	private long  bidCount= 0L;
	private double bidMax = 0;

	
	/**
	 * Creates the EventHandler and sets the analyticsServer
	 * 
	 * @param a	AnalyticsServer where all the Events are
	 */
	public EventHandler(AnalyticsServer a){
		as = a;
		logedInUser = new ConcurrentHashMap<>();
		auctions = new ConcurrentHashMap();
		bidAuctionIDs = new HashSet();
	}
	
	/**
	 * Performs every 60 seconds a processing of all Events
	 */
	public void run(){
		System.out.println("Start receiving");
		while(true){				
			
			Event event = null;
			try {
				event = as.getIncomingEvents().take();
				
				as.getDispatchedEvents().add(event);
			} catch (InterruptedException e2) {
				// TODO Auto-generated catch block
				e2.printStackTrace();
			}
//			while(!as.getIncomingEvents().isEmpty()){
				System.out.println("Got event");
				System.out.println(event.getType());
				
				
				/**
				 * Check types
				 * 
				 * -----------------------------------------
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
				/**
				 * AuctionEvents
				 * -----------
				 */
				else if(event instanceof AuctionEvent){
					//Check if auctionStarted
					AuctionEvent aevent = (AuctionEvent)event;
					//AuctionStarted; put it into the list
					if(aevent instanceof AuctionStarted){
						auctions.put(aevent.getAuctionID(), (AuctionStarted)aevent);
					}
					else if(aevent instanceof AuctionEnded){
						
						auctionsEnded++;
						System.out.println(aevent.getAuctionID());
						//Test if auction was successfull
						if(bidAuctionIDs.contains(aevent.getAuctionID()))
							auctionSuccessfull++;
						//Get corresponding auctionStarted Event
						AuctionStarted astarted = auctions.get(aevent.getAuctionID());
						long atime = aevent.getTimestamp() - astarted.getTimestamp();
						auctionTimeSUM += atime;
						auctionTimeAVG = auctionTimeSUM/auctionsEnded;
						//Create auctionAverageTime Event
						java.util.Date date= new java.util.Date();
						AuctionTimeAvg atavg = new AuctionTimeAvg("" +UUID.randomUUID().getMostSignificantBits(), "AUCTION_TIME_AVG", date.getTime(), auctionTimeAVG);
						//Try to push event into Queue
						try {
							as.getDispatchedEvents().put(atavg);
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						//Calculate successRatio
						System.out.println("Successfull " + auctionSuccessfull);
						System.out.println("Ended " + auctionsEnded);
						float asuccess = ((float)auctionSuccessfull)/auctionsEnded;
						System.out.println(asuccess);
						//Create Event
						date= new java.util.Date();
						System.err.println("AuctionSucess");
						AuctionSuccessRatio asuc = new AuctionSuccessRatio("" +UUID.randomUUID().getMostSignificantBits(), "AUCTION_SUCCESS_RATIO", date.getTime(), asuccess);
						//Try to push event into Queue
						try {
							as.getDispatchedEvents().put(asuc);
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				}//AuctionEvent
				/**
				 * BidEvents
				 * -------------------------------------------------------------------------------------------------------------------
				 * BidOverBid und BidWon don't need to be handled
				 */
				else if(event instanceof BidEvent){
					BidEvent bevent = (BidEvent) event;
					//Test if BidPlaced
					if(bevent instanceof BidPlaced){
						//Put into successfull Auctions
						synchronized (bidAuctionIDs) {
							bidAuctionIDs.add(bevent.getAuctionID());
						}
						//Update BidCOunt
						bidCount++;
						//Test if bid is higher then highest bid
						if(bevent.getPrice() > bidMax){
							bidMax = bevent.getPrice();
							Date now = new Date();
							BidPriceMax bm = new BidPriceMax("" +UUID.randomUUID().getMostSignificantBits(), "BID_PRICE_MAX", now.getTime(), bidMax);
							//Try to push event into Queue
							try {
								as.getDispatchedEvents().put(bm);
							} catch (InterruptedException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						}
							
						//Get Time how long system is running and calculate bidcount/minute
//						Date now = new Date();
//						int minutes = (int)((now.getTime() - running)/1000)/60 + 1; //Round to full minutes upwards
//						double bpm = bidCount/minutes;
//						//Send event
//						now = new Date();
//						BidCountPerMinute bp = new BidCountPerMinute("" +UUID.randomUUID().getMostSignificantBits(),"BID_COUNT_PER_MINUTE", now.getTime(), bpm);
						//Try to push event into Queue
//						try {
//							as.getDispatchedEvents().put(bp);
//						} catch (InterruptedException e) {
//							// TODO Auto-generated catch block
//							e.printStackTrace();
//						}
					}
				
			}	
				as.notifyClients();
		}
		
	}

	public long getBidCount() {
		return bidCount;
	}

}
