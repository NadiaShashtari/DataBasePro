package com.example.demo1;

import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import com.jfoenix.controls.JFXTextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;



public class AddNewLab {

    @FXML
    private Button AddActionButt;

    @FXML
    private Button AddButt;

    @FXML
    private Button BackButt;

    @FXML
    private JFXTextField CityTxtF;

    @FXML
    private JFXTextField DueTxtF;

    @FXML
    private JFXTextField NameTxtF;

    @FXML
    private JFXTextField PaidTxtF;

    @FXML
    private JFXTextField PhoneTxtF;

    @FXML
    private Button SearchButt;

    @FXML
    private Button ShowButt;

    @FXML
    private JFXTextField StreetTxtF;

    @FXML
    private Button LogOutButton;


    @FXML
    private JFXTextField TestTypeTxtF;

    private final String DB_URL = "jdbc:postgresql://localhost:5432/postgres";
    private final String USER = "nadia";
    private final String PASSWORD = "123456";

    @FXML
    private void handleAddActionButton() {
        String labName = NameTxtF.getText();
        String city = CityTxtF.getText();
        String street = StreetTxtF.getText();
        String phoneNumber = PhoneTxtF.getText();
        String testType = TestTypeTxtF.getText();
        String totalPaid = PaidTxtF.getText();
        String totalDue = DueTxtF.getText();

        String insertQuery = "INSERT INTO dentallabs (lapname, city, street, phonenumber, testtype, totalamountpaid, totalamountdue) VALUES (?, ?, ?, ?, ?, ?, ?)";

        try (Connection connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/postgres", "nadia", "123456");
             PreparedStatement preparedStatement = connection.prepareStatement(insertQuery)) {

            preparedStatement.setString(1, labName);
            preparedStatement.setString(2, city);
            preparedStatement.setString(3, street);
            preparedStatement.setString(4, phoneNumber);
            preparedStatement.setString(5, testType);
            preparedStatement.setDouble(6, Double.parseDouble(totalPaid)); // تأكد من تحويل القيم إلى النوع الصحيح
            preparedStatement.setDouble(7, Double.parseDouble(totalDue));

            int rowsAffected = preparedStatement.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Data added successfully!");
            }

        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Error adding data: " + e.getMessage());

        }

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
    void handleSearchButton(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/LapSearch.fxml"));
            Parent root = loader.load();

            Stage addPatientStage = new Stage();
            addPatientStage.setTitle("Search");
            addPatientStage.setScene(new Scene(root));
            addPatientStage.show();
            Stage currentStage = (Stage) ((Button) event.getSource()).getScene().getWindow();
            currentStage.close();

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
            addPatientStage.setTitle("Show all");
            addPatientStage.setScene(new Scene(root));
            addPatientStage.show();
            Stage currentStage = (Stage) ((Button) event.getSource()).getScene().getWindow();
            currentStage.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
