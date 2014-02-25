package jUnitTests;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;
/**
 * Tests all ManagementTests
 * @author Daniel Reichmann
 *
 */
//JUST FOR QUICK TESTING
@RunWith(Suite.class)
@SuiteClasses({AddStepTest.class, BillTest.class, LoginTest.class, LogoutTest.class, RemoveStepTest.class, StepsTest.class,
	CommandFactoryTest.class, ManagementClientTets.class})
public class AllManagement {
	
}
