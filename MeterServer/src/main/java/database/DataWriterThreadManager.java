package database;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.Socket;
import java.util.ArrayList;

import socketserver.DataQueue;
import socketserver.SmartMeterHandlerWorker;

/**
 * SQLDatabaseThreadManager creates and managers the SQL Database thread workers.

 * @author Michael
 *
 */
public class DataWriterThreadManager {
	
	private DataQueue fDataQueue;
	
	private final Integer fNumberOfWorkers;
	
	private final ArrayList<Thread> fWorkers;
	
	/**
	 * Creates and intialises the correct number of 
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


