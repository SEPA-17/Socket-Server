package streamReaders;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.ArrayList;

import socketserver.SmartMeterDataMap;

public class SmartMeterStreamReader extends StreamReader {

	protected SmartMeterStreamReader(DataInputStream aDataInputStream, DataOutputStream aOutputStream,
			ArrayList<SmartMeterDataMap> aSmartMeterData) {
		super(aDataInputStream, aOutputStream, aSmartMeterData);
	}

	@Override
	public Boolean parse() throws IOException {
		// TODO Auto-generated method stub
		return false;
	}

}
