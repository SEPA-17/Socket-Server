package database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import socketserver.ServerToDatabase;
import socketserver.SmartMeterDataEnum;
import socketserver.SmartMeterDataMap;

import org.apache.commons.configuration2.ex.ConfigurationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Writes data output to Database.
 * 
 * @author Michael
 *
 */
public class DataWriterToDatabase {
	Integer fWorkerID;
	private Logger fLogger;

	public DataWriterToDatabase(Integer aWorkerID) {
		fLogger = LoggerFactory.getLogger(DataWriterToDatabase.class);
	}

	private static String SQL_FIND_METER = "SELECT MeterId FROM Meter WHERE MeterId = ?";
	private static String SQL_METER = "INSERT INTO Meter (MeterId) VALUES (?)";
	private static String SQL_METER_DATA = "INSERT INTO MeterData (MeterId, ReadAt, KWH, KW, KVA, KVAr, Ph1i, Ph2i, Ph3i, Ph1v, Ph2v, Ph3v, PF) VALUES (?, STR_TO_DATE(?, '%d/%m/%y %H:%i:%s'), ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, 1.0)";

	/**
	 * Write the data to the Database
	 * 
	 * @param aData
	 * @throws SQLException
	 * @throws ConfigurationException
	 */
	public void write(ArrayList<SmartMeterDataMap> aDataToPush) throws SQLException, ConfigurationException {
		/**********************************
		 * All original code below here
		 *************************************/

		ServerToDatabase connectionDatabase = new ServerToDatabase();
		Connection connection = null;
		try {
			connection = connectionDatabase.connect();
			fLogger.debug("Successful connection to the Database");
		} catch (SQLException e) {
			fLogger.error("Unsuccessful connection to the Database");
			System.out.println(e);
		}

		PreparedStatement prepSqlFindMeter = connection.prepareStatement(SQL_FIND_METER);
		PreparedStatement prepSqlMeter = connection.prepareStatement(SQL_METER);
		PreparedStatement prepSqlMeterData = connection.prepareStatement(SQL_METER_DATA);

		for (SmartMeterDataMap smdm : aDataToPush) {
			int meterID = Integer.parseInt(smdm.getValueAt(SmartMeterDataEnum.METER_ID));
			prepSqlFindMeter.setInt(1, meterID);
			ResultSet rs = prepSqlFindMeter.executeQuery();
			if (rs.next() == false) {
				prepSqlMeter.setInt(1, meterID);
				prepSqlMeter.executeUpdate();
			}
			int a = 1;
			for (SmartMeterDataEnum s : SmartMeterDataEnum.values()) {

				if (smdm.getValueAt(s) != null) {
					if (s == SmartMeterDataEnum.METER_ID) {
						fLogger.info("MeterId: " + a + " - " + smdm.getValueAt(s));
						prepSqlMeterData.setInt(a++, Integer.parseInt(smdm.getValueAt(s)));

					} else if (s == SmartMeterDataEnum.DATE_TIME) {
						fLogger.info("DateTime: " + a + " - " + smdm.getValueAt(s));
						prepSqlMeterData.setString(a++, smdm.getValueAt(s));
					} else {
						fLogger.info("Others: " + a + " - " + smdm.getValueAt(s));
						prepSqlMeterData.setDouble(a++, Double.parseDouble(smdm.getValueAt(s)));
					}
				}
			}
			prepSqlMeterData.executeUpdate();
		}
		connection.close();
	}
}
