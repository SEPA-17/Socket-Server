package database;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import socketserver.SmartMeterDataEnum;
import socketserver.SmartMeterDataMap;

/**
 * Tests the functionality of the DataWriterToFile.
 * @author Steerpike
 *
 */
public class DataWriterToFileTests {
	DataWriterToFile fWriter;
	Integer fWorkerID = 999;
	String fFilePath = System.getProperty("user.dir") + "/data/dataWriterWorkerNo" + fWorkerID.toString() +".data";

	/**
	 * 
	 */
	@Before public void initialise() {
		setup();
	}
	
	/**
	 * Sets up for testing. will delete an existing file if it is found it exists.
	 */
	public void setup() {
		fWriter = new DataWriterToFile(fWorkerID);
		File tempFile = new File(fFilePath);
		if(tempFile.exists()) {
			tempFile.delete();
		}
	}
	
	/**
	 * Generates data and makes the worker write the data.
	 */
	public void workerWrite() {
		ArrayList<SmartMeterDataMap> lData = new ArrayList<>();
		SmartMeterDataMap lMap = new SmartMeterDataMap();
		
		for(SmartMeterDataEnum key : SmartMeterDataEnum.values()) {
			lMap.setValueAt(key, String.format("TestingDataWriter %s",key.name()));
		}
		
		lData.add(lMap);
		fWriter.write(lData);
		
	}
	
	/**
	 * Tests the writing functionality
	 */
	@Test public void testWriteToFile() {
		try {
		setup();
		workerWrite();
		//data now should exist.
		
		File tempFile = new File(fFilePath);
		assertTrue(tempFile.exists());
		
		
		 BufferedReader br = new BufferedReader(new FileReader(tempFile));
		 String lTemp = br.readLine();
		 
		//test that the data has been correctly written.
		 for(SmartMeterDataEnum key : SmartMeterDataEnum.values()) {
			 assertTrue(lTemp.contains(String.format("TestingDataWriter %s",key.name())));
			}
		 
		} catch(Exception e) {
			fail("Exception Thrown: " + e.toString());
			
		}
		
		
	}
	
	/**
	 * Deletes the temp files when complete.
	 */
	@After public void pulldown(){
		File tempFile = new File(fFilePath);
		if(tempFile.exists()) {
			tempFile.delete();
		}
	}
	
	
}
