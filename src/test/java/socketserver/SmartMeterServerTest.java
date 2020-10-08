package socketserver;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class SmartMeterServerTest {
	SmartMeterServer fSmartMeterServer;
	Runnable fRunnable;
	Thread fThread;
	public static int TEST_PORT = 1234;

	
	/**
	 * Create a new Server before the testing begins
	 */
	@Before public void initalise() {
		setup();
	}
	
	/**
	 * Create  a new SmartMeterServer instance for testing purposes
	 */
	public void setup() {
		
		try {
		fSmartMeterServer = new SmartMeterServer(TEST_PORT,5,new DataQueue(5));
		} catch(Exception e) {
			fail("Exception Occured: " + e.toString());
		}
		
		 fRunnable = ( ) -> {
			System.out.println("Starting Server Test");
			try {
			fSmartMeterServer.run();
		} catch (IOException e) {
			fail("Exception Occured: " + e.toString());

		}};
		
		 fThread = new Thread(fRunnable);
	}
	
	/**
	 * Ensure that we can connect to the smart meter instance, ie it accepts incoming connections.
	 */
	@Test public void testConnection() {
		fThread.start();
		assertTrue(fThread.isAlive());
		try {
			Socket lTestSocket = new Socket(InetAddress.getByName("127.0.0.1"),TEST_PORT);
			assertTrue(lTestSocket.isConnected());
			lTestSocket.close();
			assertTrue(lTestSocket.isClosed());
		} catch (Exception e) {
			fail("Exception Occured: " + e.toString());
		}
		
		
	}
	
	
	/** Stop the Test server.
	 * 
	 */
	@SuppressWarnings("deprecation")
	@After public void tearDownServer() {
		fThread.stop();
		
	}
	
	
}
