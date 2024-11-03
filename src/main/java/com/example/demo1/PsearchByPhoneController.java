
package com.example.demo1;

import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import java.sql.DriverManager;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


public class PsearchByPhoneController {

    @FXML
    private Button AddPatientButton2, BackButt, LogOutButton, SearchButt, SearchButton3, ShowButton2, UpdateButt;

    @FXML
    private AnchorPane Ancho11, Ancho22, Ancho33;

    @FXML
    private JFXTextField BDTxt, DueTxt, Email, FirstNameTxt, IDTxt, LastNameTxt, MiddleNameTxt, PaidTxt, PhoneTxt, TreatmentPlanTxt;

    @FXML
    private ImageView Imag55;

    @FXML
    private Label LabelDental;

    @FXML
    private Pane Pane00, Pane444, Pane666, Pane888;

    @FXML
    private Button BackButton;


    private Connection connection;

    // الاتصال بقاعدة البيانات
    public void initialize() {
        try {
            connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/postgres", "nadia", "123456");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void handleSearch(ActionEvent event) {
        String patientId = IDTxt.getText();
        if (patientId.isEmpty()) {
            showAlert("Error", "Please enter a Patient ID.");
            return;
        }

        String query = "SELECT firstname, middelname, lastname, phonenumber, email, birthday, treatmentplan, paid, due " +
                "FROM patients WHERE patient_id = ?";

        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, Integer.parseInt(patientId));
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                // تعبئة الحقول النصية بالبيانات المسترجعة
                FirstNameTxt.setText(resultSet.getString("firstname"));
                MiddleNameTxt.setText(resultSet.getString("middelname"));
                LastNameTxt.setText(resultSet.getString("lastname"));
                PhoneTxt.setText(resultSet.getString("phonenumber"));
                Email.setText(resultSet.getString("email"));
                BDTxt.setText(resultSet.getString("birthday"));
                TreatmentPlanTxt.setText(resultSet.getString("treatmentplan"));
                PaidTxt.setText(resultSet.getString("paid"));
                DueTxt.setText(resultSet.getString("due"));
            } else {
                showAlert("Error", "Patient not found.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            showAlert("Error", "An error occurred while searching for the patient.");
        }
    }

    @FXML
    void handleUpdate(ActionEvent event) {
        String patientId = IDTxt.getText();
        if (patientId.isEmpty()) {
            showAlert("Error", "Please enter a Patient ID.");
            return;
        }

        String firstName = FirstNameTxt.getText();
        String middleName = MiddleNameTxt.getText();
        String lastName = LastNameTxt.getText();
        String phoneNumber = PhoneTxt.getText();
        String email = Email.getText();
        String birthdayStr = BDTxt.getText(); // assume date is in YYYY-MM-DD format
        String treatmentPlan = TreatmentPlanTxt.getText();
        String paidStr = PaidTxt.getText();
        String dueStr = DueTxt.getText();

        java.sql.Date birthday = null;
        BigDecimal paid = null;
        BigDecimal due = null;

        try {
            if (birthdayStr != null && !birthdayStr.isEmpty()) {
                birthday = java.sql.Date.valueOf(birthdayStr); // تحويل النص إلى تاريخ بصيغة SQL
            }

            if (paidStr != null && !paidStr.isEmpty()) {
                paid = new BigDecimal(paidStr); // تحويل النص إلى BigDecimal
            }

            if (dueStr != null && !dueStr.isEmpty()) {
                due = new BigDecimal(dueStr); // تحويل النص إلى BigDecimal
            }

            int patientIdInt = Integer.parseInt(patientId);
            String query = "UPDATE patients SET firstname = ?, middelname = ?, lastname = ?, phonenumber = ?, " +
                    "email = ?, birthday = ?, treatmentplan = ?, paid = ?, due = ? WHERE patient_id = ?";

            try (PreparedStatement statement = connection.prepareStatement(query)) {
                // تعيين قيم الحقول في الاستعلام
                statement.setString(1, firstName);
                statement.setString(2, middleName);
                statement.setString(3, lastName);
                statement.setString(4, phoneNumber);
                statement.setString(5, email);
                statement.setDate(6, birthday);
                statement.setString(7, treatmentPlan);
                statement.setBigDecimal(8, paid);
                statement.setBigDecimal(9, due);
                statement.setInt(10, patientIdInt);

                int rowsUpdated = statement.executeUpdate();
                if (rowsUpdated > 0) {
                    showAlert("Success", "Patient information updated successfully.");
                } else {
                    showAlert("Error", "Failed to update patient information.");
                }
            } catch (SQLException e) {
                e.printStackTrace();
                showAlert("Error", "An error occurred while updating the patient information.");
            }
        } catch (NumberFormatException e) {
            showAlert("Error", "Invalid number format. Please check the numeric fields.");
        }
    }


    @FXML
    private void handleBackButt(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/halfPatient.fxml"));
            Parent previousPage = loader.load();
            Scene currentScene = BackButt.getScene();
            Stage stage = (Stage) currentScene.getWindow();
            stage.setScene(new Scene(previousPage));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void handleLogOut() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/sign in.fxml"));
            Parent root = loader.load();
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setTitle("Welcome To Dental Clinic !");
            stage.show();
            ((Stage) LogOutButton.getScene().getWindow()).close();
        } catch (Exception e) {
            e.printStackTrace();
            showAlert("Error", "Failed to load the sign-in screen. Please try again.");
        }
    }

    @FXML
    void handleShowButton(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/showAllPatient.fxml"));
            Parent root = loader.load();

            Stage addPatientStage = new Stage();
            addPatientStage.setTitle("Show All Patients");
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
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/AddNewPatient.fxml"));
            Parent root = loader.load();

            Stage addPatientStage = new Stage();
            addPatientStage.setTitle("Add New Patients");
            addPatientStage.setScene(new Scene(root));
            addPatientStage.show();
            Stage currentStage = (Stage) ((Button) event.getSource()).getScene().getWindow();
            currentStage.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
