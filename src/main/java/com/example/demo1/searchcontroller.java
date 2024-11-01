package com.example.demo1;

import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;


public class searchcontroller {


    @FXML
    private Button add;

    @FXML
    private Button addb;

    @FXML
    private AnchorPane an;

    @FXML
    private AnchorPane anch;

    @FXML
    private Button back;

    @FXML
    private Label d;
    @FXML
    private JFXTextField D;
    @FXML
    private JFXTextField datee;

    @FXML
    private Label f;

    @FXML
    private JFXTextField follow;
    @FXML
    private Button addb1;
    @FXML
    private ImageView img1;

    @FXML
    private Label label;

    @FXML
    private Label n;

    @FXML
    private JFXTextField namee;

    @FXML
    private Pane p;

    @FXML
    private Pane pane;

    @FXML
    private Pane paneee;

    @FXML
    private Button search;

    @FXML
    private Button show;

    @FXML
    private Label t;
    @FXML
    private Button ok1;
    @FXML
    private JFXTextField timee;

    @FXML
    private JFXTextField tret;

    @FXML
    private Label trw;

    @FXML
    private Pane two;

    @FXML

    private void onSearchButtonClicked() {
        String enteredDate = namee.getText().trim();;

        // إنشاء اتصال بقاعدة البيانات
        Connection conn = connection.connect();
        if (conn != null) {
            String query = "SELECT patientid, AppointmentTime, FollowUpRequired, TreatmentDetails , AppointmentDate " +
                    "FROM appointments WHERE patientid = ?"; // إزالة "WHERE" الزائدة


            try (PreparedStatement pstmt = conn.prepareStatement(query)) {
                // تعيين التاريخ المدخل في الاستعلام
                pstmt.setInt(1, Integer.parseInt(enteredDate)); // استخدام setInt لتعيين patientid


                // تنفيذ الاستعلام
                ResultSet rs = pstmt.executeQuery();

                // التحقق إذا كانت هناك نتائج
                if (rs.next()) {
                    // استرجاع البيانات وتعبئتها في الحقول
                    namee.setText(rs.getString("patientid"));
                    D.setText(rs.getString("AppointmentDate")); // تعيين تاريخ الموعد في حقل التاريخ

                    timee.setText(rs.getString("AppointmentTime"));
                    follow.setText(rs.getBoolean("FollowUpRequired") ? "Yes" : "No");
                    tret.setText(rs.getString("TreatmentDetails"));
                } else {
                    // مسح الحقول في حالة عدم العثور على نتائج
                    namee.clear();
                    D.clear(); // مسح حقل التاريخ إذا لم يتم العثور على نتائج

                    timee.clear();
                    follow.clear();
                    tret.clear();
                    System.out.println("No appointment found for the entered date.");
                }
            } catch (SQLException e) {
                System.out.println("Error executing the query: " + e.getMessage());
            } catch (NumberFormatException e) {
                System.out.println("Invalid patient ID format: " + e.getMessage());
            }
                // إغلاق الاتصال
            finally {
                // إغلاق الاتصال
                connection.closeConnection(conn);
            }
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
    private void showAddScreen(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("Add new Appoitment.fxml"));
        Parent addRoot = loader.load();
        Stage stage = new Stage();
        stage.setScene(new Scene(addRoot));
        stage.setTitle("Add Appointment");
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
    private void updateAppointmentData() {
        String firstName = namee.getText().trim(); // قراءة الاسم الأول من الحقل
        String query = "UPDATE appointments SET appointmentdate='" + D.getText() + "', " +
                "appointmenttime='" + timee.getText() + "', " +
                "followuprequired='" + follow.getText() + "', " +
                "treatmentdetails='" + tret.getText() + "' " +
                "WHERE patientid='" + firstName + "'"; // استخدام patientid بدلاً من first_name
        try (Connection conn = DriverManager.getConnection("jdbc:postgresql://localhost:5432/postgres", "postgres", "0000");
             Statement stmt = conn.createStatement()) {

            int rowsUpdated = stmt.executeUpdate(query);
            if (rowsUpdated > 0) {
                // إظهار تنبيه بالنجاح عند تحديث الموعد بنجاح
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Success");
                alert.setHeaderText(null);
                alert.setContentText("Appointment information updated successfully!");
                alert.showAndWait();
            } else {
                // إظهار تنبيه بعدم العثور على الموعد
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Warning");
                alert.setHeaderText(null);
                alert.setContentText("No appointment found with the provided ID.");
                alert.showAndWait();
            }
        } catch (SQLException e) {
            e.printStackTrace();
            // إظهار تنبيه في حالة حدوث خطأ
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Update Failed");
            alert.setContentText("An error occurred while updating appointment information.");
            alert.showAndWait();
        }
    }


}



