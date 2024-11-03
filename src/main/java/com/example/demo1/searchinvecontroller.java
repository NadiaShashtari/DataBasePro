package com.example.demo1;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
public class searchinvecontroller {

    @FXML
    private Button add;

    @FXML
    private Button back;

    @FXML
    private JFXTextField cost;

    @FXML
    private Button ok;

    @FXML
    private JFXTextField exp;

    @FXML
    private JFXTextField id;

    @FXML
    private JFXTextField name;

    @FXML
    private JFXTextField quntity;

    @FXML
    private Button show;

    @FXML
    private JFXTextField sname;

    @FXML
    private JFXTextField total;

    @FXML
    private Button updet;

    @FXML
    private void onSearchButton() {
        String enteredItemName = name.getText().trim(); // استخدام حقل name للبحث عن itemname

        // إنشاء اتصال بقاعدة البيانات
        Connection conn = connection.connect();
        if (conn != null) {
            String query = "SELECT itemid, itemname, costperitem, quantity, expirationdate, supplier_name, totalcost " +
                    "FROM inventory WHERE itemname = ?"; // استعلام للبحث عن itemname

            try (PreparedStatement pstmt = conn.prepareStatement(query)) {
                // تعيين itemname المدخل في الاستعلام
                pstmt.setString(1, enteredItemName); // استخدام setString لتعيين itemname

                // تنفيذ الاستعلام
                ResultSet rs = pstmt.executeQuery();

                // التحقق إذا كانت هناك نتائج
                if (rs.next()) {
                    // استرجاع البيانات وتعبئتها في الحقول
                    id.setText(rs.getString("itemid")); // تعيين itemid في حقل id
                    name.setText(rs.getString("itemname")); // تعيين اسم العنصر
                    cost.setText(rs.getString("costperitem")); // تعيين تكلفة العنصر
                    quntity.setText(rs.getString("quantity")); // تعيين كمية العنصر
                    exp.setText(rs.getString("expirationdate")); // تعيين تاريخ انتهاء الصلاحية
                    sname.setText(rs.getString("supplier_name")); // تعيين اسم المورد
                    total.setText(rs.getString("totalcost")); // تعيين التكلفة الإجمالية
                } else {
                    // مسح الحقول في حالة عدم العثور على نتائج
                    id.clear();
                    name.clear();
                    cost.clear();
                    quntity.clear();
                    exp.clear();
                    sname.clear();
                    total.clear();
                    System.out.println("No item found for the entered item name.");
                }
            } catch (SQLException e) {
                System.out.println("Error executing the query: " + e.getMessage());
            } catch (Exception e) {
                System.out.println("Error: " + e.getMessage());
            } finally {
                // إغلاق الاتصال
                connection.closeConnection(conn);
            }
        }
    }

    @FXML
    private void onUpdate() {
        // قراءة القيم من الحقول
        String itemIdStr = id.getText().trim();
        String itemName = name.getText().trim();
        String costPerItemStr = cost.getText().trim();
        String quantityStr = quntity.getText().trim();
        String expirationDateStr = exp.getText().trim();
        String supplierName = sname.getText().trim();

        // التحقق من صحة المدخلات
        if (itemIdStr.isEmpty() || itemName.isEmpty() || costPerItemStr.isEmpty() || quantityStr.isEmpty() || expirationDateStr.isEmpty() || supplierName.isEmpty()) {
            System.out.println("تأكد من تعبئة جميع الحقول.");
            return;
        }

        // تحويل itemId إلى عدد صحيح
        int itemId;
        try {
            itemId = Integer.parseInt(itemIdStr);
        } catch (NumberFormatException e) {
            System.out.println("رقم العنصر غير صالح.");
            return;
        }

        // تحويل الكمية إلى عدد صحيح
        int quantity;
        try {
            quantity = Integer.parseInt(quantityStr);
        } catch (NumberFormatException e) {
            System.out.println("الكمية غير صالحة.");
            return;
        }

        // تحويل التكلفة لكل عنصر إلى BigDecimal
        BigDecimal costPerItem;
        try {
            costPerItem = new BigDecimal(costPerItemStr);
        } catch (NumberFormatException e) {
            System.out.println("التكلفة لكل عنصر غير صالحة.");
            return;
        }

        // حساب التكلفة الإجمالية
        BigDecimal totalCost = costPerItem.multiply(new BigDecimal(quantity));

        // تحويل تاريخ انتهاء الصلاحية إلى java.sql.Date
        java.sql.Date expirationDate;
        try {
            expirationDate = java.sql.Date.valueOf(expirationDateStr);
        } catch (IllegalArgumentException e) {
            System.out.println("تاريخ انتهاء الصلاحية غير صالح. استخدم التنسيق YYYY-MM-DD.");
            return;
        }

        // استعلام SQL للتحديث
        String query = "UPDATE inventory SET " +
                "itemname = ?, " +
                "costperitem = ?, " +
                "quantity = ?, " +
                "expirationdate = ?, " +
                "supplier_name = ?, " +
                "totalcost = ? " +
                "WHERE itemid = ?";

        // تنفيذ التحديث
        try (Connection conn = DriverManager.getConnection("jdbc:postgresql://localhost:5432/postgres", "postgres", "0000");
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            // تعيين المعلمات
            pstmt.setString(1, itemName);
            pstmt.setBigDecimal(2, costPerItem);
            pstmt.setInt(3, quantity);
            pstmt.setDate(4, expirationDate);
            pstmt.setString(5, supplierName);
            pstmt.setBigDecimal(6, totalCost);
            pstmt.setInt(7, itemId);

            // تنفيذ التحديث
            int rowsAffected = pstmt.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("تم تحديث العنصر بنجاح.");
            } else {
                System.out.println("لم يتم العثور على عنصر بهذا المعرف.");
            }

        } catch (SQLException e) {
            System.out.println("حدث خطأ أثناء التحديث في قاعدة البيانات: " + e.getMessage());
        }
    }
    @FXML
    private void showinventoryshow() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(SignInApplication.class.getResource("show inventory.fxml"));
            Scene scene = new Scene(fxmlLoader.load(), 782, 447);
            Stage stage = new Stage();
            stage.setTitle("Appointments");
            stage.setScene(scene);
            stage.show();
            ((Stage) show.getScene().getWindow()).close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    @FXML
    private void showinventoryadd() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(SignInApplication.class.getResource("add inventory.fxml"));
            Scene scene = new Scene(fxmlLoader.load(), 782, 447);
            Stage stage = new Stage();
            stage.setTitle("Appointments");
            stage.setScene(scene);
            stage.show();
            ((Stage) add.getScene().getWindow()).close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    @FXML
    void handbackButton(ActionEvent event) {


        try {
            // Load the FXML file for the half screen
            Parent root = FXMLLoader.load(getClass().getResource("half.fxml"));
            Stage stage = (Stage) back.getScene().getWindow(); // الحصول على نافذة الزر الحالي
            stage.setScene(new Scene(root)); // تغيير المشهد
            stage.show(); // عرض المشهد الجديد
            ((Stage) back.getScene().getWindow()).close();

        } catch (Exception e) {
            e.printStackTrace(); // التعامل مع الأخطاء
        }}

    @FXML
    void handleLogButton(ActionEvent event) {
        try {
            // Load the FXML file for the signin screen
            Parent root = FXMLLoader.load(getClass().getResource("sign in.fxml"));
            Stage stage = (Stage) log.getScene().getWindow(); // الحصول على النافذة الحالية
            stage.setScene(new Scene(root)); // تغيير المشهد
            stage.show(); // عرض الشاشة الجديدة
            ((Stage) log.getScene().getWindow()).close();

        } catch (Exception e) {
            e.printStackTrace(); // طباعة أي خطأ في حالة حدوثه
        }
    }
    @FXML
    private Button log;

}