package socketserver;

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

public class ServerToDatabase {

    // If you hit an error on the database connection, ensure the MySQL server is
    // configured with +10:00 UTC time.

    public String URL;
    public String USERNAME;
    public String PASSWORD;

    private Configurations fConfigs;
    private Configuration fConfig;

    public ServerToDatabase() throws ConfigurationException {
        fConfigs = new Configurations();
        fConfig = fConfigs.properties(new File("serversocket.properties"));

        String PORT = fConfig.getString("DBPORT");
        String DBNAME = fConfig.getString("DBNAME");

        this.URL = "jdbc:mysql://" + fConfig.getString("DBURL") + ":" + PORT + "/" + DBNAME;
        this.USERNAME = fConfig.getString("DBUSER");
        this.PASSWORD = fConfig.getString("DBPASS");

    }

    public Connection connect() throws SQLException {
        Connection connectionToDatabase = DriverManager.getConnection(this.URL, this.USERNAME, this.PASSWORD);
        return connectionToDatabase;
    }
}