package com.example.demo1;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class DatabaseConnection {

    private static final String URL = "jdbc:postgresql://localhost:5432/DentistProject";
    private static final String USER = "DentistProject";
    private static final String PASSWORD = "123456";
}