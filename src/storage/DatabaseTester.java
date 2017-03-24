package storage;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class DatabaseTester {

	@Before
	public void setUp() throws Exception {
	}

	@Test
	public void testGetInstance() {
		assertNotNull("Instance not created", Database.UNIQUEINSTANCE);
	}
	
	@Test
	public void testGetConnection() {
		assertNotNull("Connection not set up", Database.UNIQUEINSTANCE.getConnection());
	}

}
