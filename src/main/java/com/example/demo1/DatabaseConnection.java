
package com.example.demo1;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {

    private static final String URL = "jdbc:postgresql://localhost:5432/postgres";
    private static final String USER = "nadia";
    private static final String PASSWORD = "123456";

    public Connection connect() {
        Connection conn = null;
        try {

            Class.forName("org.postgresql.Driver");

            conn = DriverManager.getConnection("jdbc:postgresql://localhost:5432/postgres", "nadia", "123456");
            System.out.println("Connected to the database.");
        } catch (SQLException | ClassNotFoundException e) {
            System.out.println("Connection failed.");
            e.printStackTrace();
        }
        return conn;
    }

}
