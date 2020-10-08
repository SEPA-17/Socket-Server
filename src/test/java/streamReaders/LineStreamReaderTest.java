package streamReaders;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;

import socketserver.SmartMeterDataEnum;
import socketserver.SmartMeterDataMap;

public class LineStreamReaderTest {
	InputStream fInputStream;
	OutputStream fOutputStream;
	DataInputStream fDataInputStream;
	DataOutputStream fDataOutputStream;
	
	
	ArrayList<SmartMeterDataMap> fTestList;
	LineStreamReader fReader;
	
	Runnable fRunnable;
	Thread fThread;
	
	
	@Before public void intialise() {
		
		
		
	}
	
	@Test public void testDataSend() {
			String lTestString = 
					"98801006,11/24/17 11:31,8017.00,0,8.118,-8.14,12.761,12.424,12.256,217.8,217.3,217.9,1";
			fInputStream = new ByteArrayInputStream((lTestString + "\n!").getBytes());
			
		
			fOutputStream = new ByteArrayOutputStream();
			
			
		
			try {
				
				fDataInputStream = new DataInputStream(fInputStream);
				fDataOutputStream = new DataOutputStream(fOutputStream);
				
				
				fTestList = new ArrayList<SmartMeterDataMap>();
				fReader  = new LineStreamReader(fDataInputStream, fDataOutputStream, fTestList,420);
				
				//list should be empty
				assertTrue(fTestList.size() == 0);
				fReader.parse();
				
				//list now should have a value
				assertTrue(fTestList.size() == 1);
				
				SmartMeterDataMap lValues = fTestList.get(0);
				String[] lSplitString = lTestString.split(",");
				SmartMeterDataEnum[] lKeys = SmartMeterDataEnum.values();
				
				//lengths should be the same of the above values
				assertTrue(lSplitString.length == lKeys.length);
				
				//Tests
				assertTrue(lValues.getValueAt(SmartMeterDataEnum.METER_ID).equals(lSplitString[0]));
				assertTrue(lValues.getValueAt(SmartMeterDataEnum.DATE_TIME).equals(lSplitString[1]));
				assertTrue(lValues.getValueAt(SmartMeterDataEnum.KILO_WATT_HOURS).equals(lSplitString[2]));
				assertTrue(lValues.getValueAt(SmartMeterDataEnum.KILO_WATT_TOTAL).equals(lSplitString[3]));
				assertTrue(lValues.getValueAt(SmartMeterDataEnum.KILO_WATT_AMPS_TOTAL).equals(lSplitString[4]));
				assertTrue(lValues.getValueAt(SmartMeterDataEnum.KILO_VOLT_AMP_REACTIVE_TOTAL).equals(lSplitString[5]));
				assertTrue(lValues.getValueAt(SmartMeterDataEnum.PHASE_1_CURRENT).equals(lSplitString[6]));
				assertTrue(lValues.getValueAt(SmartMeterDataEnum.PHASE_2_CURRENT).equals(lSplitString[7]));
				assertTrue(lValues.getValueAt(SmartMeterDataEnum.PHASE_3_CURRENT).equals(lSplitString[8]));
				assertTrue(lValues.getValueAt(SmartMeterDataEnum.PHASE_1_VOLTAGE).equals(lSplitString[9]));
				assertTrue(lValues.getValueAt(SmartMeterDataEnum.PHASE_2_VOLTAGE).equals(lSplitString[10]));
				assertTrue(lValues.getValueAt(SmartMeterDataEnum.PHASE_3_VOLTAGE).equals(lSplitString[11]));
				assertTrue(lValues.getValueAt(SmartMeterDataEnum.POWER_FACTOR).equals(lSplitString[12]));
				
				//check that data is being written back.
				assertFalse(fOutputStream.toString().isEmpty());
				
				
			} catch(Exception e) {
				fail("Exception occured: " + e.toString());
			}
		
		
	}
}
