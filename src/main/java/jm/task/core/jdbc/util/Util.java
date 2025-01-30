package jm.task.core.jdbc.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


public class Util {

    private static final String URL = "jdbc:mysql://localhost:3306/db1";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "root";

    private static Connection conn = null;

    private Util() { }

    public static Connection getConnection() {

        try {
            conn = DriverManager.getConnection(URL, USERNAME, PASSWORD);
            System.out.println("We are connected!");

        } catch (SQLException e) {
            System.out.println("there is no connection... Exception!");
        }
        return conn;
    }

    public static void closeConnection() {
        if (conn != null) {
            try {
                conn.close();
                System.out.println("Connection closed.");
            } catch (SQLException e) {
                System.out.println("The connection is not closed.");
            }
        }

    }
}


