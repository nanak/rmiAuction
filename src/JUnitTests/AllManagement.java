package JUnitTests;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

//JUST FOR QUICK TESTING
@RunWith(Suite.class)
@SuiteClasses({AddStepTest.class, BillTest.class, LoginTest.class, LogoutTest.class, RemoveStepTest.class, StepsTest.class,
	CommandFactoryTest.class, ManagementClientTest.class, ServerTest.class})
public class AllManagement {
	
}
