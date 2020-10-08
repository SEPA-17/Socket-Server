package socketserver;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.ArrayList;

import  org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;
/**
 * @author Steerpike
 *
 */

public class DataQueueTests {
	Integer fSize;
	DataQueue fTestQueue ;
	ArrayList<SmartMeterDataMap> fFirst ;
	ArrayList<SmartMeterDataMap> fSecond ;
	ArrayList<SmartMeterDataMap> fThird ;
	

	@Before public void initalise() {
			setup();
	}
	

	/**
	 * Set up the test environment.
	 */
	 public void setup(){
			fSize = 5;
			fTestQueue = new DataQueue(fSize);
			fFirst = new ArrayList<SmartMeterDataMap>();
			fSecond = new ArrayList<SmartMeterDataMap>();
			fThird = new ArrayList<SmartMeterDataMap>();
			
			
			
			try {
				fTestQueue.put(fFirst);
				fTestQueue.put(fSecond);
				fTestQueue.put(fThird);
			} catch (InterruptedException e) {
				fail("Exception Thrown: " + e.toString());
			}
			
		
	}
	
	
	/**
	 * Test all the basic functionality of the DataQueue.
	 */
	@Test public void testPeekPollTake() {
		setup();
		
		//ensure that the queue is 3 
		assertTrue(fTestQueue.size() == 3);
		//test poll, first element off should be fFirst
		ArrayList<SmartMeterDataMap> lTest = fTestQueue.poll();
		assertTrue(lTest.equals(fFirst));
		
		//make sure the size has decreased
		//second element should be fSecond
		assertTrue(fTestQueue.size() == 2);
		lTest = fTestQueue.peek();
		assertTrue(lTest == fSecond);
		
		//test take, the third element should be fThird
		try {
			lTest = fTestQueue.take();
		} catch (InterruptedException e) {
			fail(e.toString());
		}
		assertTrue(lTest == fSecond);
		assertTrue(fTestQueue.size() == 1);
		
		//ensure that the queue is empty, and that we are pointing to the third element
		lTest = fTestQueue.poll();
		assertTrue(fTestQueue.size() == 0);
		assertTrue(lTest == fThird);
		
		//ensure that the queue is empty, and that we are pointing to null
		lTest = fTestQueue.poll();
		assertTrue(fTestQueue.size() == 0);
		assertTrue(lTest == null);
		
		
		
		
	}
	
	

}
