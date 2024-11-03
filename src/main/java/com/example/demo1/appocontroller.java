package com.example.demo1;
import javafx.animation.TranslateTransition;
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
import javafx.event.ActionEvent;
import javafx.util.Duration;

import java.io.IOException;





public class appocontroller {



        @FXML
        private Button Appointment;

        @FXML
        private Button Doctor;

        @FXML
        private Button Search;

        @FXML
        private Button add;

        @FXML
        private Button expenses;

        @FXML
        private Pane gray;

        @FXML
        private AnchorPane imge;

        @FXML
        private Button inventory;

        @FXML
        private Label label;

        @FXML
        private Button laps;

        @FXML
        private Pane lll;

        @FXML
        private Pane p;

        @FXML
        private Pane pan;

        @FXML
        private Pane pane;

        @FXML
        private Button patient;

        @FXML
        private Button show;
        @FXML
        private Button ok1;
        @FXML
        private ImageView view;
        @FXML
        public void initialize() {

                Search.setOnAction(event -> showSearchScreen());
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
        private void showSearchScreen() {
                try {
                        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("SEARCH.fxml"));
                        Parent root = fxmlLoader.load();
                        Stage stage = new Stage();
                        stage.setScene(new Scene(root));
                        stage.setTitle("Search Screen");
                        stage.show();
                        ((Stage) Search.getScene().getWindow()).close();
                } catch (Exception e) {
                        e.printStackTrace();
                }
        }
        @FXML
        private void showShowScreen(ActionEvent event) throws IOException {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("show all.fxml"));
                Parent showRoot = loader.load();
                Stage stage = new Stage();
                stage.setScene(new Scene(showRoot));
                stage.setTitle("Show Screen");
                stage.show();
                ((Stage) show.getScene().getWindow()).close();
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


