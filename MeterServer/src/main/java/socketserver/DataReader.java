package socketserver;

import java.io.DataInputStream;
import java.io.IOException;
import java.util.ArrayList;

public abstract  class DataReader {
	
	protected final DataInputStream fInputStream;
	protected final ArrayList<SmartMeterDataMap> fSmartMeterData;
	
	protected DataReader(DataInputStream aDataInputStream, ArrayList<SmartMeterDataMap> aSmartMeterData) {
		this.fInputStream = aDataInputStream;
		this.fSmartMeterData = aSmartMeterData;
	}
	
	
	/*
	 * Parses the data in fInputStream into fSmartMeterData. Returns true when it has completed.
	 */
	public abstract Boolean parse() throws IOException;
	
	

}
