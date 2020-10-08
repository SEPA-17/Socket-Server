package database;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import socketserver.SmartMeterDataEnum;
import socketserver.SmartMeterDataMap;

/**
 * Writes Smartmeter data and places it in a file.
 * @author Michael
 *
 */

public class DataWriterToFile {
	Integer fWorkerID;
	private final Logger fLogger;
	/**
	 * Writes data to file
	 * @param aWorkerID the identifier for this worker.
	 */
	public DataWriterToFile(Integer aWorkerID) {
		fWorkerID = aWorkerID;
		fLogger = LoggerFactory.getLogger(DataWriterToFile.class);
	
	}
	
	/**
	 * Writes to data to file. 
	 * @param aDataToPush An array List of Data lines read in from a SmartMeter.
	 */
	public void write(ArrayList<SmartMeterDataMap> aDataToPush) {
		String lFilePath = System.getProperty("user.dir") + "/data/dataWriterWorkerNo" + fWorkerID.toString() +".data";
		fLogger.debug(String.format("Thread %d writiing data to file: %s", fWorkerID, lFilePath));
		//autoclose these resources on error
		try (
				FileWriter fw = new FileWriter(lFilePath, true);
				BufferedWriter bw = new BufferedWriter(fw);
			    PrintWriter lOutput = new PrintWriter(bw)
				
				)
		{
			//print the values to the File
			for(SmartMeterDataMap lLine: aDataToPush) {
				StringBuilder sb = new StringBuilder();
				
				//build the line by iteratoring through the enum map
				for(SmartMeterDataEnum lEnum : SmartMeterDataEnum.values()) {
					sb.append(lEnum.toString());
					sb.append(":");
					
					//if there is no data in this position, add the <NULL> string.
					if(lLine.getValueAt(lEnum) == null) {
						sb.append("<NULL>");
						
					} else {
						sb.append(lLine.getValueAt(lEnum));
					}
					sb.append(" , ");
				
				}
				lOutput.println(sb.toString());
			}
			
		} catch (IOException e) {
		
			
			fLogger.error(e.toString());
		}
		
	}
	

}
