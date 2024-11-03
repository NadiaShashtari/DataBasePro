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

public class showallcontroller {
    @FXML
    private Button d;
    @FXML
    private Button add;

    @FXML
    private AnchorPane ancho;

    @FXML
    private AnchorPane anchore;

    @FXML
    private Button back;

    @FXML
    private TableColumn<Map<String, Object>, String> f;

    @FXML
    private TableColumn<Map<String, Object>, String> five;

    @FXML
    private TableColumn<Map<String, Object>, String> foe;

    @FXML
    private ImageView imge;

    @FXML
    private Label label;

    @FXML
    private Pane pa;

    @FXML
    private Pane paa;

    @FXML
    private Pane panee;

    @FXML
    private TableColumn<Map<String, Object>, String> s;

    @FXML
    private Button search;

    @FXML
    private Button show;

    @FXML
    private Button show1;

    @FXML
    private TableColumn<Map<String, Object>, String> six;

    @FXML
    private TableView<Map<String, Object>> table;

    @FXML
    private TableColumn<Map<String, Object>, String> th;

    @FXML
    private Pane two;
    @FXML
    private Button ok1;

    @FXML
    private TableColumn<Map<String, Object>, CheckBox> Select;

    // private ObservableList<Map<String, Object>> appointmentList = FXCollections.observableArrayList();

    @FXML
    public void initialize() {
        // إعداد الأعمدة لعرض البيانات كـ String من القائمة
        s.setCellValueFactory(param -> new SimpleObjectProperty((String) param.getValue().get("id"))); // appointmentid
        th.setCellValueFactory(param -> new SimpleObjectProperty((String) param.getValue().get("date")));
        foe.setCellValueFactory(param -> new SimpleObjectProperty((String) param.getValue().get("time"))); // appointmenttime
        five.setCellValueFactory(param -> new SimpleObjectProperty((String) param.getValue().get("followUp"))); // followuprequired
        six.setCellValueFactory(param -> new SimpleObjectProperty((String) param.getValue().get("treatment")));

        Select.setCellValueFactory(param -> {
            Map<String, Object> row = param.getValue();
            CheckBox checkBox = new CheckBox();
            row.put("selectCheckBox", checkBox); // حفظ CheckBox في الـ Map الخاص بالصف
            return new SimpleObjectProperty<>(checkBox);
        });
        show.setOnAction(event -> loadDataFromDatabase());
        d.setOnAction(e -> deleteSelectedRows());
    }

    @FXML
    private void loadDataFromDatabase() {


        ObservableList<Map<String, Object>> appointmentList = FXCollections.observableArrayList(); // إنشاء قائمة جديدة لتخزين البيانات
        //appointmentList.clear();
        try {
            Connection conn = DatabaseConnection.getConnection(); // الاتصال بقاعدة البيانات
            String query = "SELECT appointmentid, appointmentdate, appointmenttime, followuprequired, treatmentdetails FROM appointments";
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(query);

            while (rs.next()) {
                // إنشاء Map لتمثيل كل صف
                Map<String, Object> row = new HashMap<>();

                row.put("id", rs.getString("appointmentid"));           // تعيين قيمة appointmentid
                row.put("date", rs.getString("appointmentdate"));       // تعيين قيمة appointmentdate
                row.put("time", rs.getString("appointmenttime"));       // تعيين قيمة appointmenttime
                row.put("followUp", rs.getString("followuprequired"));  // تعيين قيمة followuprequired
                row.put("treatment", rs.getString("treatmentdetails")); // تعيين قيمة treatmentdetails
                row.put("selectCheckBox", new CheckBox());              // إضافة CheckBox للعمود select

                // إضافة الـ Map إلى قائمة المواعيد
                appointmentList.add(row);
            }
            table.setItems(appointmentList); // تعيين القائمة في الجدول

            rs.close();
            stmt.close();
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void handbackButton(ActionEvent event) {


        try {
            // Load the FXML file for the half screen
            Parent root = FXMLLoader.load(getClass().getResource("appo.fxml"));
            Stage stage = (Stage) back.getScene().getWindow(); // الحصول على نافذة الزر الحالي
            stage.setScene(new Scene(root)); // تغيير المشهد
            stage.show(); // عرض المشهد الجديد
            ((Stage) back.getScene().getWindow()).close();
        } catch (Exception e) {
            e.printStackTrace(); // التعامل مع الأخطاء
        }
    }

    @FXML
    private void showAddScreen(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("Add new Appoitment.fxml"));
        Parent addRoot = loader.load();
        Stage stage = new Stage();
        stage.setScene(new Scene(addRoot));
        stage.setTitle("Add Appointment");
        stage.show();
        ((Stage) add.getScene().getWindow()).close();

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
    private void showSearchScreen() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("SEARCH.fxml"));
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
    private void deleteSelectedRows() {
        ObservableList<Map<String, Object>> items = table.getItems();
        // إنشاء قائمة لتخزين معرفات المواعيد المحذوفة
        List<Integer> idsToDelete = new ArrayList<>();

        // تحديد الصفوف المحددة وإضافتها إلى قائمة الحذف
        items.removeIf(row -> {
            CheckBox checkBox = (CheckBox) row.get("selectCheckBox");
            if (checkBox != null && checkBox.isSelected()) {
                idsToDelete.add(Integer.parseInt((String) row.get("id"))); // تحويل المعرف إلى Integer
                return true; // حذف الصف من TableView
            }
            return false; // عدم حذف الصف
        });

        // حذف الصفوف من قاعدة البيانات
        if (!idsToDelete.isEmpty()) {
            try {
                Connection conn = DatabaseConnection.getConnection();
                String sql = "DELETE FROM appointments WHERE appointmentid = ?";
                PreparedStatement pstmt = conn.prepareStatement(sql);

                for (Integer id : idsToDelete) {
                    pstmt.setInt(1, id); // استخدام setInt بدلاً من setString
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
}