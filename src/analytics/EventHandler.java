package analytics;

import java.util.Date;
import java.util.HashSet;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

import event.AuctionEnded;
import event.AuctionEvent;
import event.AuctionStarted;
import event.AuctionSuccessRatio;
import event.AuctionTimeAvg;
import event.BidEvent;
import event.BidPlaced;
import event.BidPriceMax;
import event.Event;
import event.UserDisconnected;
import event.UserEvent;
import event.UserLogin;
import event.UserLogout;
import event.UserSessionTimeAvg;
import event.UserSessionTimeMax;
import event.UserSessionTimeMin;
import analytics.exceptions.AuctionEndedButNotStartedException;
import analytics.exceptions.InvalidUserLogoutException;


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

	private boolean active = true; //as long it is true, the EventHandler runs


	
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
		while(active){				
			
			Event event = null;
			try {
				event = as.getIncomingEvents().take();
				if(!active)
					break;
				as.getDispatchedEvents().add(event);
			} catch (InterruptedException e2) {
				e2.printStackTrace();
			}
				
				
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
						
						if(ul == null)
							try {
								throw new InvalidUserLogoutException();
							} catch (InvalidUserLogoutException e1) {
								System.out.println(e1.getMessage());
								continue;
							}
						else{
							logedInUser.remove(ul); //Delete him from list
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
						//Get corresponding auctionStarted Event
						AuctionStarted astarted = auctions.get(aevent.getAuctionID());
						if(astarted == null)
							try {
								throw new AuctionEndedButNotStartedException();
							} catch (AuctionEndedButNotStartedException e1) {
								System.out.println("Auction ended but there is no startauction -> Therefore no Calculation for Statistics");
								continue;
							}
						auctionsEnded++;
						System.out.println(aevent.getAuctionID());
						//Test if auction was successfull
						if(bidAuctionIDs.contains(aevent.getAuctionID()))
							auctionSuccessfull++;
						
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
								e.printStackTrace();
							}
						}
					}
				
			}	
				as.notifyClients();
		}
		
	}

	public long getBidCount() {
		return bidCount;
	}

	
	public void setActive(boolean b) {
		active =false;		
	}

}
