package com.example.demo1;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.animation.TranslateTransition;
import javafx.util.Duration;


public class halfcontroller {
    @FXML
    private AnchorPane Anchor;

    @FXML
    private Button Appointment;

    @FXML
    private Button Doctor;

    @FXML
    private Button Expenses;

    @FXML
    private Button Inventory;

    @FXML
    private Button Laps;

    @FXML
    private Button Patient;

    @FXML
    private Pane buttons;

    @FXML
    private Label dental;

    @FXML
    private ImageView img;

    @FXML
    private Pane pa;
    @FXML
    private Button log;
    @FXML
    private Button button;

    @FXML
    private Pane pane;
    @FXML

    public void initialize() {
        Doctor.setOnAction(event -> showDentiClinic());
        Appointment.setOnAction(event -> showAppoScreen()); // إضافة الحدث الجديد

            animateButton(Appointment, 300, 0);       // تحريك الزر من اليمين
            animateButton(Doctor, -300, 0);   // تحريك الزر من اليسار
            animateButton(Expenses, 0, 300);  // تحريك الزر من الأسفل
            animateButton(Inventory, 0, -300); // تحريك الزر من الأعلى
            animateButton(Laps, 300, 0);       // تحريك الزر من اليمين
            animateButton(log, -300, 0);   // تحريك الزر من اليسار
            animateButton(Patient, 0, 300);
        animateButton(button, 0, 300);
        }

        private void animateButton(Button button, double fromX, double fromY) {
            // إعداد الحركة من نقطة البداية خارج الشاشة
            button.setTranslateX(fromX);
            button.setTranslateY(fromY);

            // إنشاء الحركة الانتقالية
            TranslateTransition transition = new TranslateTransition(Duration.seconds(1), button);
            transition.setToX(0); // العودة إلى الموضع الأصلي
            transition.setToY(0); // العودة إلى الموضع الأصلي
            transition.setAutoReverse(false);

            // تشغيل الحركة
            transition.play();
        }

    @FXML
    private void showDentiClinic() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(SignInApplication.class.getResource("dental_clinic.fxml"));
            Scene scene = new Scene(fxmlLoader.load(), 782, 447);
            Stage stage = new Stage();
            stage.setTitle("Welcome To Dental Clinic!");
            stage.setScene(scene);
            stage.show();
            ((Stage) Doctor.getScene().getWindow()).close();
        } catch (Exception e) {
            e.printStackTrace();
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
    private void showinventoryScreen() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(SignInApplication.class.getResource("inventory.fxml"));
            Scene scene = new Scene(fxmlLoader.load(), 782, 447);
            Stage stage = new Stage();
            stage.setTitle("Appointments");
            stage.setScene(scene);
            stage.show();
            ((Stage) Inventory.getScene().getWindow()).close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    @FXML
    private void showchat() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(SignInApplication.class.getResource("chatboot.fxml"));
            Scene scene = new Scene(fxmlLoader.load(), 782, 447);
            Stage stage = new Stage();
            stage.setTitle("Welcome To Dental Clinic!");
            stage.setScene(scene);
            stage.show();
            ((Stage) button.getScene().getWindow()).close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}



