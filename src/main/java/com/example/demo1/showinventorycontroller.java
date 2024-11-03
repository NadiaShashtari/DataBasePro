package com.example.demo1;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.scene.control.CheckBox;
import javafx.scene.control.cell.CheckBoxTableCell;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;



public class showinventorycontroller {

    @FXML
    private Button add;

    @FXML
    private Button back;

    @FXML
    private Button delete;

    @FXML
    private TableColumn<Map<String, Object>, String>first;

    @FXML
    private TableColumn<Map<String, Object>, String> fivth;

    @FXML
    private TableColumn<Map<String, Object>, String> fourth;
    @FXML
    private TableColumn<Map<String, Object>, CheckBox> select;

    @FXML
    private Button search;

    @FXML
    private TableColumn <Map<String, Object>, String> second;

    @FXML
    private TableColumn<Map<String, Object>, String> seventh;

    @FXML
    private Button show;

    @FXML
    private Button showall;
    @FXML
    private Button log;
    @FXML
    private TableColumn<Map<String, Object>, String> six;

    @FXML
    private TableView<Map<String, Object>> table;

    @FXML
    private TableColumn<Map<String, Object>, String> third;
    @FXML
    public void initialize() {
        // إعداد الأعمدة لعرض البيانات من قاعدة البيانات
        first.setCellValueFactory(param -> new SimpleObjectProperty<>((String) param.getValue().get("itemid")));
        second.setCellValueFactory(param -> new SimpleObjectProperty<>((String) param.getValue().get("itemname")));
        third.setCellValueFactory(param -> new SimpleObjectProperty<>((String) param.getValue().get("quantity")));
        fourth.setCellValueFactory(param -> new SimpleObjectProperty<>((String) param.getValue().get("costperitem")));
        fivth.setCellValueFactory(param -> new SimpleObjectProperty<>((String) param.getValue().get("totalcost")));
        six.setCellValueFactory(param -> new SimpleObjectProperty<>((String) param.getValue().get("expirationdate")));
        seventh.setCellValueFactory(param -> new SimpleObjectProperty<>((String) param.getValue().get("supplier_name")));

        select.setCellValueFactory(param -> {
            Map<String, Object> row = param.getValue();
            CheckBox checkBox = new CheckBox();
            row.put("selectCheckBox", checkBox); // حفظ CheckBox في الـ Map الخاص بالصف
            return new SimpleObjectProperty<>(checkBox);
        });

        show.setOnAction(event -> loadDataFromDatabase());
        delete.setOnAction(e -> deleteSelectedRows());
    }

    @FXML
    private void loadDataFromDatabase() {
        ObservableList<Map<String, Object>> inventoryList = FXCollections.observableArrayList();

        try {
            Connection conn = DatabaseConnection.getConnection(); // الاتصال بقاعدة البيانات
            String query = "SELECT itemid, itemname, quantity, costperitem, totalcost, supplier_name, expirationdate FROM inventory";
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(query);

            while (rs.next()) {
                Map<String, Object> row = new HashMap<>();
                row.put("itemid", rs.getString("itemid"));
                row.put("itemname", rs.getString("itemname"));
                row.put("quantity", rs.getString("quantity"));
                row.put("costperitem", rs.getString("costperitem"));
                row.put("totalcost", rs.getString("totalcost"));
                row.put("supplier_name", rs.getString("supplier_name"));
                row.put("expirationdate", rs.getString("expirationdate"));

                inventoryList.add(row);
            }

            table.setItems(inventoryList); // تعيين القائمة في الجدول

            rs.close();
            stmt.close();
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void deleteSelectedRows() {
        ObservableList<Map<String, Object>> items = table.getItems();
        List<String> idsToDelete = new ArrayList<>();

        items.removeIf(row -> {
            CheckBox checkBox = (CheckBox) row.get("selectCheckBox");
            if (checkBox != null && checkBox.isSelected()) {
                idsToDelete.add((String) row.get("itemid")); // جلب itemid لحذفه من قاعدة البيانات
                return true; // حذف الصف من TableView
            }
            return false; // عدم حذف الصف
        });

        if (!idsToDelete.isEmpty()) {
            try {
                Connection conn = DatabaseConnection.getConnection();
                String sql = "DELETE FROM inventory WHERE itemid = ?";
                PreparedStatement pstmt = conn.prepareStatement(sql);

                for (String id : idsToDelete) {
                    pstmt.setInt(1, Integer.parseInt(id)); // استخدام setString لأن itemid هو نصي
                    pstmt.addBatch(); // إضافة الاستعلام إلى الدفعة
                }

                pstmt.executeBatch(); // تنفيذ الدفعة
                pstmt.close();
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
    @FXML
    private void showSearchScreen() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("search inventory.fxml"));
            Parent root = fxmlLoader.load();
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setTitle("Search Screen");
            stage.show();
            ((Stage) search.getScene().getWindow()).close();
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
}