package com.example.demo1;

import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Alert;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class LabSearch {

    @FXML
    private Button BackButt;

    @FXML
    private JFXTextField CityTxtF;

    @FXML
    private JFXTextField DueTxtF;

    @FXML
    private JFXTextField IDTxtF;

    @FXML
    private Button LogOutButton;

    @FXML
    private JFXTextField NameTxtF;

    @FXML
    private JFXTextField PaidTxtF;

    @FXML
    private JFXTextField PhoneTxtF;

    @FXML
    private Button SearchButt;

    @FXML
    private JFXTextField StreetTxtF;

    @FXML
    private Button ShowButt;


    @FXML
    private Button AddButt;

    @FXML
    private JFXTextField TestTypeTxtF;

    @FXML
    private Button UpdateButt;

    private static final String DB_URL = "jdbc:postgresql://localhost:5432/postgres";
    private static final String USER = "nadia";
    private static final String PASSWORD = "123456";

    @FXML
    void handleSearch(ActionEvent event) {
        String labName = NameTxtF.getText();
        searchLab(labName);
    }

    private void searchLab(String labName) {
        String query = "SELECT * FROM DentalLabs WHERE lapname = ?";
        try (Connection connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/postgres", "nadia", "123456");
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setString(1, labName);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                // تعبئة حقول النص بالمعلومات
                IDTxtF.setText(String.valueOf(resultSet.getInt("lab_id")));
                CityTxtF.setText(resultSet.getString("city"));
                StreetTxtF.setText(resultSet.getString("street"));
                PhoneTxtF.setText(resultSet.getString("phonenumber"));
                TestTypeTxtF.setText(resultSet.getString("testtype"));
                PaidTxtF.setText(String.valueOf(resultSet.getDouble("totalamountpaid")));
                DueTxtF.setText(String.valueOf(resultSet.getDouble("totalamountdue")));
            } else {
                showAlert("Error", "Lab not found.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            showAlert("Error", "An error occurred while searching for the lab.");
        }
    }

    @FXML
    void handleUpdate(ActionEvent event) {
        updateLab();
    }

    private void updateLab() {
        int labId = Integer.parseInt(IDTxtF.getText());
        String city = CityTxtF.getText();
        String street = StreetTxtF.getText();
        String phone = PhoneTxtF.getText();
        String testType = TestTypeTxtF.getText();
        double totalPaid = Double.parseDouble(PaidTxtF.getText());
        double totalDue = Double.parseDouble(DueTxtF.getText());

        String query = "UPDATE DentalLabs SET city = ?, street = ?, phonenumber = ?, testtype = ?, totalamountpaid = ?, totalamountdue = ? WHERE lab_id = ?";
        try (Connection connection = DriverManager.getConnection(DB_URL, USER, PASSWORD);
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setString(1, city);
            preparedStatement.setString(2, street);
            preparedStatement.setString(3, phone);
            preparedStatement.setString(4, testType);
            preparedStatement.setDouble(5, totalPaid);
            preparedStatement.setDouble(6, totalDue);
            preparedStatement.setInt(7, labId);

            int rowsAffected = preparedStatement.executeUpdate();
            if (rowsAffected > 0) {
                showAlert("Success", "Lab information updated successfully.");
            } else {
                showAlert("Error", "Lab not found or information not changed.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            showAlert("Error", "An error occurred while updating the lab.");
        }
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }


    @FXML
    private void handleBackButt (ActionEvent event){
        try {
            // تحميل الصفحة السابقة
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Laps.fxml"));
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
    void handleShowButton(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/LapShowAll.fxml"));
            Parent root = loader.load();

            Stage addPatientStage = new Stage();
            addPatientStage.setTitle("Show All ");
            addPatientStage.setScene(new Scene(root));
            addPatientStage.show();
            Stage currentStage = (Stage) ((Button) event.getSource()).getScene().getWindow();
            currentStage.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void handleAddButton(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/AddNewLap.fxml"));
            Parent root = loader.load();

            Stage addPatientStage = new Stage();
            addPatientStage.setTitle("Add New Lab");
            addPatientStage.setScene(new Scene(root));
            addPatientStage.show();
            Stage currentStage = (Stage) ((Button) event.getSource()).getScene().getWindow();
            currentStage.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void handleLogOut() {
        // Load the sign-in interface
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/sign in.fxml"));
            Parent root = loader.load();

            // Create a new stage (window) for the sign-in screen
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setTitle("Welcome To Dental Clinic !");
            stage.show();


            ((Stage) LogOutButton.getScene().getWindow()).close(); // Close the current window

        } catch (Exception e) {
            e.printStackTrace();
            Alert errorAlert = new Alert(Alert.AlertType.ERROR);
            errorAlert.setTitle("Error");
            errorAlert.setHeaderText(null);
            errorAlert.setContentText("Failed to load the sign-in screen. Please try again.");
            errorAlert.showAndWait();
        }
    }
}