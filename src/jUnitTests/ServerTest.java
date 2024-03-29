/**
 * 
 */
package jUnitTests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.concurrent.ConcurrentHashMap;

import model.Auction;
import model.BidMessage;
import model.CreateMessage;
import model.ListMessage;
import model.LoginMessage;
import model.LogoutMessage;
import model.User;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import server.Server;
import analytics.AnalyticsServer;
import billing.BillingServer;
import billing.BillingServerSecure;
import billing.RemoteBillingServerSecure;
import billing.StartBillingServer;

/**
 * Tests all Server Methods
 * @author Reichmann
 *
 */
public class ServerTest {
	private Server server;
	private BillingServer bs;
	private AnalyticsServer as;
	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		StartBillingServer start = new StartBillingServer();

		 bs =new BillingServer();
		BillingServerSecure bss = new BillingServerSecure();
		RemoteBillingServerSecure rbss = new RemoteBillingServerSecure(bss);
		 as = new AnalyticsServer();
		bs.initRmi(bs, rbss);
		server = new Server();
	}
	/**
	 * Shutsdown the servers
	 */
	@After
	public void end(){
		bs.shutdown();
		as.shutdown();
		server.setActive(false);
		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	/**
	 * Test method for {@link server.Server#request(model.Message)}.
	 */
	@Test
	public void testRequestLogin() {
		LoginMessage login = new LoginMessage();
		login.setName("name");
		login.setAdresse("127.0.0.1");
		login.setTcpPort(123);
		login.setUdpPort(123);
		
		String wert = server.request(login); //Tests the login
		assertEquals(wert,"Successfully suscribed and logged in as: name");
		
		wert = server.request(login); //Tests that it isn't possible to log in twice
		assertEquals(wert,"This User is allready logged in please log out first!");
		
		System.out.println(server.getUser().get(0));
		assertEquals(server.getUser().get("name").getName(),"name"); //Tests if the user really exists
		
		server.getUser().get("name").setActive(false); //Login again without messages
		wert = server.request(login);
		assertEquals(wert,"Successfully logged in as: name\nUnread messages: No Messages");
		
		ArrayList<String> messages = new ArrayList();
		messages.add("Hallo");
		server.getUser().get("name").setMessages(messages);
		server.getUser().get("name").setActive(false); //Login again with one message
		wert = server.request(login);
		assertEquals(wert,"Successfully logged in as: name\nUnread messages: Hallo\n");
	}
	
	@Test
	public void testRequestLogout() {
		LoginMessage login = new LoginMessage();
		login.setName("name");
		login.setAdresse("127.0.0.1");
		login.setTcpPort(123);
		login.setUdpPort(123);
		
		String wert = server.request(login); //Before testing logout i must test login
		assertEquals(wert,"Successfully suscribed and logged in as: name");
		
		LogoutMessage logout = new LogoutMessage();
		logout.setName("name");
		
		String wert4 = server.request(logout); //tests the logout
		assertEquals(wert4,"Succesfully logged out as: name");
		wert4 = server.request(logout); //tests the logout twice should cause an error
		assertEquals(wert4,"Error you must log in first!");
		
		logout.setName("name2"); //Causes an error because this user doesn't exists
		wert4 = server.request(logout); 
		assertEquals(wert4,"This User doesn't exists!");
	}
	
	@Test
	public void testRequestCreate() {
		LoginMessage login = new LoginMessage();
		login.setName("name");
		login.setAdresse("127.0.0.1");
		login.setTcpPort(123);
		login.setUdpPort(123);
		
		String wert = server.request(login); //Before testing create i must test login and need a user
		assertEquals(wert,"Successfully suscribed and logged in as: name");
		
		Long duration = 10L;
		CreateMessage create = new CreateMessage();
		create.setDesc("laptop");
		create.setName("name");
		create.setDuration(duration);
		
		String wert1 = server.request(create);
		assertEquals(wert1,"You have succesfully created a new auction!"); //Tests the creation of an auction	
		assertEquals(server.getAuction().get(0).getDescription(),"laptop"); //Tests if it is on the server
	
		create.setName("name2");
		wert1 = server.request(create);
		assertEquals(wert1,"This User doesn't exists please log in first!");//Tests the creation of an auction without an existing user should pass an error message
	}
	
	@Test
	public void testRequestBid() {
		LoginMessage login = new LoginMessage();
		login.setName("name");
		login.setAdresse("127.0.0.1");
		login.setTcpPort(123);
		login.setUdpPort(123);
		
		String wert = server.request(login); //Before testing bid i must test login and need a user
		assertEquals(wert,"Successfully suscribed and logged in as: name");
		Long duration = 10L;
		CreateMessage create = new CreateMessage();
		create.setDesc("laptop");
		create.setName("name");
		create.setDuration(duration);
		String wert1 = server.request(create);
		assertEquals(wert1,"You have succesfully created a new auction!"); //Before testing bid i must test login and need a user
		
		BidMessage bid = new BidMessage();
		
		bid.setAmount(123.123);
		bid.setId(0);
		bid.setName("name"); //tests the bid
		

		String wert12 = server.request(bid);
		assertEquals(wert12,"You cannot bid on your own auction!");
		
		login.setName("name1"); //Creating another user to bid 
		server.request(login);
		
		bid.setName("name1"); // bid once on an auction
		wert12 = server.request(bid);
		assertEquals(wert12,"You successfully bid with 123.123 on 'laptop'.");	
		
		bid.setAmount(200.0); //tests the overbidding
		wert12 = server.request(bid);
		assertEquals(wert12,"You successfully bid with 200.0 on 'laptop'.");
		
		bid.setAmount(100.0); //tests the bid with a lower bid
		wert12 = server.request(bid);
		assertEquals(wert12,"Your bid must be higher then the current bid! The current bid is: 200.0");
		
		bid.setName("name2"); //bidding with a user that doesn't exists
		wert12 = server.request(bid);
		assertEquals(wert12,"This User doesn't exists!");
		
		bid.setId(1); //bid on auction that doesn't exists
		bid.setName("name1");
		wert12 = server.request(bid);
		assertEquals(wert12,"There is no Auction with this ID!");
		
		server.getAuction().get(0).setFinished(true); //bid on an auction that is over
		bid.setId(0);
		wert12 = server.request(bid);
		assertEquals(wert12,"The auction 'laptop' is already over you can not bid on this auction anymore!");
	}

	@Test
	public void testRequestList() {
		LoginMessage login = new LoginMessage();
		login.setName("name");
		login.setAdresse("127.0.0.1");
		login.setTcpPort(123);
		login.setUdpPort(123);
		
		String wert = server.request(login); //Before testing list i must test login and need a user
		assertEquals(wert,"Successfully suscribed and logged in as: name");
		
		Long duration = 10L;
		CreateMessage create = new CreateMessage();
		create.setDesc("laptop");
		create.setName("name");
		create.setDuration(duration);
		String wert1 = server.request(create);
		assertEquals(wert1,"You have succesfully created a new auction!"); //Before testing list i must test login and need a auction
		
		ListMessage list = new ListMessage();
		list.setName("name");
		
		String wert2 = server.request(list);
		assertEquals(wert2,"ID: 0    Description: laptop    Highestbid: 0.0    from: none\n");
		
		User user = new User();
		user.setName("hallo");
		server.getAuction().get(0).setLastUser(user);
		
		wert2 = server.request(list);
		assertEquals(wert2,"ID: 0    Description: laptop    Highestbid: 0.0    from: hallo\n");
	}
	
	/**
	 * Test method for {@link server.Server#getTcpPort()}.
	 */
	@Test
	public void testGetTcpPort() {
		assertEquals(server.getTcpPort(),0);
	}

	/**
	 * Test method for {@link server.Server#setTcpPort(int)}.
	 */
	@Test
	public void testSetTcpPort() {
		server.setTcpPort(123);
		assertEquals(server.getTcpPort(),123);	
	}

	/**
	 * Test method for {@link server.Server#setUser(java.util.ArrayList)}.
	 */
	@Test
	public void testSetUser() {
		User user = new User();
		user.setName("name");
		ConcurrentHashMap<String,User> users = new ConcurrentHashMap<String, User>();
		users.put("test",user);
		server.setUser(users);
		assertEquals(server.getUser().get("test").getName(),"name");
	}

	/**
	 * Test method for {@link server.Server#setAuction(java.util.ArrayList)}.
	 */
	@Test
	public void testSetAuction() {
		User user = new User();
		user.setName("name");
		Auction auction = new Auction(user ,"auction",10L);
		ConcurrentHashMap<Integer,Auction> auctions = new ConcurrentHashMap<Integer, Auction>();
		auctions.put(0, auction);
		server.setAuction(auctions);
		assertEquals(server.getAuction().get(0).getDescription(),"auction");
	}

	/**
	 * Test method for {@link server.Server#isActive()}.
	 */
	@Test
	public void testIsActive() {
		server.setActive(true);
		assertEquals(server.isActive(),true);
	}

	/**
	 * Tests if Auction ended with Bid
	 */
	@Test
	public void testAuctionEndedWithBid(){
		server.request(new LoginMessage("test", "1.2.3.4", 0, 0));
		server.request(new CreateMessage("test", "nein", 1l));
		server.request(new LoginMessage("test2", "1.2.3.4", 0, 0));
		server.request(new BidMessage("test2", 0, 5));
		try {
			Thread.sleep(1200);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		assertTrue(!server.getAuction().get(0).isActive());
		assertTrue(server.getAuction().get(0).getLastUser()!=null);
	}
	
	/**
	 * Tests if Auction ended without bid
	 */
	@Test
	public void testAuctionEndedWithoutBid(){
		server.request(new LoginMessage("test", "1.2.3.4", 0, 0));
		server.request(new CreateMessage("test", "nein", 1l));
		try {
			Thread.sleep(1200);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		assertTrue(!server.getAuction().get(0).isActive());

		assertTrue(server.getAuction().get(0).getLastUser()==null);
	}
}
