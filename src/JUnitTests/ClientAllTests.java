package JUnitTests;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({ClientTest.class, TaskExecuterTest.class, CLITest.class})
public class ClientAllTests {
	
}
