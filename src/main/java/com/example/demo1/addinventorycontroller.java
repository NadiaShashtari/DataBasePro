package com.example.demo1;

import com.jfoenix.controls.JFXTextField;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

import javafx.scene.control.Alert.AlertType;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import java.sql.DriverManager;

public class addinventorycontroller {
    @FXML
    private Button search;

    @FXML
    private Button show;
    @FXML
    private Button log;
    @FXML
    private Button add;

    @FXML
    private Button back;

    @FXML
    private JFXTextField cost;

    @FXML
    private JFXTextField exp;

    @FXML
    private JFXTextField neme;

    @FXML
    private JFXTextField quntity;

    @FXML
    private JFXTextField sname;

    @FXML
    private JFXTextField total;

    @FXML
    void handleAddActionButton() {
        String itemName = neme.getText().trim(); // حقل اسم العنصر
        String quantityStr = quntity.getText().trim(); // حقل الكمية
        String costPerItemStr = cost.getText().trim(); // حقل تكلفة العنصر
        String totalCostStr = total.getText().trim(); // حقل التكلفة الإجمالية
        String supplierName = sname.getText().trim(); // حقل اسم المورد
        String expirationDateStr = exp.getText().trim(); // حقل تاريخ انتهاء الصلاحية

        // التحقق من الحقول الفارغة
        if (itemName.isEmpty() || quantityStr.isEmpty() || costPerItemStr.isEmpty() || totalCostStr.isEmpty() || supplierName.isEmpty() || expirationDateStr.isEmpty()) {
            System.out.println("All fields must be filled.");
            return;
        }

        // التحقق من أن الكمية والتكاليف هي أرقام صحيحة
        int quantity;
        double costPerItem, totalCost;
        try {
            quantity = Integer.parseInt(quantityStr);
            costPerItem = Double.parseDouble(costPerItemStr);
            totalCost = Double.parseDouble(totalCostStr);
        } catch (NumberFormatException e) {
            System.out.println("Quantity and costs must be valid numbers.");
            return;
        }

        // استعلام الإدراج في جدول inventory
        String insertQuery = "INSERT INTO inventory (itemname, quantity, costperitem, totalcost, supplier_name, expirationdate) VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/postgres", "postgres", "0000");
             PreparedStatement preparedStatement = connection.prepareStatement(insertQuery)) {

            // تعيين القيم المطلوبة
            preparedStatement.setString(1, itemName);
            preparedStatement.setInt(2, quantity);
            preparedStatement.setDouble(3, costPerItem);
            preparedStatement.setDouble(4, totalCost);
            preparedStatement.setString(5, supplierName);
            preparedStatement.setDate(6, java.sql.Date.valueOf(expirationDateStr)); // تحويل تاريخ انتهاء الصلاحية إلى java.sql.Date

            int rowsAffected = preparedStatement.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Item added to inventory successfully.");
            } else {
                System.out.println("Error adding item to inventory.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Error adding item to inventory: " + e.getMessage());
        } catch (IllegalArgumentException e) {
            System.out.println("Error parsing expiration date: " + e.getMessage());
        }
    }
    @FXML
    void handleLogButton(ActionEvent event) {
        try {
            // Load the FXML file for the signin screen
            Parent root = FXMLLoader.load(getClass().getResource("sign in.fxml"));
            Stage stage = (Stage) log.getScene().getWindow(); // الحصول على النافذة الحالية
            stage.setScene(new Scene(root)); // تغيير المشهد
            stage.show(); // عرض الشاشة الجديدة
            ((Stage) log.getScene().getWindow()).close();
        } catch (Exception e) {
            e.printStackTrace(); // طباعة أي خطأ في حالة حدوثه
        }
    }
    @FXML
    private void showSearchScreen() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("search inventory.fxml"));
            Parent root = fxmlLoader.load();
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setTitle("Search Screen");
            stage.show();
            ((Stage) search.getScene().getWindow()).close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    @FXML
    private void showShowScreen(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("show inventory.fxml"));
        Parent showRoot = loader.load();
        Stage stage = new Stage();
        stage.setScene(new Scene(showRoot));
        stage.setTitle("Show Screen");
        stage.show();
        ((Stage) show.getScene().getWindow()).close();
    }
    @FXML
    void handbackButton(ActionEvent event) {


        try {
            // Load the FXML file for the half screen
            Parent root = FXMLLoader.load(getClass().getResource("half.fxml"));
            Stage stage = (Stage) back.getScene().getWindow(); // الحصول على نافذة الزر الحالي
            stage.setScene(new Scene(root)); // تغيير المشهد
            stage.show(); // عرض المشهد الجديد
            ((Stage) back.getScene().getWindow()).close();
        } catch (Exception e) {
            e.printStackTrace(); // التعامل مع الأخطاء
        }}


}
