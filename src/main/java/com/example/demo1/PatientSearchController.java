package com.example.demo1;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class PatientSearchController {

    @FXML
    private Button AddButton1;

    @FXML
    private Button AppButton;

    @FXML
    private Button DoctorButton;

    @FXML
    private Button ExpensesButton;

    @FXML
    private Button InventoryButton;

    @FXML
    private Button LabButton;

    @FXML
    private Button LogOutButton;

    @FXML
    private Pane Pane11;

    @FXML
    private Pane Pane4;

    @FXML
    private Pane Pane41;

    @FXML
    private Pane Pane5;

    @FXML
    private Pane Pane6;

    @FXML
    private Pane Pane7;

    @FXML
    private Button PatientButton;

    @FXML
    private Button SearchButton;

    @FXML
    private Button SearchNameButton;

    @FXML
    private Button SearchPhoneButton;

    @FXML
    private Button ShowButton;

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
    void handleSearchByPhone(ActionEvent event) {
        try {

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/SearchByPhone.fxml"));
            Parent root = loader.load();
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setTitle("Search by Phone");
            stage.show();

        } catch (Exception e) {
            e.printStackTrace();
            Alert errorAlert = new Alert(Alert.AlertType.ERROR);
            errorAlert.setTitle("Error");
            errorAlert.setHeaderText(null);
            errorAlert.setContentText("Failed to load the search by phone screen. Please try again.");
            errorAlert.showAndWait();
        }
    }
}
