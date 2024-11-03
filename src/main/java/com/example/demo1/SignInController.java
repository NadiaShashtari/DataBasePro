package com.example.demo1;

import com.jfoenix.controls.JFXTextField;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

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
    private JFXTextField JFXTextFieldPassword;

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
    @FXML
    private void showhalf() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(SignInApplication.class.getResource("half.fxml"));
            Scene scene = new Scene(fxmlLoader.load(), 782, 447);
            Stage stage = new Stage();
            stage.setTitle("Welcome To Dental Clinic!");
            stage.setScene(scene);
            stage.show();
            ((Stage) ButtonLogin.getScene().getWindow()).close(); // Close the current window
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}