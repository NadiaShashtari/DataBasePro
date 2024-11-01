package com.example.demo1;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class connection {
    public static final String URL = "jdbc:postgresql://localhost:5432/postgres";
    public static final String USER = "postgres";
    public static final String PASSWORD = "0000";

    //متاكدة هيك بشو ؟
    // شكلو لا لانو بالفويس حاكيين منرفعهم عالمين واحنا ما رفعنا اشي عالمين
    public static Connection connect() {
        try {
            Class.forName("org.postgresql.Driver"); // تأكد من وجود السائق
            return DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (SQLException e) {
            System.out.println("Error connecting to the database: " + e.getMessage());
        } catch (ClassNotFoundException e) {
            System.out.println("PostgreSQL JDBC Driver not found. Include it in your library path.");
        }
        return null; // إرجاع null في حالة حدوث خطأ
    }

    public static void closeConnection(Connection conn) {
        if (conn != null) {
            try (conn) {
                // إغلاق الاتصال تلقائياً في نهاية العبارة
            } catch (SQLException e) {
                System.out.println("Error closing the connection: " + e.getMessage());
            }
        }
    }
}
