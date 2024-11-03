package com.example.demo1;

import com.jfoenix.controls.JFXTextField;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.control.PasswordField;
import javafx.scene.layout.Pane;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javafx.scene.control.Alert;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.animation.TranslateTransition;
import javafx.scene.control.Button;
import javafx.util.Duration;

public class SignInController {

    @FXML
    private Button ButtonForget;

    @FXML
    private Button ButtonLogin;

    @FXML
    private ImageView ImagViewReception;

    @FXML
    private ImageView ImageViewPassword;

    @FXML
    private ImageView ImageViewUser;

    @FXML
    private PasswordField  passwordfieldSign;

    @FXML
    private JFXTextField JFXTextFieldUser;

    @FXML
    private Label LabelSign;

    @FXML
    private Label LapelWelcome;

    @FXML
    private Pane PanePassword;

    @FXML
    private Pane PaneUser;

    @FXML
    private Pane PaneWelcome;

    private DatabaseConnection databaseConnection = new DatabaseConnection();



    // Method to authenticate user credentials
    private boolean authenticateUser(String email, String password) {
        String query = "SELECT * FROM dentists WHERE email = ? AND password = ?";

        try (Connection conn = databaseConnection.connect();
             PreparedStatement preparedStatement = conn.prepareStatement(query)) {

            preparedStatement.setString(1, email);
            preparedStatement.setString(2, password);

            ResultSet resultSet = preparedStatement.executeQuery();
            return resultSet.next();  // Returns true if user exists

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @FXML
    private void handleLogin() {
        String email = JFXTextFieldUser.getText();
        String password = passwordfieldSign.getText();



        if (authenticateUser(email, password)) {

            // Load the new interface
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/half.fxml")); // Adjust the path as necessary
                Parent root = loader.load();

                // Create a new stage (window)
                Stage stage = new Stage();
                stage.setScene(new Scene(root));
                stage.setTitle("");
                stage.show();

                // Optionally, close the current window
                ((Stage) ButtonLogin.getScene().getWindow()).close(); // Close the login window if needed

            } catch (Exception e) {
                e.printStackTrace();
                Alert errorAlert = new Alert(Alert.AlertType.ERROR);
                errorAlert.setTitle("Error");
                errorAlert.setHeaderText(null);
                errorAlert.setContentText("Failed to load the main application. Please try again.");
                errorAlert.showAndWait();
            }
        } else {
            Alert errorAlert = new Alert(Alert.AlertType.ERROR);
            errorAlert.setTitle("Login Failed");
            errorAlert.setHeaderText(null);
            errorAlert.setContentText("Login failed. Please check your credentials.");
            errorAlert.showAndWait();
        }
    }


}
