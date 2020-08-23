package streamReaders;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.ArrayList;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import database.DataWriterThreadWorker;
import socketserver.SmartMeterDataEnum;
import socketserver.SmartMeterDataMap;

public class LineStreamReader extends StreamReader{
	private final Logger fLogger;
	private final Integer fWorkerID;
	/*
	 * Smartmeter Reader Stream Reader, allows the conntection of on going datastreams from a python connection.,
	 */
	/**
	 * @param aDataInputStream
	 * @param aSmartMeterData
	 */
	public LineStreamReader(DataInputStream aDataInputStream, DataOutputStream aDataOutputStream, ArrayList<SmartMeterDataMap> aSmartMeterData,Integer aWorkerID) {
		
		super(aDataInputStream, aDataOutputStream, aSmartMeterData);
		// TODO Auto-generated constructor stub
		fLogger = LoggerFactory.getLogger(LineStreamReader.class);
		fWorkerID = aWorkerID;
	}



	@Override
	public Boolean parse() throws IOException {
		fLogger.debug("LineStreamReader Started");
		
		// TODO Auto-generated method stub
		byte[] lBuffer = new byte[5];
		//LinkedList<byte> lSentenceBuffer = new LinkedList<byte>();
		
		Integer noOfBytes = 0;
		
		boolean finsihedFlag = false;
		StringBuilder sb = new StringBuilder();
		while(!finsihedFlag) {
			
			fInputStream.read(lBuffer);
			for(byte b: lBuffer) {
				if((char)b == '\n' || (char)b == '\r') {
					
					fLogger.debug("Processed a new line: " + sb.toString());
				
					String[] lValues = sb.toString().split(",");
					
				
					SmartMeterDataMap lTempDataMap = new SmartMeterDataMap();
					//add  each corresponding value to the SmartMeterDataEnum
					lTempDataMap.setValueAt(SmartMeterDataEnum.METER_ID, lValues[0]);
					lTempDataMap.setValueAt(SmartMeterDataEnum.DATE_TIME, lValues[1]);
					lTempDataMap.setValueAt(SmartMeterDataEnum.KILO_WATT_HOURS, lValues[2]);
					lTempDataMap.setValueAt(SmartMeterDataEnum.KILO_WATT_TOTAL, lValues[3]);
					lTempDataMap.setValueAt(SmartMeterDataEnum.KILO_WATT_AMPS_TOTAL, lValues[4]);
					lTempDataMap.setValueAt(SmartMeterDataEnum.KILO_VOLT_AMP_REACTIVE_TOTAL, lValues[5]);
					lTempDataMap.setValueAt(SmartMeterDataEnum.PHASE_1_CURRENT, lValues[6]);
					lTempDataMap.setValueAt(SmartMeterDataEnum.PHASE_2_CURRENT, lValues[7]);
					lTempDataMap.setValueAt(SmartMeterDataEnum.PHASE_3_CURRENT, lValues[8]);
					lTempDataMap.setValueAt(SmartMeterDataEnum.PHASE_1_VOLTAGE, lValues[9]);
					lTempDataMap.setValueAt(SmartMeterDataEnum.PHASE_2_VOLTAGE, lValues[10]);
					lTempDataMap.setValueAt(SmartMeterDataEnum.PHASE_3_VOLTAGE, lValues[11]);
					lTempDataMap.setValueAt(SmartMeterDataEnum.PHASE_3_VOLTAGE, lValues[12]);
					
					//add line reading to the list of readings
					this.fSmartMeterData.add(lTempDataMap);
					sb.setLength(0);
					
					
				}
				else if(b == 33) { //Exclamation mark received
					finsihedFlag = true;
					break;
				} else {
					sb.append((char)b);
					System.out.print((char)b);
				}
				
				
				
			}
			
			
		}
		//print the final message
		super.fOutputStream.writeBytes("Java Server Worker " + fWorkerID + " sent " + fSmartMeterData.size() +" lines of Data!");
	    super.fOutputStream.flush();
	    super.fOutputStream.close();
		System.out.println("Finished!");
		
		
		return true;
	}

}
