package socketserver;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
/**
 * ClientHandler will handle a single Smart Meter connection. It will parse in the input stream, send data to the Smart Meter (if needed),
 * and then enque the data to the process to send put it into the database.
 * 
 * @author Michael Reeb
 *
 */
public class ClientHandler extends Thread {
	private final  Socket fSocket;
	private final DataInputStream fDataInputStream;
	private final DataOutputStream fDataOutputStream;
	
	private final ArrayList<SmartMeterDataMap> fSmartMeterData;
	//private final DataReader fDataReader;
	
	
	public ClientHandler(Socket aSocket, DataInputStream aDataInputStream, DataOutputStream aDataOutputStream) {
		
		this.fSmartMeterData = new ArrayList<SmartMeterDataMap>();
		this.fSocket = aSocket;
		this.fDataOutputStream = aDataOutputStream;
		this.fDataInputStream = aDataInputStream;	
		//this.fDataReader = new CSVDataReader(aDataInputStream,fSmartMeterData);
	}
	
	@Override
	/**
	 * Overrides threads Run statement. This is the process that the ClientHandler must perform to extract the Smart Meter Data from the 
	 * socket.
	 */
	public void run() {
			try {
		 		//DataReader lReader = new CSVDataReader(this.fDataInputStream,this.fSmartMeterData);
				
				DataReader lReader = new SMRStreamReader(this.fDataInputStream,this.fSmartMeterData);
				//if we are able to successfully parse the data, then move on.
				//the stored data is now located in the handler fSmartMeterData field.
		 		//it is now safe to close the input stream
				lReader.parse();
				
				for(SmartMeterDataMap s : fSmartMeterData) {
					System.out.println(s.toString());
				}
				
				
				this.fDataOutputStream.writeBytes("Java Server Received " + fSmartMeterData.size() +" lines of Data!");
				this.fDataOutputStream.flush();
				this.fDataOutputStream.close();
				this.fSocket.close();
				
				//push the data out into the Database.
				
			} catch (Exception e) {
				//depending on the exceptions thrown, we have to do different things.
				//for the most part, we cannot let exceptions leave this handler, else risk crashing the whole server.
				//which is not good
				//System.out.println(e.getMessage());
				e.printStackTrace();
				
				
				}
			
		// Now we want the output data. here we will try write data out.
			
			try {
				//SQLDataWriter lWriter = new SQLDataWriter(this.fSmartMeterData);
				//lWriter.run();
			}
			catch (Exception e) {
				//depending on the exceptions thrown, we have to do different things.
				//for the most part, we cannot let exceptions leave this handler, else risk crashing the whole server.
				//also, as this exception is in the writing phase, we may want to not throw away the collected data.
				e.printStackTrace();
				
				
				}

		
	}
	
	
	
	

}
