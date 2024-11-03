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
import java.io.IOException;
import javafx.animation.TranslateTransition;
import javafx.util.Duration;

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
    void handleAddButton(ActionEvent event) {
        try {
            // تحميل FXML الخاص بالواجهة الجديدة
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/AddNewPatient.fxml"));
            Parent root = loader.load();

            Stage addPatientStage = new Stage();
            addPatientStage.setTitle("Add Patient");
            addPatientStage.setScene(new Scene(root));
            addPatientStage.show();
            Stage currentStage = (Stage) ((Button) event.getSource()).getScene().getWindow();
            currentStage.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    @FXML
    private void handleSearchButton(ActionEvent event) {
        // Load the search interface
        try {

            Stage currentStage = (Stage) SearchButton1.getScene().getWindow();

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

    @FXML
    void handleLabButton(ActionEvent event) {
        try {
            // تحميل FXML الخاص بالواجهة الجديدة
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Laps.fxml"));
            Parent root = loader.load();

            Stage addPatientStage = new Stage();
            addPatientStage.setTitle("Lab");
            addPatientStage.setScene(new Scene(root));
            addPatientStage.show();
            Stage currentStage = (Stage) ((Button) event.getSource()).getScene().getWindow();
            currentStage.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void handleExpensesButton() {
        // Load the patient interface
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Expenses.fxml"));
            Parent root = loader.load();

            // Create a new stage (window) for the patient screen
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setTitle("Expenses Management");
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
    private void initialize() {
        SearchButton1.setOnAction(this::handleSearchButton); // Assign the event handler to the search button

        animateButton(ShowButton, -300, 0);

        // تحريك AddButton1 من اليسار إلى موضعه الأصلي
        animateButton(AddButton1, -300, 0);

        // تحريك SearchButton1 من اليسار إلى موضعه الأصلي
        animateButton(SearchButton1, -300, 0);
    }

    private void animateButton(Button button, double fromX, double fromY) {
        // إعداد موضع البداية للزر ليبدأ من اليسار خارج الشاشة
        button.setTranslateX(fromX);
        button.setTranslateY(fromY);

        // إنشاء حركة الانتقال
        TranslateTransition transition = new TranslateTransition(Duration.seconds(1), button);
        transition.setToX(0); // العودة إلى الموضع الأصلي
        transition.setToY(0);
        transition.setAutoReverse(false);

        // تشغيل الحركة
        transition.play();
    }
}


