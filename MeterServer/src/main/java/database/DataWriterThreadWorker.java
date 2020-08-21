package database;

import java.io.FileWriter;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import socketserver.DataQueue;
import socketserver.ServerToDatabase;
import socketserver.SmartMeterDataEnum;
import socketserver.SmartMeterDataMap;
import socketserver.SmartMeterHandlerWorker;

/**
 * SQLDataBaseThreadWoker
 * This class pulls data of the DataQueue, and feeds it into the SQLDatabase.
 * @author Heng, Michael
 *
 */
public class DataWriterThreadWorker extends Thread{
	private DataQueue fDataQueue;
	private Integer fWorkerIdentifier;
	private final Logger fLogger;

	

	/**Create a new SQLDatabaseThreadWorker thread. This is used to pull data off the DataQueue, and push to the SQLDatabase. 
	 * @param aIdentifier The identifier of this worker thread assigned by the manager
	 * @param aDataQueue The DataQueue which will be used to pull data off.
	 */
	public DataWriterThreadWorker(Integer aIdentifier, DataQueue aDataQueue) {
		// TODO Auto-generated constructor stub
		fDataQueue = aDataQueue ;
		fWorkerIdentifier =  aIdentifier;
		//set up the logger
	    fLogger = LoggerFactory.getLogger(DataWriterThreadWorker.class);
	    
	    fLogger.info("DataWriterThreadWorker " + fWorkerIdentifier + " spawned!");
	}

	/**
	 * Kicks of the process. Starts trying to pull data from the incoming queue and starts
	 */
	public void run() {
		fLogger.info("DataWriterThreadWorker " + fWorkerIdentifier + " is now running!");
	
		while(true) {
			//Sleep until there is some data in the queue to grab
			ArrayList<SmartMeterDataMap> lDataToPush;
			try {
				lDataToPush = fDataQueue.take();
				//do something with this chunk of data
				DataWriterToFile lDataWriter = new DataWriterToFile(fWorkerIdentifier);
				
				
				lDataWriter.write(lDataToPush);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
	}

}
