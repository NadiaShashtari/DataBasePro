package com.example.demo1;
import javafx.animation.TranslateTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.util.Duration;

public class inventorycontroller {

    @FXML
    private Button Search;

    @FXML
    private Button add;

    @FXML
    private AnchorPane an;

    @FXML
    private AnchorPane an2;
    @FXML
    private Button log;
    @FXML
    private Pane b;

    @FXML
    private Pane ba;

    @FXML
    private Pane bane;

    @FXML
    private ImageView ee;

    @FXML
    private Label eee;

    @FXML
    private Pane pane;

    @FXML
    private Pane pano;

    @FXML
    private Button show;
    @FXML
    private Button appo;
    @FXML
    private Button doctor;
    @FXML
    public void initialize() {


        animateButton(show, -300, 0);

        // تحريك AddButton1 من اليسار إلى موضعه الأصلي
        animateButton(add, -300, 0);

        // تحريك SearchButton1 من اليسار إلى موضعه الأصلي
        animateButton(Search, -300, 0);
    }

    private void animateButton(Button button, double fromX, double fromY) {
        // إعداد موضع البداية للزر ليبدأ من اليسار خارج الشاشة
        button.setTranslateX(fromX);
        button.setTranslateY(fromY);

        // إنشاء حركة الانتقال
        TranslateTransition transition = new TranslateTransition(Duration.seconds(1), button);
        transition.setToX(0); // العودة إلى الموضع الأصلي
        transition.setToY(0);
        transition.setAutoReverse(false);

        // تشغيل الحركة
        transition.play();

    }
    @FXML
    private void showinventorysearch() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(SignInApplication.class.getResource("search inventory.fxml"));
            Scene scene = new Scene(fxmlLoader.load(), 782, 447);
            Stage stage = new Stage();
            stage.setTitle("Appointments");
            stage.setScene(scene);
            stage.show();
            ((Stage) Search.getScene().getWindow()).close(); // Close the current window
        } catch (Exception e) {
            e.printStackTrace();
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
            ((Stage) show.getScene().getWindow()).close(); // Close the current window
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
            ((Stage) add.getScene().getWindow()).close(); // Close the current window
        } catch (Exception e) {
            e.printStackTrace();
        }
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
            ((Stage) doctor.getScene().getWindow()).close();
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
            ((Stage) appo.getScene().getWindow()).close();
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
            ((Stage) log.getScene().getWindow()).close(); // Close the current window
        } catch (Exception e) {
            e.printStackTrace(); // طباعة أي خطأ في حالة حدوثه
        }
    }
}
