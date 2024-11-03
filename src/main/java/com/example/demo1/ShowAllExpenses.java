package com.example.demo1;

import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import com.jfoenix.controls.JFXTextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;

public class ShowAllExpenses {

    @FXML
    private TableColumn<Map<String, Object>, Integer> IDColumn;
    @FXML
    private TableColumn<Map<String, Object>, Double> TotalColumn;
    @FXML
    private TableColumn<Map<String, Object>, Double> DueColumn;
    @FXML
    private TableColumn<Map<String, Object>, String> DateColumn;
    @FXML
    private TableColumn<Map<String, Object>, String> DentistIDColumn;
    @FXML
    private TableColumn<Map<String, Object>, String> MethodColumn;
    @FXML
    private TableColumn<Map<String, Object>, String> TypeColumn;

    @FXML
    private TableColumn<Map<String, Object>, Boolean> SelectColumn;

    @FXML
    private TableView<Map<String, Object>> expensesTable;

    @FXML
    private JFXTextField TotalAmountTxt;
    @FXML
    private Button AddButt, BackButt, CalcButt, SearchButt, ShowAllButt , LogOutButton;

    private ObservableList<Map<String, Object>> expenseData = FXCollections.observableArrayList();

    @FXML
    private void initialize() {
        IDColumn.setCellValueFactory(cellData -> new SimpleObjectProperty<>((Integer) cellData.getValue().get("ExpenseID")));
        TotalColumn.setCellValueFactory(cellData -> new SimpleObjectProperty<>((Double) cellData.getValue().get("Amount")));
        DueColumn.setCellValueFactory(cellData -> new SimpleObjectProperty<>((Double) cellData.getValue().get("Due")));
        DateColumn.setCellValueFactory(cellData -> new SimpleObjectProperty<>((String) cellData.getValue().get("Date")));
        DentistIDColumn.setCellValueFactory(cellData -> new SimpleObjectProperty<>((String) cellData.getValue().get("DentistID")));
        MethodColumn.setCellValueFactory(cellData -> new SimpleObjectProperty<>((String) cellData.getValue().get("Method")));
        TypeColumn.setCellValueFactory(cellData -> new SimpleObjectProperty<>((String) cellData.getValue().get("Type")));

        SelectColumn.setCellValueFactory(cellData -> {
            Map<String, Object> row = cellData.getValue();
            SimpleObjectProperty<Boolean> selectedProperty = new SimpleObjectProperty<>((Boolean) row.get("selected"));
            selectedProperty.addListener((observable, oldValue, newValue) -> row.put("selected", newValue));
            return selectedProperty;
        });

        SelectColumn.setCellFactory(col -> new TableCell<>() {
            private final CheckBox checkBox = new CheckBox();
            {
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
        });

        loadExpenseData();
        expensesTable.setItems(expenseData);
    }

    private void loadExpenseData() {
        String url = "jdbc:postgresql://localhost:5432/postgres";
        String user = "nadia";
        String password = "123456";

        try (Connection connection = DriverManager.getConnection(url, user, password);
             Statement statement = connection.createStatement()) {
            String query = "SELECT expense_id, amount, due, paymentdate, dentistid, paymentmethod, expensetype FROM Expenses";
            ResultSet resultSet = statement.executeQuery(query);

            while (resultSet.next()) {
                Map<String, Object> expenseMap = new HashMap<>();
                expenseMap.put("ExpenseID", resultSet.getInt("expense_id"));
                expenseMap.put("Amount", resultSet.getDouble("amount"));
                expenseMap.put("Due", resultSet.getDouble("due"));
                expenseMap.put("Date", resultSet.getString("paymentdate"));
                expenseMap.put("DentistID", resultSet.getString("dentistid"));
                expenseMap.put("Method", resultSet.getString("paymentmethod"));
                expenseMap.put("Type", resultSet.getString("expensetype"));
                expenseMap.put("selected", false);

                expenseData.add(expenseMap);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void handleCalculateButton(ActionEvent event) {
        double totalAmount = 0.0;
        for (Map<String, Object> expense : expensesTable.getItems()) {
            if (expense.get("selected") instanceof Boolean && (Boolean) expense.get("selected")) {
                totalAmount += (Double) expense.get("Amount");
            }
        }
        TotalAmountTxt.setText(" " + totalAmount);
    }

    @FXML
    void handleSearchButton(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/ExpensesSearch.fxml"));
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
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/AddNewExpenses.fxml"));
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


    @FXML
    private void handleBackButt(ActionEvent event) {
        try {
            // تحميل الصفحة السابقة
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Expenses.fxml"));
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

}




