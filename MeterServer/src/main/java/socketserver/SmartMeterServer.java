package socketserver;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/***
 * 
 * @author Michael Reeb 03/05/2020
 * 
 * This class is the main class of the SmartMeterServer. It contains a listening function, which will listen for connections from the smart meters.
 * Then it pulls the data from the smart meter, and queues it ready to send to the SQL server.
 * This Class relies heavliy on Exceptions to ensure that the program does not crash under errors.
 * 
 * Tommorrow will take us away, f
 */
public class SmartMeterServer {
	/**
	 * 
	 */
	private static final long serialVersionUID = 3341121932816792313L;
	private ServerSocket fServerSocket = null;

	public SmartMeterServer(String aSocket) throws SmartMeterServerException {
		// TODO Auto-generated constructor stub
		try {
			Integer lSocket = Integer.parseInt(aSocket);
			fServerSocket = new ServerSocket(lSocket);
			
			//catch the integer failing, and throw new exception up the heirachy
		} catch(NumberFormatException | IOException  e) {
			throw new SmartMeterServerException("Error Intialising ServerSocket: " + e.getStackTrace());
			
		}
		
		
	}
	
	
	
	/**
	 * @throws IOException 
	 * 
	 */
			
	public void start() throws IOException {
		// no expection block in this try statement should cause the server to exit.
		while(true) {
			Socket lSocket = null;
			try {
				lSocket = fServerSocket.accept();
				//Log something about the Client connecting
				
				//get the input stream. this may be put into the Client Handler.
				DataInputStream lDataInputStream = new DataInputStream(lSocket.getInputStream());
				DataOutputStream lDataOutputStream = new DataOutputStream(lSocket.getOutputStream());
				
				//start a new thread for this socke
				Thread t = new ClientHandler(lSocket, lDataInputStream, lDataOutputStream);
				t.start();
				
				
			
			
		} catch(Exception e) {
			//log exceptions to the LOGGER. close the bad socket.
			lSocket.close();
			
			
			
		}
		}
		
	}

}
