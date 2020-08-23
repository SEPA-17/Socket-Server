package streamReaders;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.ArrayList;

import socketserver.SmartMeterDataMap;

public abstract  class StreamReader {
	
	protected final DataInputStream fInputStream;
	protected final DataOutputStream fOutputStream;
	protected final ArrayList<SmartMeterDataMap> fSmartMeterData;
	
	protected StreamReader(DataInputStream aDataInputStream, DataOutputStream aOutputStream, ArrayList<SmartMeterDataMap> aSmartMeterData) {
		fInputStream = aDataInputStream;
		fOutputStream = aOutputStream;
		fSmartMeterData = aSmartMeterData;
	}
	
	
	/*
	 * Parses the data in fInputStream into fSmartMeterData. Returns true when it has completed.
	 */
	public abstract Boolean parse() throws IOException;
	
	

}
