package socketserver;

import java.util.EnumMap;

/**
 * This is class that will hold the parsed data. It is basically a wrapper class for Enum Map.
 * THis is  used to make handling the data received from the SmartMeter simple and clear.
 * 
 * 
 * @author Steerpike
 *
 */
public class SmartMeterDataMap {
	private EnumMap<SmartMeterDataEnum,String> fDataMap;
	
	public SmartMeterDataMap() {
		fDataMap = new EnumMap<SmartMeterDataEnum,String>(SmartMeterDataEnum.class);
	}
	
	public String getValueAt(SmartMeterDataEnum aKey) {
		return fDataMap.get(aKey);
		
	}
	
	public void setValueAt(SmartMeterDataEnum aKey , String aValue) {
		fDataMap.put(aKey, aValue);
	}
	
	

}
