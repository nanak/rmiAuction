package jUnitTests;

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

@SuiteClasses({ManagementClientTets.class, ClientTset.class, EventTest.class, BillingServerSecureTest.class, ServerTest.class, EventHandlerTest.class,AddStepTest.class, BillTest.class, LoginTest.class, LogoutTest.class, RemoveStepTest.class, StepsTest.class,
	CommandFactoryTest.class, PropertiesTest.class, Loading.class,FakeCliTest.class,  FileHandlerUnitTest.class })
public class AllClasses {
	
}