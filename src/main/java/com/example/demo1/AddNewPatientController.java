package com.example.demo1;

import com.jfoenix.controls.JFXTextField;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import javafx.event.ActionEvent;
import java.io.IOException;


public class AddNewPatientController {

    @FXML
    private Button ADDActionButt;

    @FXML
    private Button AddButt;

    @FXML
    private JFXTextField BDTxtF;

    @FXML
    private Button BackButt;

    @FXML
    private JFXTextField EmailTxtF;

    @FXML
    private JFXTextField FirstNameTxtF;

    @FXML
    private JFXTextField LastNameTxtF;

    @FXML
    private JFXTextField MiddleNameTxtF;

    @FXML
    private JFXTextField PhoneTxtF;

    @FXML
    private Button SearchButt;

    @FXML
    private Button ShowButt;

    @FXML
    private Button LogOutButton;


    @FXML
    private JFXTextField TreatmentPlanTxtF;


    @FXML
    void handleAddActionButton() {
        String firstName = FirstNameTxtF.getText().trim();
        String middleName = MiddleNameTxtF.getText().trim();
        String lastName = LastNameTxtF.getText().trim();
        String email = EmailTxtF.getText().trim();
        String phone = PhoneTxtF.getText().trim();
        String treatmentPlan = TreatmentPlanTxtF.getText().trim();
        String birthDateStr = BDTxtF.getText().trim();

        // التحقق من الحقول الفارغة
        if (firstName.isEmpty() || lastName.isEmpty() || email.isEmpty() || phone.isEmpty() || treatmentPlan.isEmpty() || birthDateStr.isEmpty()) {
            System.out.println("All fields must be filled.");
            return;
        }

        java.sql.Date birthDate = null;


        try {
            // تحويل النص إلى java.sql.Date
            java.util.Date utilDate = new SimpleDateFormat("yyyy-MM-dd").parse(birthDateStr);
            birthDate = new java.sql.Date(utilDate.getTime());
        } catch (ParseException e) {
            e.printStackTrace();
            System.out.println("Error parsing the birth date.");
            return;
        }
        String insertQuery = "INSERT INTO patients (firstname, middelname, lastname, email, phonenumber, birthday, treatmentplan) VALUES (?, ?, ?, ?, ?, ?, ?)";

        try (Connection connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/postgres", "nadia", "123456");
             PreparedStatement preparedStatement = connection.prepareStatement(insertQuery)) {

            preparedStatement.setString(1, firstName);
            preparedStatement.setString(2, middleName);
            preparedStatement.setString(3, lastName);
            preparedStatement.setString(4, email);
            preparedStatement.setString(5, phone);
            preparedStatement.setDate(6, birthDate);
            preparedStatement.setString(7, treatmentPlan);

            int rowsAffected = preparedStatement.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Patient added successfully.");
            } else {
                System.out.println("Error adding patient.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Error adding patient: " + e.getMessage());
        }
    }

    @FXML
    private void handleBackButtonAction(ActionEvent event) {
        try {
            // تحميل الصفحة السابقة
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/halfPatient.fxml"));
            Parent previousPage = loader.load();

            // الحصول على المشهد الحالي
            Scene currentScene = BackButt.getScene();

            // الحصول على مرحلة العرض
            Stage stage = (Stage) currentScene.getWindow();

            // إعداد المشهد الجديد
            stage.setScene(new Scene(previousPage));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();

        }
    }


    @FXML
    void handleLogOut(ActionEvent event) {
        try {
            // Load the Sign-In screen
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/sign in.fxml"));
            Parent root = loader.load();

            // Create a new stage for the Sign-In screen
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setTitle("Sign In");
            stage.show();

            // Close the current HalfPatient screen
            ((Stage) LogOutButton.getScene().getWindow()).close();

        } catch (Exception e) {
            e.printStackTrace();
            Alert errorAlert = new Alert(Alert.AlertType.ERROR);
            errorAlert.setTitle("Error");
            errorAlert.setHeaderText(null);
            errorAlert.setContentText("Failed to load the Sign-In screen. Please try again.");
            errorAlert.showAndWait();
        }
    }
    @FXML
    void handleShow(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/showAllPatient.fxml"));
            Parent newPage = loader.load();
            Stage stage = new Stage();
            stage.setScene(new Scene(newPage));
            stage.show();
            Stage currentStage = (Stage) ((Button) event.getSource()).getScene().getWindow();
            currentStage.close();

        } catch (IOException e) {
            e.printStackTrace();
            showAlert("Error", "Failed to load the new page.");
        }

    }
    private void showAlert(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }

    @FXML
    private void handleSearchButton(ActionEvent event) {
        // Load the search interface
        try {

            Stage currentStage = (Stage) SearchButt.getScene().getWindow();

            currentStage.close(); // إغلاق الشاشة السابقة

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/SearchByPhone.fxml"));
            Parent root = loader.load();

            // Create a new stage (window) for the search screen
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setTitle("Search Patients");
            stage.show();


        } catch (Exception e) {
            e.printStackTrace();
            Alert errorAlert = new Alert(Alert.AlertType.ERROR);
            errorAlert.setTitle("Error");
            errorAlert.setHeaderText(null);
            errorAlert.setContentText("Failed to load the search screen. Please try again.");
            errorAlert.showAndWait();
        }
    }
}