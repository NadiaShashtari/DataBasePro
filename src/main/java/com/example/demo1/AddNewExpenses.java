package com.example.demo1;

import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.io.IOException;
import java.math.BigDecimal;                   // للتعامل مع القيم العددية (مثل المبالغ المالية)
import java.sql.Date;                          // لتعيين تاريخ في قاعدة البيانات
import java.nio.file.Files; // استيراد مكتبة Files لمعالجة الملفات

import java.sql.ResultSet;





public class AddNewExpenses {

    @FXML
    private Button AddButt , LogOutButton;

    @FXML
    private Button BackButt;

    @FXML
    private JFXTextField DateTxt;

    @FXML
    private JFXTextField DueTxt;

    @FXML
    private JFXTextField FullAmountTxt;

    @FXML
    private ComboBox<String> IDBox; // Specify the type for better code readability

    @FXML
    private ComboBox<String> TypeBox1;

    @FXML
    private ComboBox<String> MethodBox;

    @FXML
    private Button UploadButt;

    private File selectedImageFile; // To hold the uploaded image file
    private Connection connection;



    @FXML
    void initialize() {
        loadDentists();      // لتحميل بيانات الدكاترة
        loadExpenseTypes();  // لتحميل بيانات أنواع المصروفات
        loadPaymentMethods(); // لتحميل طرق الدفع
    }

    private void loadDentists() {
        String query = "SELECT dentist_id FROM dentists"; // استعلام للحصول على الـ ID
        try (PreparedStatement statement = connection.prepareStatement(query);
             ResultSet resultSet = statement.executeQuery()) {
            while (resultSet.next()) {

                IDBox.getItems().add(resultSet.getString("dentist_id")); // إضافة الـ ID إلى الكومبو بوكس
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void loadExpenseTypes() {
        String query = "SELECT DISTINCT expensetype FROM expenses"; // استعلام للحصول على أنواع المصروفات
        try (PreparedStatement statement = connection.prepareStatement(query);
             ResultSet resultSet = statement.executeQuery()) {
            while (resultSet.next()) {
                TypeBox1.getItems().add(resultSet.getString("expensetype")); // إضافة الأنواع إلى الكومبو بوكس
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void loadPaymentMethods() {
        MethodBox.getItems().addAll("Cash", "Check"); // إضافة طرق الدفع إلى الكومبو بوكس
    }



    public AddNewExpenses() {
        try {
            connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/postgres", "nadia", "123456");
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("خطأ في الاتصال بقاعدة البيانات.");
        }
    }

    // Method to handle the Add button click
    @FXML
    void handleAdd(ActionEvent event) {

        if (IDBox.getValue() == null || TypeBox1.getValue() == null ||
                FullAmountTxt.getText().isEmpty() || DateTxt.getText().isEmpty() ||
                DueTxt.getText().isEmpty() || MethodBox.getValue() == null) {
            System.out.println("يرجى تعبئة جميع الحقول.");
            return; // الخروج إذا كانت هناك حقول فارغة
        }

        try {
            // التحقق من أن هناك صورة تم تحميلها
            byte[] imageData = null;
            if (selectedImageFile != null) {
                imageData = Files.readAllBytes(selectedImageFile.toPath());
            }

            // استعلام الإدراج
            String query = "INSERT INTO expenses (dentistid, expensetype, amount, paymentdate, due, paymentmethod, check_image) VALUES (?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement statement = connection.prepareStatement(query);

            // ضبط القيم في الاستعلام
            // تحويل dentistId إلى Integer
            int dentistId = Integer.parseInt(IDBox.getValue()); // تحويل القيمة إلى عدد صحيح
            statement.setInt(1, dentistId); // استخدام setInt لإدخال القيمة بشكل صحيح
            statement.setString(2, TypeBox1.getValue());
            statement.setBigDecimal(3, new BigDecimal(FullAmountTxt.getText()));
            statement.setDate(4, Date.valueOf(DateTxt.getText()));
            statement.setBigDecimal(5, new BigDecimal(DueTxt.getText()));
            statement.setString(6, MethodBox.getValue());
            statement.setBytes(7, imageData); // إذا كانت الصورة موجودة، يتم إدراجها؛ إذا لم تكن موجودة يتم إدخال قيمة null.

            // تنفيذ الاستعلام
            statement.executeUpdate();
            System.out.println("تم إضافة المصروفات بنجاح.");

        } catch (NumberFormatException e) {
            System.out.println("خطأ في تحويل قيمة ID الطبيب إلى عدد صحيح: " + e.getMessage());
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("خطأ في تحميل الصورة.");
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("خطأ في الاتصال بقاعدة البيانات أو إدراج البيانات.");
        }
    }


    @FXML
    void handleUpload(ActionEvent event) {
        // Check if the selected payment method is "Cash"
        if ("Cash".equals(MethodBox.getValue())) {
            System.out.println("لا يمكن إضافة صورة عند اختيار طريقة الدفع نقدًا.");
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Warning");
            alert.setHeaderText(null);
            alert.setContentText("لا يمكن إضافة صورة عند اختيار طريقة الدفع نقدًا.");
            alert.showAndWait();
            return; // Exit the method to prevent further execution
        }

        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.jpeg"));
        selectedImageFile = fileChooser.showOpenDialog(null);

        if (selectedImageFile != null) {
            System.out.println("تم اختيار الصورة: " + selectedImageFile.getName());
        } else {
            System.out.println("لم يتم تحديد ملف.");
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
    void handleShowButton(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/ShowAllExpenses.fxml"));
            Parent root = loader.load();

            Stage addPatientStage = new Stage();
            addPatientStage.setTitle("Show all");
            addPatientStage.setScene(new Scene(root));
            addPatientStage.show();
            Stage currentStage = (Stage) ((Button) event.getSource()).getScene().getWindow();
            currentStage.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void handleBackButt (ActionEvent event){
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

}
