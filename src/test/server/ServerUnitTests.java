package test.server;

import org.junit.* ;
import static org.junit.Assert.* ;

public class ServerUnitTests {
	
	@Before
	public void setup() {
	}
	
	@After
	public void teardown() {
	}
	
	@Test
	public void test_1() {
		assertEquals("OK", "OK");
		assertTrue(true);
		assertFalse(false);
	}

	public static void main(String[] args) {
		
		String[] testClasses = new String[] {
				"test.server.ServerUnitTests",
				"test.java.indexer.server.daos.BatchDAOTest",
				"test.java.indexer.server.daos.FieldDAOTest",
				"test.java.indexer.server.daos.ProjectDAOTest",
				"test.java.indexer.server.daos.RecordDAOTest",
				"test.java.indexer.server.daos.UserDAOTest"
		};

		org.junit.runner.JUnitCore.main(testClasses);
	}
	
}

