package com.example.demo1;

import javafx.animation.TranslateTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;

public class Expenses {

    @FXML
    private Button AddButt;

    @FXML
    private Button AppButt;

    @FXML
    private Button DoctorButt;

    @FXML
    private Button InventoryButt;

    @FXML
    private Button LabButt;

    @FXML
    private Button LogOutButton;

    @FXML
    private Button PatientButt;

    @FXML
    private Button SearchButt;

    @FXML
    private Button ShowButt;

    @FXML
    private void handlePatientButton() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/halfPatient.fxml"));
            Parent root = loader.load();

            // Create a new stage (window) for the patient screen
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setTitle("Patient Management");
            stage.show();

            // Optionally, close the current window (Half screen)
            ((Stage) PatientButt.getScene().getWindow()).close(); // Close the current window

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
            Scene currentScene = LabButt.getScene();

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
    private void handleSearchButton(ActionEvent event) {
        // Load the search interface
        try {

            Stage currentStage = (Stage) SearchButt.getScene().getWindow();

            currentStage.close(); // إغلاق الشاشة السابقة

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/ExpensesSearch.fxml"));
            Parent root = loader.load();

            // Create a new stage (window) for the search screen
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setTitle("Search ");
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
    void handleAddButton(ActionEvent event) {
        try {
            // تحميل FXML الخاص بالواجهة الجديدة
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/AddNewExpenses.fxml"));
            Parent root = loader.load();

            Stage addPatientStage = new Stage();
            addPatientStage.setTitle("Add ");
            addPatientStage.setScene(new Scene(root));
            addPatientStage.show();
            Stage currentStage = (Stage) ((Button) event.getSource()).getScene().getWindow();
            currentStage.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    @FXML
    void handleShow(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/ShowAllExpenses.fxml"));
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
    private void initialize() {

        animateButton(ShowButt, -300, 0);

        // تحريك AddButton1 من اليسار إلى موضعه الأصلي
        animateButton(AddButt, -300, 0);

        // تحريك SearchButton1 من اليسار إلى موضعه الأصلي
        animateButton(SearchButt, -300, 0);
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
