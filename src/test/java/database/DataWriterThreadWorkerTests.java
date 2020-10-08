package database;

import static org.junit.Assert.assertTrue;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import socketserver.DataQueue;
/**
 * Test class for the DataWriterThreadWoker
 * @author Michael R.
 *
 */
public class DataWriterThreadWorkerTests {
	DataQueue fQueue;
	DataWriterThreadWorker fWorker;
	
	

	
	/**
	 * performs the initial procedures before testing.
	 */
	@Before public void initialise() {
		//System.out.println("");
		setup();
	}
	
	/**
	 * sets the variables to initial testing conditions
	 */
	public void setup() {
		fQueue = new DataQueue(5);
		fWorker = new DataWriterThreadWorker(1, fQueue);
		 
	}
	
	/**
	 * Ensure that the thread starts and runs
	 */
	@Test public void testThreadIsRunning() {
		//fWorker.run();
		//assertTrue(fWorker.isAlive());
	}
	
	@After public void tearDown() {
		fWorker.stop();
	}
	
	
}
