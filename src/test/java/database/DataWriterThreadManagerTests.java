package database;

import socketserver.DataQueue;
import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * Test class for DataWriterThreadManger class
 * @author Steerpike
 *
 */
public class DataWriterThreadManagerTests {
	private  DataQueue fQueue;
	private  DataWriterThreadManagerTestClass fManager;
	
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
		 fManager = new DataWriterThreadManagerTestClass(5, fQueue);
	}
	
	/**
	 * Checks that the number of threads created is correct
	 */
	@Test public void testCorrectNumberOfWorkers() {
		assertTrue(fManager.fNumberOfWorkers == fManager.getWorkersList().size());
	}
	
	/**
	 * assures that the worker threads are alive.
	 */
	@Test public void testDataWriterRunning() {
		
		fManager.run();
		
		for(Thread t: fManager.getWorkersList()) {
			assertTrue(t.isAlive());
			
		}
		
		
	}
	
	/**
	 * Test class that can be used to test protected variables and methods of the underlying DataWriterThreadManager.
	 * Using simple inheritance to access the needed variables.
	 *
	 * @author Steerpike
	 *
	 */
	private class DataWriterThreadManagerTestClass extends DataWriterThreadManager{

		public DataWriterThreadManagerTestClass(Integer aNumberOfWorkers, DataQueue aDataQueue) {
			super(aNumberOfWorkers, aDataQueue);
			// TODO Auto-generated constructor stub
		}
		
		
		/**
		 * Get the list of worker threads from the superclass {@link DataWriterThreadManager}.
		 * 
		 * @return the list of worker Threads.
		 */
		private ArrayList<Thread> getWorkersList(){
			return super.fWorkers;
		}
		
		@After public void tearDown() {
			
		}
		
	}

	

}
