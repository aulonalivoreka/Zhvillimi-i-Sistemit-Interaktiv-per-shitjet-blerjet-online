package services;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnector {
    private static String URL = "";
    private static String USER = "";
    private static String PASSWORD = "";
    private static Connection connection = null;

    public static Connection getConnection(){
        if(connection == null){
            try {
                connection = DriverManager.getConnection(
                        URL, USER, PASSWORD
                );
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
        return connection;
    }
}