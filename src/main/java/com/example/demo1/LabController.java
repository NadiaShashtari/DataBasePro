package com.example.demo1;


import javafx.animation.TranslateTransition;
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
import javafx.util.Duration;

import java.io.IOException;

public class LabController {

    @FXML
    private Button AddButt;

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
    private Pane Pane3;

    @FXML
    private Button LogOutButton;


    @FXML
    private Button PatientButton;

    @FXML
    private Button SearchButt;

    @FXML
    private Button ShowButt;

    @FXML
    private void handleShowButtonAction(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/LapShowAll.fxml"));
            Parent newRoot = loader.load();

            Stage currentStage = (Stage) ShowButt.getScene().getWindow();
            currentStage.close();

            // إنشاء مرحلة جديدة للشاشة التالية وعرضها
            Stage newStage = new Stage();
            newStage.setScene(new Scene(newRoot));
            newStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void handleAddButtonAction(ActionEvent event) {
        try {
            // تحميل ملف FXML للشاشة الجديدة
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/AddNewLap.fxml")); // استبدل "NewScreen.fxml" باسم الشاشة الجديدة
            Parent newRoot = loader.load();
            // الحصول على المرحلة الحالية وإغلاقها
            Stage currentStage = (Stage) AddButt.getScene().getWindow();
            currentStage.close();

            // إعداد المرحلة الجديدة وعرضها
            Stage newStage = new Stage();
            newStage.setScene(new Scene(newRoot));
            newStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void handlePatientButtonAction(ActionEvent event) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/halfPatient.fxml"));
            Parent newScreen = fxmlLoader.load();

            Stage stage = new Stage();
            stage.setScene(new Scene(newScreen));
            stage.show();
            ((Stage) PatientButton.getScene().getWindow()).close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void handleSearchButtonAction() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/LapSearch.fxml"));
            Parent newRoot = loader.load();

            Scene newScene = new Scene(newRoot);
            Stage currentStage = (Stage) SearchButt.getScene().getWindow();
            currentStage.setScene(newScene);
            currentStage.show();
        } catch (IOException e) {
            System.err.println("Failed to load LapSearch.fxml: " + e.getMessage());
            e.printStackTrace(); // طباعة الاستثناء
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
