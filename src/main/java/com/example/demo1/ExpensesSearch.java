/* package com.example.demo1;

import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import java.sql.DriverManager;
import java.math.BigDecimal;
import java.sql.Connection; // لتمثيل اتصال قاعدة البيانات
import java.sql.PreparedStatement; // لتنفيذ استعلامات قاعدة البيانات
import java.sql.ResultSet; // لتمثيل نتائج الاستعلامات
import java.io.ByteArrayInputStream; // للتعامل مع البيانات الثنائية (مثل الصور)
import java.sql.SQLException; // لمعالجة استثناءات قاعدة البيانات


public class ExpensesSearch {

    @FXML
    private JFXTextField AmountButt, DateButt, DueButt, IDButt, ExpensesTypeButt;

    @FXML
    private ComboBox<String> MethodBox, IdBox, TypeBox11;

    @FXML
    private ImageView CheckImag;

    @FXML
    private Button SearchButt, ShowCheckButt,LogOutButton, UpdateButt;

    private ObservableList<String> dentistIds = FXCollections.observableArrayList(); // قائمة لـ Dentist IDs
    private ObservableList<String> expenseTypes = FXCollections.observableArrayList(); // قائمة لـ Expense Types

    private Connection connection; // اتصل بقاعدة البيانات هنا

    private static final String DB_URL = "jdbc:postgresql://localhost:5432/postgres";
    private static final String USER = "nadia";
    private static final String PASSWORD = "123456";

    @FXML
    void handleSearch(ActionEvent event) {
         try {
            String doctorID = IdBox.getValue();
            String expenseType = TypeBox11.getValue();

            if (doctorID != null && expenseType != null) {
                String query = "SELECT * FROM expenses WHERE dentistid = ? AND expensetype = ?";
                PreparedStatement statement = connection.prepareStatement(query);
                statement.setString(1, doctorID);
                statement.setString(2, expenseType);

                ResultSet resultSet = statement.executeQuery();
                if (resultSet.next()) {
                    IDButt.setText(resultSet.getString("expense_id"));
                    AmountButt.setText(resultSet.getString("amount"));
                    DateButt.setText(resultSet.getString("paymentdate")); // تأكد من تنسيق التاريخ
                    DueButt.setText(resultSet.getString("due"));
                    ExpensesTypeButt.setText(resultSet.getString("expensetype"));
                    MethodBox.setValue(resultSet.getString("paymentmethod"));


                    boolean isCheck = "Check".equals(resultSet.getString("paymentmethod"));
                    ShowCheckButt.setVisible(isCheck);

                    if (isCheck) {
                        String imagePath = resultSet.getString("check_image");
                        if (imagePath != null) {
                            Image image = new Image("file:" + imagePath);
                            CheckImag.setImage(image);
                        } else {
                            CheckImag.setImage(null);
                        }
                    } else {
                        CheckImag.setImage(null);
                    }
                } else {
                    // إذا لم يتم العثور على البيانات
                    clearFields();
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void clearFields() {
        IDButt.clear();
        AmountButt.clear();
        DateButt.clear();
        DueButt.clear();
        ExpensesTypeButt.clear();
        MethodBox.setValue(null);
        CheckImag.setImage(null);
    }



    private void initializeConnection() {
        try {
            String url = "jdbc:mysql://localhost:3306/postgres"; // استبدل "your_database" باسم قاعدة البيانات الخاصة بك
            String user = "nadia"; // اسم المستخدم
            String password = "123456"; // كلمة المرور
            connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/postgres", "nadia", "123456");
        } catch (SQLException e) {
            e.printStackTrace();
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Database Connection Error");
            alert.setHeaderText(null);
            alert.setContentText("Failed to connect to the database. Please check your connection settings.");
            alert.showAndWait();
        }
    }


    @FXML
    void initialize() {
        initializeConnection(); // تهيئة الاتصال بقاعدة البيانات
        loadDentistIds(); // تحميل Dentist IDs عند بدء الشاشة
        loadExpenseTypes(); // تحميل Expense Types عند بدء الشاشة

        MethodBox.setItems(FXCollections.observableArrayList("$$$$", "Check"));

        IdBox.setOnAction(event -> {
            String selectedDoctorId = IdBox.getValue();

            // تحقق مما إذا كانت القيمة مختارة
            if (selectedDoctorId != null) {
                int doctorId = Integer.parseInt(selectedDoctorId);
                // قم بإجراء العملية التي تريدها باستخدام ID للدكتور المحدد
                System.out.println("Selected Doctor ID: " + doctorId);
                // هنا يمكنك استدعاء دالة للبحث عن طريقة الدفع أو تحديث واجهة المستخدم حسب الحاجة
            }
        });

        TypeBox11.setOnAction(event -> {
            String selectedExpenseType = TypeBox11.getValue();
            if (selectedExpenseType != null) {
                // قم بإجراء العملية التي تريدها باستخدام نوع المصروفات المحدد
                System.out.println("Selected Expense Type: " + selectedExpenseType);
                // هنا يمكنك استدعاء دالة للبحث عن ID الدكتور أو تحديث واجهة المستخدم حسب الحاجة
            }
        });
    }

    private void loadDentistIds() {
        try {
            String query = "SELECT dentist_id FROM dentists"; // تعديل حسب قاعدة البيانات الخاصة بك
            PreparedStatement statement = connection.prepareStatement(query);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                dentistIds.add(resultSet.getString("dentist_id"));
            }
            IdBox.setItems(dentistIds);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void loadExpenseTypes() {
        try {
            String query = "SELECT DISTINCT expensetype FROM expenses"; // تعديل حسب قاعدة البيانات الخاصة بك
            PreparedStatement statement = connection.prepareStatement(query);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                expenseTypes.add(resultSet.getString("expensetype"));
            }
            TypeBox11.setItems(expenseTypes);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @FXML
    void handleShowCheck(ActionEvent event) {
        try {
            String expenseID = IDButt.getText();
            String query = "SELECT check_image FROM expenses WHERE expense_id = ?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, expenseID);

            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                // عرض الصورة في CheckImag
                byte[] imageBytes = resultSet.getBytes("check_image");
                if (imageBytes != null) {
                    Image image = new Image(new ByteArrayInputStream(imageBytes));
                    CheckImag.setImage(image);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    void handleUpdate(ActionEvent event) {
        try {
            String expenseID = IDButt.getText();
            String amount = AmountButt.getText();
            String date = DateButt.getText();
            String due = DueButt.getText();
            String expenseType = ExpensesTypeButt.getText();
            String paymentMethod = MethodBox.getValue();

            String query = "UPDATE expenses SET amount = ?, paymentdate = ?, due = ?, expensetype = ?, paymentmethod = ? WHERE expense_id = ?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setBigDecimal(1, new BigDecimal(amount)); // لتحويل Amount إلى BigDecimal
            statement.setDate(2, java.sql.Date.valueOf(date)); // تأكد من تحويل String إلى Date
            statement.setBigDecimal(3, new BigDecimal(due)); // لتحويل Due إلى BigDecimal
            statement.setString(4, expenseType);
            statement.setString(5, paymentMethod);
            statement.setString(6, expenseID);

            statement.executeUpdate();

            System.out.println("تم تحديث البيانات بنجاح");
        } catch (Exception e) {
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
} */


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
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.IOException;
import java.sql.DriverManager;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.io.ByteArrayInputStream;
import java.sql.SQLException;

public class ExpensesSearch {

    @FXML
    private JFXTextField AmountButt, DateButt, DueButt, IDButt ;

    @FXML
    private ComboBox<String> MethodBox, IdBox, TypeBox11;

    @FXML
    private ImageView CheckImag;

    @FXML
    private Button SearchButt, ShowCheckButt, LogOutButton, UpdateButt , BackButt;

    private ObservableList<String> dentistIds = FXCollections.observableArrayList();
    private ObservableList<String> expenseTypes = FXCollections.observableArrayList();

    private Connection connection;

    private static final String DB_URL = "jdbc:postgresql://localhost:5432/postgres";
    private static final String USER = "nadia";
    private static final String PASSWORD = "123456";

    @FXML
    void handleSearch(ActionEvent event) {
        try {
            String doctorID = IdBox.getValue();
            String expenseType = TypeBox11.getValue();

            // التحقق من أن القيم ليست null
            if (doctorID != null && expenseType != null) {
                String query = "SELECT * FROM expenses WHERE dentistid = ? AND expensetype = ?";
                PreparedStatement statement = connection.prepareStatement(query);

                // تحويل doctorID إلى عدد صحيح واستخدام setInt
                try {
                    statement.setInt(1, Integer.parseInt(doctorID)); // إعداد dentistid كعدد صحيح
                } catch (NumberFormatException e) {
                    System.out.println("Invalid Doctor ID: " + doctorID);
                    return; // الخروج من الدالة إذا كان الرقم غير صالح
                }

                statement.setString(2, expenseType); // إعداد expensetype كنص

                ResultSet resultSet = statement.executeQuery();

                if (resultSet.next()) {
                    IDButt.setText(resultSet.getString("expense_id"));
                    AmountButt.setText(resultSet.getString("amount"));
                    DateButt.setText(resultSet.getString("paymentdate"));
                    DueButt.setText(resultSet.getString("due"));
                    MethodBox.setValue(resultSet.getString("paymentmethod"));

                    boolean isCheck = "Check".equals(resultSet.getString("paymentmethod"));
                    ShowCheckButt.setVisible(isCheck);

                    // عرض الصورة إذا كانت طريقة الدفع هي الشيك
                    String imagePath = resultSet.getString("check_image");
                    if (isCheck && imagePath != null) {
                        Image image = new Image("file:" + imagePath);
                        CheckImag.setImage(image);
                    } else {
                        CheckImag.setImage(null); // تعيين الصورة إلى null إذا لم يكن هناك صورة
                    }
                } else {
                    clearFields(); // تفريغ الحقول إذا لم يتم العثور على النتائج
                }
            } else {
                System.out.println("Doctor ID or Expense Type is null.");
            }
        } catch (SQLException e) {
            System.out.println("Database error: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("Unexpected error: " + e.getMessage());
        }
    }

    private void clearFields() {
        IDButt.clear();
        AmountButt.clear();
        DateButt.clear();
        DueButt.clear();
        MethodBox.setValue(null);
        CheckImag.setImage(null);
    }

    private void initializeConnection() {
        try {
            connection = DriverManager.getConnection(DB_URL, USER, PASSWORD);
        } catch (SQLException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Database Connection Error");
            alert.setContentText("Failed to connect to the database: " + e.getMessage());
            alert.showAndWait();
        }
    }

    @FXML
    void initialize() {
        initializeConnection();
        loadDentistIds();
        loadExpenseTypes();

        MethodBox.setItems(FXCollections.observableArrayList("Cash", "Check"));
    }

    private void loadDentistIds() {
        try {
            String query = "SELECT dentist_id FROM dentists";
            PreparedStatement statement = connection.prepareStatement(query);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                dentistIds.add(resultSet.getString("dentist_id"));
            }
            IdBox.setItems(dentistIds);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void loadExpenseTypes() {
        try {
            String query = "SELECT DISTINCT expensetype FROM expenses";
            PreparedStatement statement = connection.prepareStatement(query);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                expenseTypes.add(resultSet.getString("expensetype"));
            }
            TypeBox11.setItems(expenseTypes);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    void handleShowCheck(ActionEvent event) {
        try {
            String expenseID = IDButt.getText();
            if (expenseID.isEmpty()) {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setContentText("يرجى إدخال معرف المصروفات.");
                alert.showAndWait();
                return;
            }

            int id = Integer.parseInt(expenseID); // تحويل إلى عدد صحيح

            // استعلام للتحقق من طريقة الدفع
            String queryPaymentMethod = "SELECT paymentmethod FROM expenses WHERE expense_id = ?";
            PreparedStatement statementPaymentMethod = connection.prepareStatement(queryPaymentMethod);
            statementPaymentMethod.setInt(1, id);

            ResultSet resultSetPaymentMethod = statementPaymentMethod.executeQuery();
            if (resultSetPaymentMethod.next()) {
                String paymentMethod = resultSetPaymentMethod.getString("paymentmethod");

                // تحقق مما إذا كانت طريقة الدفع نقدية
                if ("Cash".equalsIgnoreCase(paymentMethod)) {
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setContentText("الدفع تم نقدًا، ولا توجد صورة شيك مرتبطة بهذا المعرف.");
                    alert.showAndWait();
                    return;
                }

                // إذا كانت طريقة الدفع ليست نقدية، استعلام لاسترداد صورة الشيك
                String queryImage = "SELECT check_image FROM expenses WHERE expense_id = ?";
                PreparedStatement statementImage = connection.prepareStatement(queryImage);
                statementImage.setInt(1, id);

                ResultSet resultSetImage = statementImage.executeQuery();

                if (resultSetImage.next()) {
                    byte[] imageBytes = resultSetImage.getBytes("check_image");
                    if (imageBytes != null) {
                        Image image = new Image(new ByteArrayInputStream(imageBytes));
                        CheckImag.setImage(image); // تعيين الصورة
                    } else {
                        Alert alert = new Alert(Alert.AlertType.INFORMATION);
                        alert.setContentText("لا توجد صورة شيك مرتبطة بهذا المعرف.");
                        alert.showAndWait();
                    }
                } else {
                    Alert alert = new Alert(Alert.AlertType.WARNING);
                    alert.setContentText("لم يتم العثور على مصروفات بهذا المعرف.");
                    alert.showAndWait();
                }

            } else {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setContentText("لم يتم العثور على مصروفات بهذا المعرف.");
                alert.showAndWait();
            }

        } catch (NumberFormatException e) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setContentText("يرجى إدخال معرف مصروفات صحيح.");
            alert.showAndWait();
        } catch (SQLException e) {
            e.printStackTrace(); // من الأفضل أيضًا عرض تنبيه للمستخدم
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("حدث خطأ أثناء الوصول إلى قاعدة البيانات.");
            alert.showAndWait();
        }
    }

    @FXML
    void handleUpdate(ActionEvent event) {
        try {
            String expenseID = IDButt.getText();
            String amount = AmountButt.getText();
            String date = DateButt.getText();
            String due = DueButt.getText();
            String paymentMethod = MethodBox.getValue();

            // تحقق من ملء جميع الحقول
            if (expenseID.isEmpty() || amount.isEmpty() || date.isEmpty() || due.isEmpty() || paymentMethod == null) {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Input Error");
                alert.setContentText("يرجى ملء جميع الحقول.");
                alert.showAndWait();
                return; // خروج من الدالة إذا فشل التحقق
            }

            // الاستعلام لتحديث البيانات
            String query = "UPDATE expenses SET amount = ?, paymentdate = ?, due = ?, paymentmethod = ? WHERE expense_id = ?";
            PreparedStatement statement = connection.prepareStatement(query);

            // تعيين القيم للمعلمات
            statement.setBigDecimal(1, new BigDecimal(amount)); // تعيين المبلغ
            statement.setDate(2, java.sql.Date.valueOf(date)); // تعيين تاريخ الدفع
            statement.setBigDecimal(3, new BigDecimal(due)); // تعيين المستحقات
            statement.setString(4, paymentMethod); // تعيين طريقة الدفع
            statement.setInt(5, Integer.parseInt(expenseID)); // تعيين معرف المصروفات كعدد صحيح

            // تنفيذ تحديث البيانات
            int rowsUpdated = statement.executeUpdate();
            if (rowsUpdated > 0) {
                System.out.println("Data updated successfully");
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Success");
                alert.setContentText("تم تحديث البيانات بنجاح.");
                alert.showAndWait();
            }

        } catch (NumberFormatException e) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Input Error");
            alert.setContentText("يرجى إدخال معرف مصروفات صحيح.");
            alert.showAndWait();
        } catch (SQLException e) {
            e.printStackTrace();
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Database Error");
            alert.setContentText("حدث خطأ أثناء تحديث البيانات.");
            alert.showAndWait();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    void handleShowButton(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/ShowAllExpenses.fxml"));
            Parent root = loader.load();

            Stage addPatientStage = new Stage();
            addPatientStage.setTitle("Show All ");
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
            addPatientStage.setTitle("Add New Expenses");
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
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/sign in.fxml"));
            Parent root = loader.load();
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setTitle("Sign In");
            stage.show();
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
    private void handleBackButt (ActionEvent event){
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Expenses.fxml"));
            Parent previousPage = loader.load();

            Scene currentScene = BackButt.getScene();

            Stage stage = (Stage) currentScene.getWindow();

            stage.setScene(new Scene(previousPage));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();

        }
    }
}



