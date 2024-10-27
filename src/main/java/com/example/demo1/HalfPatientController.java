package com.example.demo1;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class HalfPatientController {

    @FXML
    private Button AddButton1;

    @FXML
    private AnchorPane Anchhalfpatient;

    @FXML
    private Button AppButton;

    @FXML
    private Button DoctorButton;

    @FXML
    private Button ExpensesButton;

    @FXML
    private ImageView ImagHP;

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
    private Pane Pane4;

    @FXML
    private Button PatientButton;

    @FXML
    private Button SearchButton1;

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
    private void handleSearchButton(ActionEvent event) {
        // Load the search interface
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Search.fxml"));
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

    @FXML
    private void initialize() {
        SearchButton1.setOnAction(this::handleSearchButton); // Assign the event handler to the search button
    }
}


