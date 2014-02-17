package JUnitTests;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

/**
 * Combines all classes and runs them to see the total coverage
 * 
 * @author Rudolf Krepela
 * @email rkrepela@student.tgm.ac.at
 * @version 11.02.2013
 *
 */
@RunWith(Suite.class)
@SuiteClasses({BillingServerSecureTest.class, TestEventHandler.class })
public class AllClassesTest {
	
}
