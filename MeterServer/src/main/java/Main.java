

import socketserver.SmartMeterServer;
import socketserver.SmartMeterServerException;

import java.io.IOException;

/**
 * To run this application, simply run the commands: java socketserver <listening ip address> <socket>
 * Will probably change this to load from a configuartion file.
 * 
 * 
 */


public class Main {
	
	
	public static void main(String[] args) {
	//Starts the whole #!. Change IP address here
	//might change this to load from a configuration file
		
		//String ipAddress = args[1];
		String socket = "1234";// args[0];
		
			try {
				SmartMeterServer s = new SmartMeterServer(socket);
				s.start();
			} catch (SmartMeterServerException | IOException e) {
		
				e.printStackTrace();
			}
			
			
			
			
		
	
	}
}
