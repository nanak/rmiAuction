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
@SuiteClasses({BillingServerSecureTest.class, EventHandlerTest.class,AddStepTest.class, BillTest.class, LoginTest.class, LogoutTest.class, RemoveStepTest.class, StepsTest.class,
	CommandFactoryTest.class, ManagementClientTest.class, PropertiesTest.class, FakeCliTest.class, LoadTestTest.class, ClientAllTests.class, FileHandlerUnitTest.class })
public class AllClassesTest {
	
}
