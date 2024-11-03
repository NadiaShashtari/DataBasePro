
package com.example.demo1;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.beans.property.SimpleObjectProperty;
import javafx.stage.Stage;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;

import java.sql.PreparedStatement; // لتشغيل استعلامات SQL
import java.sql.ResultSet; // لنتائج استعلامات SQL
import java.sql.SQLException; // لإدارة الأخطاء المتعلقة بقاعدة البيانات
import javafx.scene.control.TableCell;


public class LabShowAll {

    @FXML
    private TableColumn<Map<String, Object>, Integer> IDColumn;

    @FXML
    private TableColumn<Map<String, Object>, String> NameColumn;

    @FXML
    private TableColumn<Map<String, Object>, String> CityColumn;

    @FXML
    private TableColumn<Map<String, Object>, String> StreetColumn;


    @FXML
    private TableColumn<Map<String, Object>, String> PhoneColumn;

    @FXML
    private TableColumn<Map<String, Object>, String> TestTypeColumn;

    @FXML
    private TableColumn<Map<String, Object>, Double> PaidColumn;

    @FXML
    private TableColumn<Map<String, Object>, Double> DueColumn;

    @FXML
    private TableView<Map<String, Object>> LapTableView;

    @FXML
    private Button BackButt;

    @FXML
    private Button LogOutButton;

    @FXML
    private TableColumn<Map<String, Object>, Boolean> selectColumn;

    private ObservableList<Map<String, Object>> labData = FXCollections.observableArrayList();


    @FXML
    private void initialize() {
        // ربط الأعمدة مع بيانات الجدول
        IDColumn.setCellValueFactory(cellData -> new SimpleObjectProperty<>((Integer) cellData.getValue().get("LabID")));
        NameColumn.setCellValueFactory(cellData -> new SimpleObjectProperty<>((String) cellData.getValue().get("Name")));
        CityColumn.setCellValueFactory(cellData -> new SimpleObjectProperty<>((String) cellData.getValue().get("city")));
        StreetColumn.setCellValueFactory(cellData -> new SimpleObjectProperty<>((String) cellData.getValue().get("street")));
        PhoneColumn.setCellValueFactory(cellData -> new SimpleObjectProperty<>((String) cellData.getValue().get("PhoneNumber")));
        TestTypeColumn.setCellValueFactory(cellData -> new SimpleObjectProperty<>((String) cellData.getValue().get("TestType")));
        PaidColumn.setCellValueFactory(cellData -> new SimpleObjectProperty<>((Double) cellData.getValue().get("TotalAmountPaid")));
        DueColumn.setCellValueFactory(cellData -> new SimpleObjectProperty<>((Double) cellData.getValue().get("TotalAmountDue")));

        selectColumn.setCellValueFactory(cellData -> {
            Map<String, Object> row = cellData.getValue();
            SimpleObjectProperty<Boolean> selectedProperty = new SimpleObjectProperty<>((Boolean) row.get("selected"));

            // تحديث القيمة في الخريطة عند تغيير حالة الـ CheckBox
            selectedProperty.addListener((observable, oldValue, newValue) -> row.put("selected", newValue));
            return selectedProperty;
        });

        selectColumn.setCellFactory(col -> {
            return new TableCell<>() {
                private final CheckBox checkBox = new CheckBox();

                {
                    // تحديث الخريطة عند تغيير حالة الـ CheckBox
                    checkBox.setOnAction(e -> {
                        Map<String, Object> row = getTableView().getItems().get(getIndex());
                        row.put("selected", checkBox.isSelected());
                    });
                }

                @Override
                protected void updateItem(Boolean selected, boolean empty) {
                    super.updateItem(selected, empty);
                    if (empty) {
                        setGraphic(null);
                    } else {
                        checkBox.setSelected(selected != null && selected);
                        setGraphic(checkBox);
                    }
                }
            };
        });


        loadLabData();
        LapTableView.setItems(labData);
    }

    private void loadLabData() {
        String url = "jdbc:postgresql://localhost:5432/postgres";
        String user = "nadia";
        String password = "123456";

        try {
            Connection connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/postgres", "nadia", "123456");
            Statement statement = connection.createStatement();
            String query = "SELECT lab_id, lapname, city, street, phonenumber, testtype, totalamountpaid, totalamountdue FROM dentallabs";
            ResultSet resultSet = statement.executeQuery(query);

            while (resultSet.next()) {
                Map<String, Object> labMap = new HashMap<>();
                labMap.put("LabID", resultSet.getInt("lab_id"));
                labMap.put("Name", resultSet.getString("lapname"));
                labMap.put("city", resultSet.getString("city")); // Ensure consistency in key names
                labMap.put("street", resultSet.getString("street"));
                labMap.put("PhoneNumber", resultSet.getString("phonenumber"));
                labMap.put("TestType", resultSet.getString("testtype"));
                labMap.put("TotalAmountPaid", resultSet.getDouble("totalamountpaid"));
                labMap.put("TotalAmountDue", resultSet.getDouble("totalamountdue"));
                labMap.put("selected", false);


                labData.add(labMap);
            }

            resultSet.close();
            statement.close();
            connection.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void handleBackButt(ActionEvent event) {
        try {
            // تحميل الصفحة السابقة
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Laps.fxml"));
            Parent previousPage = loader.load();

            // الحصول على المشهد الحالي
            Scene currentScene = BackButt.getScene();

            // الحصول على مرحلة العرض
            Stage stage = (Stage) currentScene.getWindow();

            // إعداد المشهد الجديد
            stage.setScene(new Scene(previousPage));
            stage.show();
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

    @FXML
    void handleDeleteButton(ActionEvent event) {
        for (Map<String, Object> lab : LapTableView.getItems()) {
            if (lab.get("selected") instanceof Boolean && (Boolean) lab.get("selected")) {
                int labId = (Integer) lab.get("LabID");
                deleteLabFromDatabase(labId);
                labData.remove(lab); // إزالة الصف من جدول العرض
                break;
            }
        }
    }

    private void deleteLabFromDatabase(int labId) {

        String query = "DELETE FROM dentallabs WHERE lab_id = ?";
        try (Connection connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/postgres", "nadia", "123456");
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setInt(1, labId);
            int rowsAffected = preparedStatement.executeUpdate();
            if (rowsAffected > 0) {
                showAlert("Success", "Lab deleted successfully.");
            } else {
                showAlert("Error", "Lab not found.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            showAlert("Error", "An error occurred while deleting the lab.");
        }
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    @FXML
    void handleSearchButton(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/LapSearch.fxml"));
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
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/AddNewLap.fxml"));
            Parent root = loader.load();

            Stage addPatientStage = new Stage();
            addPatientStage.setTitle("Add New Labs");
            addPatientStage.setScene(new Scene(root));
            addPatientStage.show();
            Stage currentStage = (Stage) ((Button) event.getSource()).getScene().getWindow();
            currentStage.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

