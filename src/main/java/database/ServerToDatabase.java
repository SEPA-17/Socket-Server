package database;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;
import java.io.File;

import org.apache.commons.configuration2.Configuration;
import org.apache.commons.configuration2.FileBasedConfiguration;
import org.apache.commons.configuration2.builder.fluent.Configurations;
import org.apache.commons.configuration2.ex.ConfigurationException;
import org.apache.commons.beanutils.PropertyUtils;

//##########################################
// If error: ClassNotFound
// go to, File -> Project Structure -> Modules
// make sure, mysql:mysql-connector-java:8.0.15 is Compile in Scope column

/**
 * 
 * @author Heng and Michael S
 *
 */
public class ServerToDatabase {

    // private static String URL =
    // "jdbc:mysql://127.0.0.1:8889/MeterDatabase?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC";
    // If Error occurs: The server time zone value 'AEST' is unrecognized or
    // represents more than one time zone.
    // -> Use above jdbc driver, serverTimezone=UTC is because Mysql server using
    // UTC timezone

    // You can try this below first if there is no error.

    private String URL;
    private String USERNAME;
    private String PASSWORD;

    // private static String URL = "jdbc:mysql://127.0.0.1:8889/meterdb-dev";
    // private static String USERNAME = "root";
    // private static String PASSWORD = "root";

    private Configurations fConfigs;
    private Configuration fConfig;
    private final Logger fLogger = LoggerFactory.getLogger(ServerToDatabase.class);

    public ServerToDatabase() throws ConfigurationException {
        fConfigs = new Configurations();
        fConfig = fConfigs.properties(new File("serversocket.properties"));

        Integer PORT = fConfig.getInteger("DBPORT", 3306);
        String DBNAME = fConfig.getString("DBNAME", "db");

        this.URL = "jdbc:mysql://" + fConfig.getString("DBHOSTNAME", "localhost") + ":" + PORT + "/" + DBNAME;
        this.USERNAME = fConfig.getString("DBUSER", "root");
        this.PASSWORD = fConfig.getString("DBPASS", "root");
    }

    public Connection connect() throws SQLException {
        Connection connectionToDatabase = DriverManager.getConnection(this.URL, this.USERNAME, this.PASSWORD);
        return connectionToDatabase;
    }
}