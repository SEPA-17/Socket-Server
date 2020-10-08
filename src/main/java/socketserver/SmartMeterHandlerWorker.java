package socketserver;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Semaphore;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import streamReaders.StreamReader;
import streamReaders.LineStreamReader;
import streamReaders.SmartMeterStreamReader;
/**
 * SmartMeterHandlerWorker will handle a single Smart Meter connection. It will parse in the input stream, send data to the Smart Meter (if needed),
 * and then place the extracted data into the DataQueue.
 * 
 * @author Michael Reeb
 *
 */
public class SmartMeterHandlerWorker extends Thread {
	private final  Socket fSocket;
	private final DataInputStream fDataInputStream;
	private final DataOutputStream fDataOutputStream;
	
	private final ArrayList<SmartMeterDataMap> fSmartMeterData;
	private final Semaphore fCounterSemaphore;
	private final  DataQueue fSmartMeterDataQueue;
	private final Integer fWorkerID;
	
	private final Logger fLogger;
	
	//private final DataReader fDataReader;
	
	/**
	 * Create a new Smart Meter Handler Worker thread. there may be multiple instances of this class running conncurrently to handle in
	 * input from the SmartMeter connections.
	 * @param aSocket The Socket connection of this Worker
	 * @param aDataInputStream The input Datastream that this will be reading data in from
	 * @param aDataOutputStream The output stream used to message back to the Smartmeter server of status, etc
	 * @param aSemaphore This is used to signal the Master/Boss thread that this has completed.
	 */
	public SmartMeterHandlerWorker(Integer aWorkerID, Socket aSocket, DataInputStream aDataInputStream, DataOutputStream aDataOutputStream, Semaphore aCounterSemaphore, DataQueue aSmartMeterDataQueue) {
		//assign the member varibles to the arguments
		fSmartMeterData = new ArrayList<SmartMeterDataMap>();
		fSocket = aSocket;
		fDataOutputStream = aDataOutputStream;
		fDataInputStream = aDataInputStream;	
		fCounterSemaphore = aCounterSemaphore;
		fWorkerID = aWorkerID;
		
		//the data queue
		fSmartMeterDataQueue = aSmartMeterDataQueue;
	
		//set up the logger
		fLogger = LoggerFactory.getLogger(SmartMeterHandlerWorker.class);
	}
	
	@Override
	/**
	 * Overrides threads Run statement. This is the process that the ClientHandler must perform to extract the Smart Meter Data from the 
	 * socket.
	 */
	public void run() {
			try {
		 		
				//Line Stream reader is a test reader.
				StreamReader lReader = new LineStreamReader(fDataInputStream, fDataOutputStream, fSmartMeterData, fWorkerID);
				//StreamReader lReader = new SmartMeterStreamReader(fDataInputStream, fDataOutputStream, fSmartMeterData, fWorkerID);
				
				//if we are able to successfully parse the data, then add the ArrayList to the queue to be pushed to the SQL.
				//database
				//the stored data is now located in the ArrayList fSmartMeterData.
		 		
				if(! lReader.parse()) {
					//parsing was unsuccessful.
					/**
					 * TODO might move this message system into the Reader class
					*/
					StringBuilder sb = new StringBuilder("Logger was unable to read from device ");
					sb.append(fSocket.getInetAddress());
					sb.append(":");
					sb.append(fSocket.getPort());
					fLogger.info(sb.toString());
					return;
				}
				
				
				//we have succefully parsed the data input from the smart meter. Now we need to push this to the DataQueue for the 
				//SQLDatabase worker threads to push to the 
				
				//TODO might move this message system into the Reader class
				StringBuilder sb = new StringBuilder(this.getClass().getName());
				sb.append(".");
				sb.append(lReader.getClass().getName());
				sb.append(" successfully read ");
				sb.append(fSmartMeterData.size());
				sb.append(" lines of data from device");
				sb.append(fSocket.getInetAddress());
				sb.append(":");
				sb.append(fSocket.getPort());
				fLogger.info(sb.toString());
				
				
	
				
				//push the data out into the Queue, if there is data.
				if(!fSmartMeterData.isEmpty()) fSmartMeterDataQueue.put(fSmartMeterData);
				
			} catch (Exception e) {
				//depending on the exceptions thrown, we have to do different things.
				//for the most part, we cannot let exceptions leave this handler, else risk crashing the whole server.
				//which is not good
				//System.out.println(e.getMessage());
				fLogger.error(e.getMessage());
				//e.printStackTrace();
				
				
				}
			
			finally {
				//release the Semaphore so new connections can be created.
				fLogger.info("Worker" + fWorkerID + "has completed reading from  input.");
				fCounterSemaphore.release();
				}

		
	}
	
	
	
	

}
