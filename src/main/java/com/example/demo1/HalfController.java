package com.example.demo1;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;

import javafx.event.ActionEvent;
import java.io.IOException;


public class HalfController {

    @FXML
    private Button AppButton;

    @FXML
    private Button DoctorButton;

    @FXML
    private Button ExpensesButton;

    @FXML
    private AnchorPane HalfAnch;

    @FXML
    private ImageView HalfImag;

    @FXML
    private Button InventoryButton;

    @FXML
    private Button LabButton;

    @FXML
    private Button LogOutButton;

    @FXML
    private Pane Pane1;

    @FXML
    private Pane Pane2;

    @FXML
    private Pane Pane3;

    @FXML
    private Button PatientButton;

    @FXML
    private Label TitelLabel;


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

        // Optionally, close the current window (Half screen)
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

    @FXML
    private void handlePatientButton() {
        // Load the patient interface
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/halfPatient.fxml")); // تأكد من أن المسار صحيح
            Parent root = loader.load();

            // Create a new stage (window) for the patient screen
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setTitle("Patient Management");
            stage.show();

            // Optionally, close the current window (Half screen)
            ((Stage) PatientButton.getScene().getWindow()).close(); // Close the current window

        } catch (Exception e) {
            e.printStackTrace();
            Alert errorAlert = new Alert(Alert.AlertType.ERROR);
            errorAlert.setTitle("Error");
            errorAlert.setHeaderText(null);
            errorAlert.setContentText("Failed to load the patient screen. Please try again.");
            errorAlert.showAndWait();
        }
    }

    @FXML
    private void handleLabButtonAction(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Laps.fxml"));
            Parent newPage = loader.load();

            // الحصول على المشهد الحالي
            Scene currentScene = LabButton.getScene();

            // الحصول على مرحلة العرض
            Stage stage = (Stage) currentScene.getWindow();

            // إعداد المشهد الجديد
            stage.setScene(new Scene(newPage));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();

        }
    }

    @FXML
    private void initialize() {
        LogOutButton.setOnAction(event -> handleLogOut()); // Assign the event handler to the logout button
        PatientButton.setOnAction(event -> handlePatientButton()); // Assign the event handler to the patient button
    }
}

