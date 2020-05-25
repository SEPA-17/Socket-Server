package socketserver;

import java.sql.*;

//##########################################
// If error: ClassNotFound
// go to, File -> Project Structure -> Modules
// make sure, mysql:mysql-connector-java:8.0.15 is Compile in Scope column


public class ServerToDatabase {

    private static String URL = "jdbc:mysql://localhost:3306/MeterDatabase?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC";
//    If Error occurs: The server time zone value 'AEST' is unrecognized or represents more than one time zone.
//    -> Use above jdbc driver, serverTimezone=UTC is because Mysql server using UTC timezone

    //    You can try this below first if there is no error.
//    private static String URL = "jdbc:mysql://localhost:3306/MeterDatabase";
    private static String USERNAME = "root";
    private static String PASSWORD = "Sepa12345";

    public ServerToDatabase (){

    }

    public ServerToDatabase (String url, String username, String password){
        this.URL = url;
        this.USERNAME = username;
        this.PASSWORD = password;
    }

    public Connection connect() throws SQLException {
        Connection connectionToDatabase = DriverManager.getConnection(this.URL, this.USERNAME, ServerToDatabase.PASSWORD);
        return connectionToDatabase;
    }
}
