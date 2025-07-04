package Controller;

import java.sql.*;

public class DBConnector {
    public static Connection getConnection() {
        try {
            return DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/mydb",
                "root",
                "DLSU1234!"
            );
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }
}
