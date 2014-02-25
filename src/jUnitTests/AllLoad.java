package jUnitTests;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

/**
 * Tests all LoadComponents
 * 
 * @author Daniel
 *
 */
@RunWith(Suite.class)
@SuiteClasses({PropertiesTest.class, FakeCliTest.class, Loading.class})
public class AllLoad {
	
}
