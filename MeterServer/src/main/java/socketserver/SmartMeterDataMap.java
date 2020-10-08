package socketserver;

import java.util.EnumMap;

/**
 * This is class that will hold the parsed data. It is a wrapper class for an EnumMap.
 * This is  used to make handling the data received from the SmartMeter simple and clear.
 * 
 * 
 * @author Steerpike
 *
 */
public class SmartMeterDataMap {
	private EnumMap<SmartMeterDataEnum,String> fDataMap;
	
	/**
	 * Create a new instance of the SmartMeterDataMap. This  map is used to store a single row of collected data from the SmartMeter.
	 */
	public SmartMeterDataMap() {
		fDataMap = new EnumMap<SmartMeterDataEnum,String>(SmartMeterDataEnum.class);
		
	}
	
	
	/** Get the value at the given Key
	 * @param aKey the name of the value to get, given by the elements in SmartMeterDataEnum
	 * @return the value at aKey, or NULL if there is no value.
	 */
	public String getValueAt(SmartMeterDataEnum aKey) {
		return fDataMap.get(aKey);
		
	}
	
	/**
	 *  Set the value at a given SmartMeterDataEnum key. If there is an exisiting value, it will be overwritten with the new value.
	 *  
	 * @param aKey The name of the value to map, given by the elements in SmartMeterDataEnum
	 * @param aValue  The new value of the assoisated key given.
	 */
	public void setValueAt(SmartMeterDataEnum aKey , String aValue) {
		fDataMap.put(aKey, aValue);
	}
	
	/**
	 * Get the Data Map as a String
	 * @return The Datamap as a string
	 */
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
	
	/**
	 * Clears all values in the data map. this sets them to NULL.
	 */
	public void clear() {
		fDataMap.clear();
	}
	
	
	
	

}
