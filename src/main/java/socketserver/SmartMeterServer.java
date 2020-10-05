package socketserver;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.Semaphore;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/***
 * 
 * @author Michael Reeb 03/05/2020
 * 
 * This class is the main class of the SmartMeterServer. It contains socket listening service, which will listen for connections from the smart meters.
 * It then creates a Client Handler thread that will start to pull the data from the Smart Meter, which then will put it into the Queue.
 * Then it pulls the data from the smart meter, and queues it ready to send to the SQLserver.

 */
public class SmartMeterServer {

	private final ServerSocket fServerSocket;
	private final Semaphore fCounterSemaphore;
	private final Logger fLogger;
	private final DataQueue fSmartMeterDataQueue;

	/**
	 * Constructor for the SmartMeterServer. 
	 * @param aListenPortNumber The port number that will be listen on for incomming connections
	 * @param aMaxNumberOfConnections THe maximum number of connections that the server will hold before it refuses more connections
	 * @param aSmartMeterDataQueue 
	 * @param aDataQueueManager The DataQueueManager that the incoming data will be sent to
	 * @throws SmartMeterServerException 
	 */
	public SmartMeterServer(Integer aListenPortNumber, Integer aMaxNumberOfConnections, DataQueue aSmartMeterDataQueue) throws SmartMeterServerException {
		//attempt to create a new server. if this fails, then throw a new exception so that the Server never starts.
		try {
			fServerSocket = new ServerSocket(aListenPortNumber);
			fCounterSemaphore = new Semaphore(aMaxNumberOfConnections);
			fLogger = LoggerFactory.getLogger(SmartMeterServer.class);
			fSmartMeterDataQueue= aSmartMeterDataQueue;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new SmartMeterServerException("Error Intialising ServerSocket" + e.getStackTrace());
		}
		
	}
	
	
	
	
	/**
	 * Start the Server Socket program.  This kicks off the Server listening process.
	 * @throws IOException 
	 * 
	 */
			
	public void run() throws IOException {
		// no expection block in this try statement should cause the server to exit.
		//Master Loop.
		while(true) {
			Socket lSocket = null;
			try {
				//get a permit, and if a permit is avalible, start a new connection. else this will catch some zzzzzs
				fCounterSemaphore.acquire();
				lSocket = fServerSocket.accept();
				DataInputStream lDataInputStream = new DataInputStream(lSocket.getInputStream());
				DataOutputStream lDataOutputStream = new DataOutputStream(lSocket.getOutputStream());
				//if this line is reached, there is an incoming connection, and we are not yet at max capacity. 
				//log connection.
				StringBuilder sb = new StringBuilder("New connection from: ");
				sb.append(lSocket.getInetAddress());
				sb.append(":");
				sb.append(lSocket.getPort());
				fLogger.info(sb.toString());
				
				//start a new thread for this socket. This thread will release it the sempaphore permit when it has completed.
				Integer lWorkerID = fCounterSemaphore.availablePermits() ;
				Thread t = new SmartMeterHandlerWorker(lWorkerID, lSocket, lDataInputStream, lDataOutputStream,fCounterSemaphore,fSmartMeterDataQueue);
				t.start();
				
				
			
			//TODO handling the various exception seperatly.
		} catch(Exception e) {
			fLogger.error("Error in SmartMeterServer: " + e.getMessage());
			lSocket.close();
			
			
			
		}
		}
		
	}

}
