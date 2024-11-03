/* package com.example.demo1;


import com.jfoenix.controls.JFXTextField;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class dentalcliniccontroller {

    @FXML private JFXTextField doctorIdField;
    @FXML private JFXTextField firstNameField;
    @FXML private JFXTextField middleNameField;
    @FXML private JFXTextField finalNameField;
    @FXML private JFXTextField numberField;
    @FXML private JFXTextField emailField;
    @FXML private JFXTextField specializationField;
    @FXML private JFXTextField salaryField;
    @FXML private ComboBox<String> workShiftComboBox;
    @FXML private Button updateButton;

    private Connection connect() {
    return connection.connect(); // استخدم دالة الاتصال من فئة connection
}

    // جلب البيانات من قاعدة البيانات بناءً على ID
    @FXML
    private void fetchDoctorData() {
        String doctorId = doctorIdField.getText();
        String query = "SELECT * FROM dentists WHERE dentist_id = ?"; // تعديل اسم الجدول هنا

        try (Connection conn = connect(); PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setString(1, doctorId);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                firstNameField.setText(rs.getString("first_name"));
                middleNameField.setText(rs.getString("middle_name"));
                finalNameField.setText(rs.getString("last_name"));
                numberField.setText(rs.getString("phone_number"));
                emailField.setText(rs.getString("email"));
                specializationField.setText(rs.getString("specialization"));
                salaryField.setText(rs.getString("salary"));
                workShiftComboBox.setValue(rs.getString("work_shift"));
            } else {
                System.out.println("No doctor found with the provided ID.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // تحديث البيانات في قاعدة البيانات
    @FXML
    private void updateDoctorData() {
        String doctorId = doctorIdField.getText();
        String query = "UPDATE dentists SET first_name=?, middle_name=?, last_name=?, phone_number=?, email=?, specialization=?, salary=?, work_shift=? WHERE dentist_id=?"; // تعديل اسم الجدول هنا

        try (Connection conn = connect(); PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setString(1, firstNameField.getText());
            pstmt.setString(2, middleNameField.getText());
            pstmt.setString(3, finalNameField.getText());
            pstmt.setString(4, numberField.getText());
            pstmt.setString(5, emailField.getText());
            pstmt.setString(6, specializationField.getText());
            pstmt.setString(7, salaryField.getText());
            pstmt.setString(8, workShiftComboBox.getValue());
            pstmt.setString(9, doctorId);

            int rowsUpdated = pstmt.executeUpdate();
            if (rowsUpdated > 0) {
                System.out.println("Doctor information updated successfully!");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
*/
package com.example.demo1;

/*import javafx.fxml.FXML;


import com.jfoenix.controls.JFXTextField;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;


import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javafx.scene.layout.AnchorPane;


public class dentalcliniccontroller {

    @FXML
    private Button Appointment;

    @FXML
    private Button Doctor;

    @FXML
    private Label ccc;

    @FXML
    private Label ddd;

    @FXML
    private Label ddddd;

    @FXML
    private Label doctor;

    @FXML
    private JFXTextField doctorIdField;

    @FXML
    private JFXTextField emailField;

    @FXML
    private Button expenses;

    @FXML
    private JFXTextField finalNameField;

    @FXML
    private JFXTextField firstNameField;

    @FXML
    private Button inventory;

    @FXML
    private Label jj;

    @FXML
    private Label kk;

    @FXML
    private Button laps;

    @FXML
    private Label ll;

    @FXML
    private Pane lll;

    @FXML
    private Pane llll;

    @FXML
    private Label llllll;

    @FXML
    private JFXTextField middleNameField;

    @FXML
    private Label mmam;

    @FXML
    private Label n;

    @FXML
    private Label nn;

    @FXML
    private JFXTextField numberField;

    @FXML
    private Pane oooo;

    @FXML
    private Button patient;

    @FXML
    private ImageView qqq;

    @FXML
    private JFXTextField salaryField;

    @FXML
    private JFXTextField specializationField;

    @FXML
    private Pane ss;

    @FXML
    private AnchorPane sssss;

    @FXML
    private Button updateButton;

    @FXML
    private ComboBox<String> workShiftComboBox;

    @FXML
    private Label xxx;
    @FXML
    private Button ok;
    @FXML
    private Button log;

    // Method to connect to the PostgreSQL database
    private Connection connect() throws SQLException {
        String url = "jdbc:postgresql://localhost:5432/postgres";
        String user = "baraah"; // اسم المستخدم
        String password = "0000"; // كلمة المرور
        return DriverManager.getConnection(url, user, password);
    }

    // Method to fetch doctor data from the database
    @FXML
    private void fetchDoctorData() {
        String doctorId = doctorIdField.getText();
        String query = "SELECT * FROM dentists WHERE dentist_id = ?"; // تأكد من اسم الجدول والعمود الصحيحين

        try (Connection conn = connect(); PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setString(1, doctorId);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                firstNameField.setText(rs.getString("first_name"));
                middleNameField.setText(rs.getString("middle_name"));
                finalNameField.setText(rs.getString("last_name"));
                numberField.setText(rs.getString("phone_number"));
                emailField.setText(rs.getString("email"));
                specializationField.setText(rs.getString("specialization"));
                salaryField.setText(rs.getString("salary"));
                workShiftComboBox.setValue(rs.getString("work_shift"));
            } else {
                System.out.println("No doctor found with the provided ID.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    // Add this to the initialize method or associate it with the updateButton
    //@FXML
  //  private void initialize() {
        // إضافة مستمع للحدث لحقل إدخال Doctor ID
        /*doctorIdField.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) {
                fetchDoctorData();
            }
        });*/
        //  ok.setOnAction(event -> fetchDoctorData());  }


   // }
   /*@FXML
   private void handleLogButton() {
       try {
           // Load the FXML file for the half screen
           Parent root = FXMLLoader.load(getClass().getResource("half.fxml"));
           Stage stage = (Stage) log.getScene().getWindow(); // الحصول على نافذة الزر الحالي
           stage.setScene(new Scene(root)); // تغيير المشهد
           stage.show(); // عرض المشهد الجديد
       } catch (Exception e) {
           e.printStackTrace(); // التعامل مع الأخطاء
       }
   }

}*/
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;

import javafx.stage.Stage;

import java.sql.*;

public class dentalcliniccontroller {

       @FXML
       private Button Appointment;

       @FXML
       private Button Doctor;

       @FXML
       private Label ccc;
    @FXML
    private Button ok1;

       @FXML
       private Label ddd;

       @FXML
       private Label ddddd;

       @FXML
       private Label doctor;

       @FXML
       private JFXTextField doctorIdField;

       @FXML
       private JFXTextField emailField;

       @FXML
       private Button expenses;

       @FXML
       private JFXTextField finalNameField;

       @FXML
       private JFXTextField firstNameField;

       @FXML
       private Button inventory;

       @FXML
       private Label jj;

       @FXML
       private Label kk;

       @FXML
       private Button laps;

       @FXML
       private Label ll;

       @FXML
       private Pane lll;

       @FXML
       private Pane llll;

       @FXML
       private Label llllll;

       @FXML
       private Button log;

       @FXML
       private JFXTextField middleNameField;

       @FXML
       private Label mmam;

       @FXML
       private Label n;

       @FXML
       private Label nn;

       @FXML
       private JFXTextField numberField;

       @FXML
       private Button ok;

       @FXML
       private Pane oooo;

       @FXML
       private Button patient;

       @FXML
       private ImageView qqq;

       @FXML
       private JFXTextField salaryField;

       @FXML
       private JFXTextField specializationField;

       @FXML
       private Pane ss;

       @FXML
       private AnchorPane sssss;//احااا لشو هاد

       @FXML
       private Button updateButton;

       @FXML
       private ComboBox<String> workShiftComboBox;

       @FXML
       private Label xxx;

       @FXML
       void handbackButton(ActionEvent event) {


         try {
           // Load the FXML file for the half screen
           Parent root = FXMLLoader.load(getClass().getResource("half.fxml"));
           Stage stage = (Stage) log.getScene().getWindow(); // الحصول على نافذة الزر الحالي
           stage.setScene(new Scene(root)); // تغيير المشهد
           stage.show(); // عرض المشهد الجديد
             ((Stage) log.getScene().getWindow()).close();

       } catch (Exception e) {
             e.printStackTrace(); // التعامل مع الأخطاء
         }}

       @FXML
       private void fetchDoctorData() {
           int doctorId = Integer.parseInt(doctorIdField.getText());


           try{
               String URL = "jdbc:postgresql://localhost:5432/postgres";
                String USER = "postgres";
               String PASSWORD = "0000";
               String sql = "SELECT * FROM dentists WHERE dentist_id = "+doctorId;
               Connection conn = DriverManager.getConnection(URL,USER, PASSWORD);
               Statement stmt = conn.createStatement();
               ResultSet rs = stmt.executeQuery(sql);
               System.out.print("ahaijhdakjdnkas");

               if (rs.next()) {
                   firstNameField.setText(rs.getString("first_name"));
                   middleNameField.setText(rs.getString("middle_name"));
                   finalNameField.setText(rs.getString("last_name"));
                   numberField.setText(rs.getString("phone_number"));
                   emailField.setText(rs.getString("email"));
                   specializationField.setText(rs.getString("specialization"));
                   salaryField.setText(rs.getString("salary"));
                  // workShiftComboBox.setValue(rs.getString("work_shift"));
               } else {
                   Alert alert = new Alert(AlertType.WARNING);
                   alert.setTitle("Doctor Not Found");
                   alert.setHeaderText(null);
                   alert.setContentText("No doctor found with the provided ID.");
                   alert.showAndWait();
               }
           } catch (SQLException e) {
               e.printStackTrace();
               // إشعار بخطأ في الاتصال
               Alert alert = new Alert(AlertType.ERROR);
               alert.setTitle("Database Error");
               alert.setHeaderText("Failed to Fetch Data");
               alert.setContentText("An error occurred while fetching doctor data.");
               alert.showAndWait();
           }
       }

    @FXML
    private void updateDoctorData() {
        String doctorId = doctorIdField.getText();
        String query = "UPDATE dentists SET first_name='" + firstNameField.getText() + "', " +
                "middle_name='" + middleNameField.getText() + "', " +
                "last_name='" + finalNameField.getText() + "', " +
                "phone_number='" + numberField.getText() + "', " +
                "email='" + emailField.getText() + "', " +
                "specialization='" + specializationField.getText() + "', " +
                "salary='" + salaryField.getText() + "', " +
                "work_shift='" + workShiftComboBox.getValue() + "' " +
                "WHERE dentist_id='" + doctorId + "'";

        try (Connection conn = DriverManager.getConnection("jdbc:postgresql://localhost:5432/postgres", "postgres", "0000");
             Statement stmt = conn.createStatement()) {
            int rowsUpdated = stmt.executeUpdate(query);
            if (rowsUpdated > 0) {
                Alert alert = new Alert(AlertType.INFORMATION);
                alert.setTitle("Success");
                alert.setHeaderText(null);
                alert.setContentText("Doctor information updated successfully!");
                alert.showAndWait();
            } else {
                // إظهار إشعار بعدم العثور على الطبيب
                Alert alert = new Alert(AlertType.WARNING);
                alert.setTitle("Warning");
                alert.setHeaderText(null);
                alert.setContentText("No doctor found with the provided ID.");
                alert.showAndWait();
            }
        } catch (SQLException e) {
            e.printStackTrace();
            // إظهار إشعار في حالة حدوث خطأ
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Update Failed");
            alert.setContentText("An error occurred while updating doctor information.");
            alert.showAndWait();
        }
    }
    @FXML
    public void initialize() {
        workShiftComboBox.getItems().add("evening");
        workShiftComboBox.getItems().add("morning");
    }
    @FXML
    void handleLogButton(ActionEvent event) {
        try {
            // Load the FXML file for the signin screen
            Parent root = FXMLLoader.load(getClass().getResource("sign in.fxml"));
            Stage stage = (Stage) ok1.getScene().getWindow(); // الحصول على النافذة الحالية
            stage.setScene(new Scene(root)); // تغيير المشهد
            stage.show(); // عرض الشاشة الجديدة
            ((Stage) ok1.getScene().getWindow()).close();
        } catch (Exception e) {
            e.printStackTrace(); // طباعة أي خطأ في حالة حدوثه
        }
    }
    @FXML
    private void showAppoScreen() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(SignInApplication.class.getResource("appo.fxml"));
            Scene scene = new Scene(fxmlLoader.load(), 782, 447);
            Stage stage = new Stage();
            stage.setTitle("Appointments");
            stage.setScene(scene);
            stage.show();
            ((Stage) Appointment.getScene().getWindow()).close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    @FXML
    private void showinventoryScreen() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(SignInApplication.class.getResource("inventory.fxml"));
            Scene scene = new Scene(fxmlLoader.load(), 782, 447);
            Stage stage = new Stage();
            stage.setTitle("Appointments");
            stage.setScene(scene);
            stage.show();
            ((Stage) inventory.getScene().getWindow()).close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
   }
