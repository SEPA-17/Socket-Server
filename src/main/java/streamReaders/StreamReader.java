package streamReaders;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.ArrayList;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import socketserver.SmartMeterDataMap;

public abstract  class StreamReader {
	
	protected final DataInputStream fInputStream;
	protected final DataOutputStream fOutputStream;
	protected final ArrayList<SmartMeterDataMap> fSmartMeterData;
	protected  Logger fLogger;
	private final Integer fWorkerID;
	
	public StreamReader(DataInputStream aDataInputStream, DataOutputStream aOutputStream, ArrayList<SmartMeterDataMap> aSmartMeterData, Integer aWorkerID) {
		fInputStream = aDataInputStream;
		fOutputStream = aOutputStream;
		fSmartMeterData = aSmartMeterData;
		fLogger = LoggerFactory.getLogger(StreamReader.class);
		fWorkerID = aWorkerID;
	}
	
	
	/*
	 * Parses the data in fInputStream into fSmartMeterData. Returns true when it has completed.
	 */
	public abstract Boolean parse() throws IOException;
	
	

}
