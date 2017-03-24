package persistence;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class WordMapperTester {

	@Before
	public void setUp() throws Exception {
	}

	@Test
	public void testGetInstance() {
		assertNotNull("Instance not created", WordMapper.UNIQUEINSTANCE);
	}
	
	@Test
	public void testGetRandom() {
		assertNotNull("Instance not created", WordMapper.randInt(1, 20));
	}
	
	@Test
	public void testGetConnection() {
		assertNotNull("Connection not set up", WordMapper.UNIQUEINSTANCE.getWordRandomly(1));
		System.out.println(WordMapper.UNIQUEINSTANCE.getWordRandomly(1));
	}

}
