package socketserver;

import java.io.DataInputStream;
import java.io.IOException;
import java.util.ArrayList;

public class SMRStreamReader extends DataReader{
	
	protected SMRStreamReader(DataInputStream aDataInputStream, ArrayList<SmartMeterDataMap> aSmartMeterData) {
		super(aDataInputStream, aSmartMeterData);
		// TODO Auto-generated constructor stub
	}



	@Override
	public Boolean parse() throws IOException {
		// TODO Auto-generated method stub
		byte[] lInput = new byte[0];
		
		Integer noOfBytes = 0;
		while(( noOfBytes = this.fInputStream.read(lInput)) > 0) {
			
			
		}
		
		
		return null;
	}

}
