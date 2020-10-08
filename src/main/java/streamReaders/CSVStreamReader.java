package streamReaders;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

import socketserver.SmartMeterDataEnum;
import socketserver.SmartMeterDataMap;

public class CSVStreamReader extends StreamReader {

	protected CSVStreamReader(DataInputStream aDataInputStream, DataOutputStream aOutputStream, ArrayList<SmartMeterDataMap> aSmartMeterData, Integer aWorkerID) {
		super(aDataInputStream, aOutputStream, aSmartMeterData,aWorkerID);
		// TODO Auto-generated constructor stub
	}

	@Override
	public Boolean parse() throws IOException {
		
	
		
		
		BufferedReader  br = new BufferedReader(new InputStreamReader(this.fInputStream));
		String lTempString = null;
		//read the next line of text in. br.readLine returns null if there is no more data to read.
		while((lTempString = br.readLine()) != null) {
			SmartMeterDataMap lTempDataMap = new SmartMeterDataMap();
			//Data should be read in as in this order:
			//MeterID, Datetime, kWH, EltricPowerTotal, TotalKVA, TotalKVW, ph1_I, ph2_I, ph3_I, ph1_V, ph2_V, Ph_V3, PF
			//now lTempString is a single line from the CSV file ie: 
			//98801009, 11/24/17 11:31,8017.00,0,8.118,-8.14,12.761,12.424,12.256,217.8,217.3,217.9,1
			//so we split them on the "," character 
			String[] lValues = lTempString.split(",");
			
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
			lTempDataMap.setValueAt(SmartMeterDataEnum.POWER_FACTOR, lValues[12]);
			
			//add line reading to the list of readings
			this.fSmartMeterData.add(lTempDataMap);
			
		}
		
		
		
		// TODO Auto-generated method stub
		return true;
	
    }
}
