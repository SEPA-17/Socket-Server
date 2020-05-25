package socketserver;

import java.io.FileWriter;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class SQLDataWriter {
	ArrayList<SmartMeterDataMap> fSmartMeterData;

	static String sqlFindMeter = "SELECT MeterId FROM Meter WHERE MeterId = ?";
	static String sqlMeter = "INSERT INTO Meter (MeterId) VALUES (?)";
	static String sqlMeterData = "INSERT INTO MeterData (MeterId, ReadAt, KWH, KW, KVA, KVAr, Ph1i, Ph2i, Ph3i, Ph1v, Ph2v, Ph3v, PF) VALUES (?, STR_TO_DATE(?, '%m/%d/%y %H:%i:%s'), ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, 1.0)";


	public SQLDataWriter(ArrayList<SmartMeterDataMap> aSmartMeterData) {
		// TODO Auto-generated constructor stub
		this.fSmartMeterData = aSmartMeterData;
	}

	public void run() throws SQLException {
		//do the part of connecting to the SQLDatabase.
		//For now, just write to a file.

		ServerToDatabase connectionDatabase = new ServerToDatabase();
		Connection connection = null;
		try {
			connection = connectionDatabase.connect();
			System.out.println("Successful Connection to Database");
		}catch (SQLException e) {
			System.out.println("Error Connect to Database: " + e);
		}

		PreparedStatement prepSqlFindMeter = connection.prepareStatement(sqlFindMeter);
		PreparedStatement prepSqlMeter = connection.prepareStatement(sqlMeter);
		PreparedStatement prepSqlMeterData = connection.prepareStatement(sqlMeterData);


		for (SmartMeterDataMap smdm : fSmartMeterData) {
			int meterID = Integer.parseInt(smdm.getValueAt(SmartMeterDataEnum.METER_ID));
			prepSqlFindMeter.setInt(1, meterID);
			ResultSet rs = prepSqlFindMeter.executeQuery();
			if (rs.next() == false) {
				prepSqlMeter.setInt(1, meterID);
				prepSqlMeter.executeUpdate();
			}
			int a =1;
			for (SmartMeterDataEnum s : SmartMeterDataEnum.values()) {

				if (smdm.getValueAt(s) != null) {
					if (s == SmartMeterDataEnum.METER_ID){
						System.out.println("MeterId: " + a +" - "+ smdm.getValueAt(s));
						prepSqlMeterData.setInt(a++, Integer.parseInt(smdm.getValueAt(s)));

					}else  if (s == SmartMeterDataEnum.DATE_TIME) {
						System.out.println("DateTime: " + a +" - "+ smdm.getValueAt(s));
						prepSqlMeterData.setString(a++, smdm.getValueAt(s));
					}else {
						System.out.println("Others: " + a +" - "+ smdm.getValueAt(s));
						prepSqlMeterData.setDouble(a++, Double.parseDouble(smdm.getValueAt(s)));

					}
				}

			}
			prepSqlMeterData.executeUpdate();
		}


		connection.close();







	}

}
