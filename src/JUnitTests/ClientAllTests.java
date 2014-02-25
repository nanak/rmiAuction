package JUnitTests;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;
/**
 * Runs all tests for the Client package
 * 
 * @author Nanak Tattyrek
 * @version 24.02.2014
 * @email ntattyrek@student.tgm.ac.at
 *
 */
@RunWith(Suite.class)
@SuiteClasses({ClientTest.class, TaskExecuterTest.class, CLITest.class})
public class ClientAllTests {
	
}
