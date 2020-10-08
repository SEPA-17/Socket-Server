package database;

import java.io.FileWriter;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import org.apache.commons.configuration2.ex.ConfigurationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import socketserver.DataQueue;
import socketserver.SmartMeterDataEnum;
import socketserver.SmartMeterDataMap;
import socketserver.SmartMeterHandlerWorker;

/**
 * This class pulls data of the DataQueue, and feeds it to a DataWriter, where
 * the DataWriter decideds how to process this data into the SQLDatabase.
 * 
 * @author Heng, Michael S, Michael R.
 *
 */
public class DataWriterThreadWorker extends Thread {
	private DataQueue fDataQueue;
	protected Integer fWorkerIdentifier;
	private final Logger fLogger;

	/**
	 * Create a new DataWriterThreadWorker thread. This is used to pull data off the
	 * DataQueue, and push to the SQLDatabase.
	 * 
	 * @param aIdentifier The identifier of this worker thread assigned by the
	 *                    manager
	 * @param aDataQueue  The DataQueue which will be used to pull data off.
	 */
	public DataWriterThreadWorker(Integer aIdentifier, DataQueue aDataQueue) {
		// TODO Auto-generated constructor stub
		fDataQueue = aDataQueue;
		fWorkerIdentifier = aIdentifier;
		// set up the logger
		fLogger = LoggerFactory.getLogger(DataWriterThreadWorker.class);

		fLogger.info("DataWriterThreadWorker " + fWorkerIdentifier + " spawned!");
	}

	/**
	 * Kicks of the process. Starts trying to pull data from the incoming queue and
	 * starts.
	 * 
	 */
	public void run() {
		fLogger.info("DataWriterThreadWorker " + fWorkerIdentifier + " is now running!");

		while (true) {
			// Sleep until there is some data in the queue to grab
			ArrayList<SmartMeterDataMap> lDataToPush;
			try {
				lDataToPush = fDataQueue.take();
				// do something with this chunk of data
				DataWriterToDatabase lDataWriter = new DataWriterToDatabase(fWorkerIdentifier);

				try {
					lDataWriter.write(lDataToPush);
				} catch (SQLException e) {
					fLogger.error(e.toString());
					e.printStackTrace();
				} catch (ConfigurationException e) {
					fLogger.error(e.toString());
					e.printStackTrace();
				}
			} catch (InterruptedException e) {
				fLogger.error(e.toString());
				e.printStackTrace();
			}

		}
	}

}
