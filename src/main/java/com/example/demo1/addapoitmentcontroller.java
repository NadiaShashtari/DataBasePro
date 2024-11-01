package com.example.demo1;

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

public class addapoitmentcontroller {

    @FXML
    private Button add;
    @FXML
    private Button ok1;

    @FXML
    private AnchorPane an;
    @FXML
    private Button back;

    @FXML
    private JFXTextField datee;

    @FXML
    private JFXTextField follow;

    @FXML
    private JFXTextField namee; // first name
    @FXML
    private JFXTextField namee1; // middle name
    @FXML
    private JFXTextField namee2; // last name

    @FXML
    private JFXTextField timee;

    @FXML
    private JFXTextField tret; // treatment plan

    @FXML
    void handleAddActionButton() {
        String firstName = namee.getText().trim();
        String middleName = namee1.getText().trim();
        String lastName = namee2.getText().trim(); // استخدام namee2 كاسم العائلة
        String appointmentDateStr = datee.getText().trim();
        String appointmentTimeStr = timee.getText().trim();
        String followupStr = follow.getText().trim();
        String treatmentPlan = tret.getText().trim();

        // التحقق من الحقول الفارغة
        if (firstName.isEmpty() || lastName.isEmpty() || appointmentDateStr.isEmpty() || appointmentTimeStr.isEmpty() || followupStr.isEmpty() || treatmentPlan.isEmpty()) {
            System.out.println("All fields must be filled.");
            return;
        }

        java.sql.Date appointmentDate = null;

        // تحويل النص إلى java.sql.Date
        try {
            java.util.Date utilDate = new SimpleDateFormat("yyyy-MM-dd").parse(appointmentDateStr);
            appointmentDate = new java.sql.Date(utilDate.getTime());
        } catch (ParseException e) {
            e.printStackTrace();
            System.out.println("Error parsing the appointment date.");
            return;
        }

        // استعلام الإدراج في جدول المواعيد
        String getPatientIdQuery = "SELECT patientid FROM patients WHERE first_name = ? AND middle_name = ? AND last_name = ?";
        int patientId = -1;
        try (Connection connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/postgres", "postgres", "0000");
             PreparedStatement preparedStatement = connection.prepareStatement(getPatientIdQuery)) {
            preparedStatement.setString(1, firstName);
            preparedStatement.setString(2, middleName);
            preparedStatement.setString(3, lastName);
            // تعيين القيم المطلوبة
           /* preparedStatement.setInt(1, 1); // استبدل هذا بالقيمة الفعلية لـ PatientID
            preparedStatement.setInt(2, 1); // استبدل هذا بالقيمة الفعلية لـ dentist_id
            preparedStatement.setDate(3, appointmentDate);
            preparedStatement.setTime(4, java.sql.Time.valueOf(appointmentTimeStr)); // تحويل الوقت
            preparedStatement.setString(5, treatmentPlan);
            preparedStatement.setBoolean(6, Boolean.parseBoolean(followupStr)); // تحويل المتابعة إلى Boolean
            preparedStatement.setString(7, firstName);
            preparedStatement.setString(8, middleName);
            preparedStatement.setString(9, lastName);

            int rowsAffected = preparedStatement.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Appointment added successfully.");
            } else {
                System.out.println("Error adding appointment.");
            }*/
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                patientId = resultSet.getInt("patientid");
            } else {
                System.out.println("Patient not found.");
                return; // إنهاء العملية إذا لم يتم العثور على المريض
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Error adding appointment: " + e.getMessage());
        }
        String insertQuery = "INSERT INTO appointments (patientid, dentist_id, appointmentdate, appointmenttime, treatmentdetails, followuprequired, first_name, middle_name, last_name) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/postgres", "postgres", "0000");
             PreparedStatement preparedStatement = connection.prepareStatement(insertQuery)) {

            // تعيين القيم المطلوبة
            preparedStatement.setInt(1, patientId); // استخدام patientId المستخرج
            preparedStatement.setInt(2, 1); // استبدل هذا بالقيمة الفعلية لـ dentist_id
            preparedStatement.setDate(3, appointmentDate);
            preparedStatement.setTime(4, java.sql.Time.valueOf(appointmentTimeStr)); // تحويل الوقت
            preparedStatement.setString(5, treatmentPlan);
            preparedStatement.setBoolean(6, Boolean.parseBoolean(followupStr)); // تحويل المتابعة إلى Boolean
            preparedStatement.setString(7, firstName);
            preparedStatement.setString(8, middleName);
            preparedStatement.setString(9, lastName);

            int rowsAffected = preparedStatement.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Appointment added successfully.");
            } else {
                System.out.println("Error adding appointment.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Error adding appointment: " + e.getMessage());
        }
    }

    @FXML
    private void showSearchScreen() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("SEARCH.fxml"));
            Parent root = fxmlLoader.load();
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setTitle("Search Screen");
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    @FXML
    private void showShowScreen(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("show all.fxml"));
        Parent showRoot = loader.load();
        Stage stage = new Stage();
        stage.setScene(new Scene(showRoot));
        stage.setTitle("Show Screen");
        stage.show();
    }

    @FXML
    void handleLogButton(ActionEvent event) {
        try {
            // Load the FXML file for the signin screen
            Parent root = FXMLLoader.load(getClass().getResource("sign in.fxml"));
            Stage stage = (Stage) ok1.getScene().getWindow(); // الحصول على النافذة الحالية
            stage.setScene(new Scene(root)); // تغيير المشهد
            stage.show(); // عرض الشاشة الجديدة
        } catch (Exception e) {
            e.printStackTrace(); // طباعة أي خطأ في حالة حدوثه
        }
    }
    @FXML
    void handbackButton(ActionEvent event) {


        try {
            // Load the FXML file for the half screen
            Parent root = FXMLLoader.load(getClass().getResource("appo.fxml"));
            Stage stage = (Stage) back.getScene().getWindow(); // الحصول على نافذة الزر الحالي
            stage.setScene(new Scene(root)); // تغيير المشهد
            stage.show(); // عرض المشهد الجديد
        } catch (Exception e) {
            e.printStackTrace(); // التعامل مع الأخطاء
        }}
}
