package Controller;

import java.sql.*;

public class DBConnector {
    public static Connection getConnection() {
        try {
            return DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/mydb",
                    "root",
                    "root");
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }
}
