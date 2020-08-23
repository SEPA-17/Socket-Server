package database;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import socketserver.SmartMeterDataEnum;
import socketserver.SmartMeterDataMap;

/**
 * Writes data output to file
 * @author Michael
 *
 */
//TODO implment this from DataWriter superclass.
public class DataWriterToFile {
	Integer fWorkerID;
	/**
	 * Writes data to file
	 * @param aWorkerID
	 */
	public DataWriterToFile(Integer aWorkerID) {
		fWorkerID = aWorkerID;
	
	}
	
	/**
	 * Write to data to file;
	 * @param aDataToPush
	 */
	public void write(ArrayList<SmartMeterDataMap> aDataToPush) {
		String lFilePath = System.getProperty("user.dir") + "/logs/dataWriterWorkerNo" + fWorkerID.toString() +".log";
		
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
		
			
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	

}
