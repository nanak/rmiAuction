package JUnitTests;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;
import billing.BillingServerSecure;
import billing.PriceStep;
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
			//ToDo read price steps
			assertTrue(true);
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
			assertTrue(true);
		} catch (PriceStepIntervalOverlapException e) {
		}
	}
	
	@Test
	public void toStringTest(){
		try {
			PriceStep p= new PriceStep(0, 10, 5, 5);
			System.out.println(p.toString());
			assertEquals(p.toString(), "PriceSteps [startPrice=0.0, endPrice=10.0, fixedPrice=5.0, variablePricePercent=5.0]");
		} catch (IllegalValueException e) {
		}
		
	}
}
