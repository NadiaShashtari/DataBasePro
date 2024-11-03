package com.example.demo1;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
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
import java.io.IOException;

public class addapoitmentcontroller {

    @FXML
    private ChoiceBox<Integer> chois;

    @FXML
    private Label d;

    @FXML
    private Button add;
    @FXML
    private Button ok1;

    @FXML
    private AnchorPane an;
    @FXML
    private Button back;
    @FXML
    private Button show;
    @FXML
    private Button search;

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

    public void initialize() {
        loadDentistIds(); // تحميل أرقام dentists عند بدء الشاشة
    }

    private void loadDentistIds() {
        String query = "SELECT dentist_id FROM dentists";
        try (Connection connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/postgres", "postgres", "0000");
             PreparedStatement preparedStatement = connection.prepareStatement(query);
             ResultSet resultSet = preparedStatement.executeQuery()) {

            while (resultSet.next()) {
                int dentistId = resultSet.getInt("dentist_id");
                chois.getItems().add(dentistId); // إضافة dentist_id إلى ChoiceBox
            }

        } catch (SQLException e) {
            showAlert("Error", "Error loading dentist IDs: " + e.getMessage(), AlertType.ERROR);
        }
    }

    @FXML
    void handleAddActionButton() {
        String firstName = namee.getText().trim();
        String middleName = namee1.getText().trim();
        String lastName = namee2.getText().trim();
        String appointmentDateStr = datee.getText().trim();
        String appointmentTimeStr = timee.getText().trim();
        String followupStr = follow.getText().trim();
        String treatmentPlan = tret.getText().trim();

        if (chois.getValue() == null || firstName.isEmpty() || lastName.isEmpty() || appointmentDateStr.isEmpty() ||
                appointmentTimeStr.isEmpty() || followupStr.isEmpty() || treatmentPlan.isEmpty()) {
            showAlert("Warning", "All fields must be filled.", AlertType.WARNING);
            return;
        }

        java.sql.Date appointmentDate = null;
        try {
            Date utilDate = new SimpleDateFormat("yyyy-MM-dd").parse(appointmentDateStr);
            appointmentDate = new java.sql.Date(utilDate.getTime());
        } catch (ParseException e) {
            showAlert("Error", "Error parsing the appointment date.", AlertType.ERROR);
            return;
        }

        int selectedDentistId = chois.getValue();

        // التحقق من عدم وجود موعد بنفس الساعة عند نفس الدكتور
        String checkAppointmentQuery = "SELECT * FROM appointments WHERE dentist_id = ? AND appointmentdate = ? AND appointmenttime = ?";
        try (Connection connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/postgres", "postgres", "0000");
             PreparedStatement checkStatement = connection.prepareStatement(checkAppointmentQuery)) {

            checkStatement.setInt(1, selectedDentistId);
            checkStatement.setDate(2, appointmentDate);
            checkStatement.setTime(3, java.sql.Time.valueOf(appointmentTimeStr));

            ResultSet resultSet = checkStatement.executeQuery();
            if (resultSet.next()) {
                showAlert("Warning", "Appointment already exists for this dentist at the same time.", AlertType.WARNING);
                return;
            }
        } catch (SQLException e) {
            showAlert("Error", "Error checking appointment: " + e.getMessage(), AlertType.ERROR);
            return;
        }

        // إضافة الموعد بعد التأكد من عدم التعارض
        String insertQuery = "INSERT INTO appointments (patientid, dentist_id, appointmentdate, appointmenttime, treatmentdetails, followuprequired, first_name, middle_name, last_name) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
        int patientId = getPatientId(firstName, middleName, lastName);

        if (patientId == -1) {
            showAlert("Warning", "Patient not found.", AlertType.WARNING);
            return;
        }

        try (Connection connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/postgres", "postgres", "0000");
             PreparedStatement preparedStatement = connection.prepareStatement(insertQuery)) {

            preparedStatement.setInt(1, patientId);
            preparedStatement.setInt(2, selectedDentistId);
            preparedStatement.setDate(3, appointmentDate);
            preparedStatement.setTime(4, java.sql.Time.valueOf(appointmentTimeStr));
            preparedStatement.setString(5, treatmentPlan);
            preparedStatement.setBoolean(6, Boolean.parseBoolean(followupStr));
            preparedStatement.setString(7, firstName);
            preparedStatement.setString(8, middleName);
            preparedStatement.setString(9, lastName);

            int rowsAffected = preparedStatement.executeUpdate();
            if (rowsAffected > 0) {
                showAlert("Success", "Appointment added successfully.", AlertType.INFORMATION);
            } else {
                showAlert("Error", "Error adding appointment.", AlertType.ERROR);
            }
        } catch (SQLException e) {
            showAlert("Error", "Error adding appointment: " + e.getMessage(), AlertType.ERROR);
        }
    }

    private int getPatientId(String firstName, String middleName, String lastName) {
        String query = "SELECT patientid FROM patients WHERE first_name = ? AND middle_name = ? AND last_name = ?";
        try (Connection connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/postgres", "postgres", "0000");
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setString(1, firstName);
            preparedStatement.setString(2, middleName);
            preparedStatement.setString(3, lastName);

            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return resultSet.getInt("patientid");
            }
        } catch (SQLException e) {
            showAlert("Error", "Error fetching patient ID: " + e.getMessage(), AlertType.ERROR);
        }
        return -1;
    }

    private void showAlert(String title, String message, AlertType alertType) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
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
            ((Stage) search.getScene().getWindow()).close();
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
        ((Stage) show.getScene().getWindow()).close();
    }

    @FXML
    void handleLogButton(ActionEvent event) {
        try {
            // Load the FXML file for the signin screen
            Parent root = FXMLLoader.load(getClass().getResource("sign in.fxml"));
            Stage stage = (Stage) ok1.getScene().getWindow(); // الحصول على النافذة الحالية
            stage.setScene(new Scene(root)); // تغيير المشهد
            stage.show(); // عرض الشاشة الجديدة
            ((Stage) ok1.getScene().getWindow()).close();
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
            stage.show();// عرض المشهد الجديد
            ((Stage) back.getScene().getWindow()).close();
        } catch (Exception e) {

            e.printStackTrace(); // التعامل مع الأخطاء
        }}
}
