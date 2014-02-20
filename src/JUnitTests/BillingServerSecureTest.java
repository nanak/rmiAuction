package JUnitTests;

import static org.junit.Assert.*;

import java.rmi.RemoteException;

import org.junit.Before;
import org.junit.Test;
import billing.BillingServerSecure;
import billing.PriceStep;
import billing.RemoteBillingServerSecure;
import Exceptions.IllegalValueException;
import Exceptions.PriceStepIntervalOverlapException;

/**
 * tests the functions from BillingServerSecure
 * 
 * @author Rudolf Krepela
 * @email rkrepela@student.tgm.ac.at
 * @version 11.02.2014
 *
 */
public class BillingServerSecureTest{
	
	@Before
	public void setUp() {
	}

	@Test
	public void createPriceStepTest() {
		BillingServerSecure s= new BillingServerSecure();
		try {
			s.createPriceStep(0, 10, 5, 10);
			s.createPriceStep(10, 100, 5, 10);
			s.createPriceStep(200, 300, 7, 9);
			s.createPriceStep(300, 0, 5, 6);
			assertEquals(s.getPriceSteps(),"Min_Price	Max_Price	Max_Price	Fee_Variable\n0,00		10,00		5,00		10,00\n10,00		100,00		5,00		10,00\n200,00		300,00		7,00		9,00\n300,00		INFINITY	5,00		6,00");
		} catch (PriceStepIntervalOverlapException e) {
		}
	}
	
	@Test(expected=PriceStepIntervalOverlapException.class)
	public void createPriceStepTestPriceStepIntervalOverlapException() throws PriceStepIntervalOverlapException {
		BillingServerSecure s= new BillingServerSecure();
			s.createPriceStep(0, 10, 5, 10);
			s.createPriceStep(0, 11, 5, 6);
	}
	
	@Test(expected=PriceStepIntervalOverlapException.class)
	public void createPriceStepTestPriceStepIntervalOverlapException2() throws PriceStepIntervalOverlapException {
		BillingServerSecure s= new BillingServerSecure();
			s.createPriceStep(10, 0, 5, 10);
			s.createPriceStep(11, 0, 5, 6);
	}
	
	@Test(expected=PriceStepIntervalOverlapException.class)
	public void createPriceStepTestPriceStepIntervalOverlapException3() throws PriceStepIntervalOverlapException {
		BillingServerSecure s= new BillingServerSecure();
			s.createPriceStep(10, 0, 5, 10);
			s.createPriceStep(11, 100, 5, 6);
	}

	@Test
	public void deletePriceStepTest() {
		BillingServerSecure s= new BillingServerSecure();
		try {
			s.createPriceStep(0, 10, 5, 10);
			s.deletePriceStep((double)0, (double)10);
			s.createPriceStep(0, 10, 5, 10);
			s.createPriceStep(0, 10, 5, 10);
			assertTrue(true);
		} catch (RemoteException e) {
		}
	}
	
	@Test
	public void toStringTest(){
		try {
			PriceStep p= new PriceStep(0, 10, 5, 5);
			assertEquals(p.toString(),"0,00		10,00		5,00		5,00");
		} catch (IllegalValueException e) {
		}
		
	}
	
	@Test
	public void billAuctionAndGetBillTest(){
		BillingServerSecure s= new BillingServerSecure();
		try {
			s.createPriceStep(0, 10, 5.6,5);
			s.createPriceStep(10, 30, 10,10);
		} catch (PriceStepIntervalOverlapException e) {
			e.printStackTrace();
		}
		s.billAuction("test", 1, 9);
		s.billAuction("test", 2, 20);
		s.billAuction("test", 3, 10);
		s.billAuction("t", 3, 10);
		assertEquals("auction_ID	strike_price	fee_fixed	fee_variable	fee_total\n1		9,00		5,00		0,50		5,50		\n2		20,00		10,00		2,00		12,00		\n3		10,00		10,00		1,00		11,00		\n", s.getBill("test"));
	}
	@Test
	public void billAuctionAndGetBillTestIntervalDoesNotExist(){
		BillingServerSecure s= new BillingServerSecure();
		try {
			s.createPriceStep(0, 10, 5.6,5);
		} catch (PriceStepIntervalOverlapException e) {
		}
		s.billAuction("test", 1, 100);
		assertEquals("auction_ID	strike_price	fee_fixed	fee_variable	fee_total\n1		100,00		0,00		0,00		0,00		\n", s.getBill("test"));
	}
	@Test
	public void getBillTestFalse(){
		BillingServerSecure s= new BillingServerSecure();
		assertEquals("No bill for the user tz available.", s.getBill("tz"));
	}
}
