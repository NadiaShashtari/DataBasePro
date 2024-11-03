
package com.example.demo1;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.Alert;
import javafx.beans.property.SimpleObjectProperty;
import javafx.stage.Stage;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.scene.control.cell.CheckBoxTableCell;

import java.sql.ResultSet; // لنتائج استعلامات SQL
import javafx.scene.control.CheckBox;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;

public class showAllPatient {

    @FXML
    private Button AddButt;

    @FXML
    private TableColumn<Map<String, Object>, String> BDColumn;

    @FXML
    private Button BackButt;

    @FXML
    private TableColumn<Map<String, Object>, Double> DueColumn;

    @FXML
    private TableColumn<Map<String, Object>, String> EmailColumn;

    @FXML
    private TableColumn<Map<String, Object>, Integer> IDColumn;

    @FXML
    private TableColumn<Map<String, Object>, String> NameColumn;

    @FXML
    private TableColumn<Map<String, Object>, Double> PaidColumn;

    @FXML
    private TableView<Map<String, Object>> PatientTabel;

    @FXML
    private TableColumn<Map<String, Object>, String> PhoneColumn;

    @FXML
    private Button SearchBut;

    @FXML
    private Button ShowButt;

    @FXML
    private Button LogOutButton;

    @FXML
    private TableColumn<Map<String, Object>, String> TPColumn;

    @FXML
    private TableColumn<Map<String, Object>, Double> TotalColumn;

    private ObservableList<Map<String, Object>> patientData = FXCollections.observableArrayList();


    @FXML
    private TableColumn<Map<String, Object>, Boolean> SelectColumn;

    @FXML
    private Button DeleteButt1;


    @FXML
    private void initialize() {


        IDColumn.setCellValueFactory(cellData -> new SimpleObjectProperty<>((Integer) cellData.getValue().get("id")));
        NameColumn.setCellValueFactory(cellData -> new SimpleObjectProperty<>((String) cellData.getValue().get("name")));
        BDColumn.setCellValueFactory(cellData -> new SimpleObjectProperty<>((String) cellData.getValue().get("birthDate")));
        PhoneColumn.setCellValueFactory(cellData -> new SimpleObjectProperty<>((String) cellData.getValue().get("phone")));
        EmailColumn.setCellValueFactory(cellData -> new SimpleObjectProperty<>((String) cellData.getValue().get("email")));
        TotalColumn.setCellValueFactory(cellData -> new SimpleObjectProperty<>((Double) cellData.getValue().get("totalTreatmentAmount")));
        PaidColumn.setCellValueFactory(cellData -> new SimpleObjectProperty<>((Double) cellData.getValue().get("paid")));
        DueColumn.setCellValueFactory(cellData -> new SimpleObjectProperty<>((Double) cellData.getValue().get("due")));
        TPColumn.setCellValueFactory(cellData -> new SimpleObjectProperty<>((String) cellData.getValue().get("treatmentPlan")));

        SelectColumn.setCellValueFactory(cellData -> new SimpleBooleanProperty((Boolean) cellData.getValue().get("selected")));
        SelectColumn.setCellFactory(CheckBoxTableCell.forTableColumn(SelectColumn));

        SelectColumn.setCellValueFactory(cellData -> {
            Boolean selected = (Boolean) cellData.getValue().getOrDefault("selected", false);
            return new SimpleObjectProperty<>(selected);
        });

        SelectColumn.setCellFactory(col -> new CheckBoxTableCell<Map<String, Object>, Boolean>() {
            @Override
            public void updateItem(Boolean item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setGraphic(null);
                } else {
                    CheckBox checkBox = new CheckBox();
                    checkBox.setSelected(item);
                    checkBox.setOnAction(event -> {
                        Map<String, Object> rowData = getTableRow().getItem();
                        if (rowData != null) {
                            rowData.put("selected", checkBox.isSelected());
                        }
                    });
                    setGraphic(checkBox);
                }
            }
        });

        loadPatientData();

        // تعيين قائمة المرضى في الجدول
        PatientTabel.setItems(patientData);
    }

    private void loadPatientData() {
        String url = "jdbc:postgresql://localhost:5432/postgres";
        String user = "nadia";
        String password = "123456";

        try {
            // إنشاء اتصال بقاعدة البيانات
            Connection connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/postgres", "nadia", "123456");
            Statement statement = connection.createStatement();

            // استعلام SQL لاسترجاع جميع المرضى
            String query = "SELECT patient_id, firstname, middelname, lastname, email, phonenumber, birthday, treatmentplan, paid, due, totaltreatmentamount FROM patients";
            ResultSet resultSet = statement.executeQuery(query);

            // التكرار على النتائج وإضافة كل مريض إلى القائمة
            while (resultSet.next()) {

                int patientId = resultSet.getInt("patient_id");
                String firstname = resultSet.getString("firstname");
                String middelname = resultSet.getString("middelname");
                String lastname = resultSet.getString("lastname");
                String email = resultSet.getString("email");
                String phonenumber = resultSet.getString("phonenumber");
                String birthday = resultSet.getString("birthday");
                String treatmentplan = resultSet.getString("treatmentplan");
                double paid = resultSet.getDouble("paid");
                double due = resultSet.getDouble("due");
                double totaltreatmentamount = resultSet.getDouble("totaltreatmentamount");

                Map<String, Object> patientMap = new HashMap<>();
                patientMap.put("id", patientId);
                patientMap.put("name", firstname + " " + middelname + " " + lastname);
                patientMap.put("email", email);
                patientMap.put("phone", phonenumber);
                patientMap.put("birthDate", birthday);
                patientMap.put("treatmentPlan", treatmentplan);
                patientMap.put("paid", paid);
                patientMap.put("due", due);
                patientMap.put("totalTreatmentAmount", totaltreatmentamount);
                patientMap.put("selected", false);

                // إضافة الخريطة إلى قائمة المرضى
                patientData.add(patientMap);
            }

            // إغلاق الاتصال
            resultSet.close();
            statement.close();
            connection.close();
        } catch (Exception e) {
            e.printStackTrace();
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
    void handleDeleteButton(ActionEvent event) {
        ObservableList<Map<String, Object>> selectedPatients = FXCollections.observableArrayList();

        // Collect selected patients
        for (Map<String, Object> patient : patientData) {
            if ((Boolean) patient.get("selected")) {
                selectedPatients.add(patient);
            }
        }

        // Delete from the database
        deletePatients(selectedPatients);

        // Remove selected patients from the ObservableList
        patientData.removeAll(selectedPatients);
        PatientTabel.refresh();
    }

    private void deletePatients(ObservableList<Map<String, Object>> selectedPatients) {
        String url = "jdbc:postgresql://localhost:5432/postgres";
        String user = "nadia";
        String password = "123456";

        try {
            Connection connection = DriverManager.getConnection(url, user, password);
            Statement statement = connection.createStatement();

            for (Map<String, Object> patient : selectedPatients) {
                int patientId = (Integer) patient.get("id");
                String query = "DELETE FROM patients WHERE patient_id = " + patientId;
                statement.executeUpdate(query);
            }

            statement.close();
            connection.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    void handleBackButton(ActionEvent event) {
        // إغلاق الشاشة الحالية
        Stage currentStage = (Stage) BackButt.getScene().getWindow();
        currentStage.close();

        try {

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/halfPatient.fxml"));
            Parent root = loader.load();
            Stage previousStage = new Stage();
            previousStage.setTitle("Half Patient Screen");
            previousStage.setScene(new Scene(root));
            previousStage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    @FXML
    void handleSearchButton(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/SearchByPhone.fxml"));
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

}