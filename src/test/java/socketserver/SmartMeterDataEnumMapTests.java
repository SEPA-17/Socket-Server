package socketserver;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import  org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

import socketserver.SmartMeterDataEnum;

/**
 * Test case of the SmartMeterDataMap. 
 * @author Steerpike
 *
 */
public class SmartMeterDataEnumMapTests {
	SmartMeterDataMap fTestEnumMap = new SmartMeterDataMap();
	
	
	/**
	 * Initialize the data in the class for testing
	 */
	@Before public void intialiseDataForTesting() {
		this.setDataForTesting();
	}
	 /**
	 * Common method used to set the data of the SmartMeterDataMap
	 */
	public void setDataForTesting(){
		for(SmartMeterDataEnum key: SmartMeterDataEnum.values()) {
			fTestEnumMap.setValueAt(key, String.format("Test %s",key.toString()));
		}
		
				
	}
	
	/**
	 * Tests that we are able to correctly get and set values
	 */
	@Test public void testGetAndSetValue() {
		setDataForTesting();
		
		String newID = "Blurst Of Times";
		//inital test to make sure that the values are the same
		for(SmartMeterDataEnum key: SmartMeterDataEnum.values()) {
			assertTrue(fTestEnumMap.getValueAt(key).equals(String.format("Test %s",key.toString())));
		}
		
		
		
		//change the value and assert that the change was succesful.
		for(SmartMeterDataEnum key: SmartMeterDataEnum.values()) {
			fTestEnumMap.setValueAt(key,String.format("FAIL%s",key.toString()));
		}
		//make sure that change changed the values
		for(SmartMeterDataEnum key: SmartMeterDataEnum.values()) {
			assertFalse(fTestEnumMap.getValueAt(key).equals(String.format("Test %s",key.toString())));
		}
		
		//make sure that change was correct.
		for(SmartMeterDataEnum key: SmartMeterDataEnum.values()) {
			assertTrue(fTestEnumMap.getValueAt(key).equals(String.format("FAIL%s",key.toString())));
		}
		
		
		
		
		
	}
	
	/**
	 * Tests the ensure we produce a string from the toString functions
	 */
	@Test public void testToString() {
		setDataForTesting();
		//test to make sure we are returning a string
		assertFalse(fTestEnumMap.toString().isEmpty());
		
	}
	
	/**
	 * Tests to ensure that when we clear the EnumMap, that all values for each element ar nulled.
	 */
	@Test public void testClear() {
		setDataForTesting();
		
		fTestEnumMap.clear();
		for(SmartMeterDataEnum key: SmartMeterDataEnum.values()) {
			assertTrue(fTestEnumMap.getValueAt(key) == null);
		}
		
		
	}
	
}
