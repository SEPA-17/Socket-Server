package socketserver;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.net.URLClassLoader;

import javax.naming.ConfigurationException;

import org.apache.commons.configuration2.Configuration;
import org.apache.commons.configuration2.FileBasedConfiguration;
import org.apache.commons.configuration2.builder.fluent.Configurations;
import org.apache.commons.beanutils.PropertyUtils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import database.DataWriterThreadManager;

/**
 * The point of entry for the Program. Commons COnfiguration package is used for
 * loading configuration settings from file
 * 
 * Logback is used for logging messages and information
 * 
 * JUnit is used for creating and running unit tests
 * 
 * javax.mail is used for creating and sending emails (if needed)
 * 
 * @author Michael R
 *
 */

public class Main {
	// private static final Logger fLogger ;

	public static void main(String args[]) {
		ClassLoader cl = ClassLoader.getSystemClassLoader();

		// set up the systems properties
		Configurations lConfigs = new Configurations();

		try {
			Configuration lConfig = lConfigs.properties(new File("serversocket.properties"));
			System.out.println(lConfig.getString("project.message"));

			// DataQueue setup
			DataQueue lQueue = new DataQueue(lConfig.getInteger("dataqueue.length", 40));

			// DataWriter manager setup
			DataWriterThreadManager lDWTM = new DataWriterThreadManager(
					lConfig.getInteger("datawriter.noofworkerthreads", 5), lQueue);

			// SmartMeter Server startup
			SmartMeterServer lServer = new SmartMeterServer(lConfig.getInteger("smartmeterserver.portnumber", 1234),
					lConfig.getInteger("smartmeterserver.maxconnections", 20), lQueue);
			// kick off the processes
			lDWTM.run();

			// main thread chills out in this process for now
			lServer.run();

			// s.start();
		} catch (org.apache.commons.configuration2.ex.ConfigurationException cex) {
			System.out.println(cex.toString());

		} catch (SmartMeterServerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	// create a new SocketServerInstance,
	// create a new DataQueue instance
	// create a new S

}
