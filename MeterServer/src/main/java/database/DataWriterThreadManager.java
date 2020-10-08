package database;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.Socket;
import java.util.ArrayList;

import socketserver.DataQueue;
import socketserver.SmartMeterHandlerWorker;

/**
 * DatawriterThreadManager creates and managers worker threads that deal with pulling the data
 * off the queue, and doing some action with them. Ideally this will be connected with a DataWriterToDatabase, where
 * the data is pushed to an SQL database.

 * @author Michael
 *
 */
public class DataWriterThreadManager {
	
	protected DataQueue fDataQueue;
	
	protected final Integer fNumberOfWorkers;
	
	protected final ArrayList<Thread> fWorkers;
	
	/**
	 * Creates and initializes the correct number of worker threads, which will pull data from the Queue to put into the database
	 * 
	 * @param aNumberOfWorkers
	 * @param aDataQueue
	 */
	public DataWriterThreadManager(Integer aNumberOfWorkers, DataQueue aDataQueue) {
		fNumberOfWorkers = aNumberOfWorkers;
		fDataQueue = aDataQueue;
		
		fWorkers = new ArrayList<Thread>(fNumberOfWorkers);
		for(int i = 0; i < fNumberOfWorkers; i++) {
			fWorkers.add( new DataWriterThreadWorker(i,fDataQueue)) ;
		}
	
	}

	public void run() {
		for(Thread t : fWorkers) {
			t.start();
		}
		
	}
		
}


