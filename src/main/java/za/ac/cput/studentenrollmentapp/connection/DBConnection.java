
package za.ac.cput.studentenrollmentapp.connection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author ATHINI NGQUKE
 */
public class DBConnection {
    public static Connection derbyConnection() throws SQLException{
        
        String dURL =  "jdbc:derby://localhost:1527/StudentEnrollmentDB";
        String username = "Administrator";
        String password = "password";
        
        Connection con = DriverManager.getConnection(dURL, username, password);
        return con;
    }
}
