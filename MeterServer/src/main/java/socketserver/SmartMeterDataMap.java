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
	
	public String toString() {
		StringBuilder sb = new StringBuilder();
		for(SmartMeterDataEnum key : fDataMap.keySet()) {
			sb.append(key.toString());
			sb.append(":");
			
			if(fDataMap.get(key) == null){
				sb.append("<NULL>");
			} else sb.append(fDataMap.get(key).toString());
			sb.append(" ");
		}
		
		
		
		return sb.toString();
		
	}
	
	

}
