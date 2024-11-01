package com.example.demo1;
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
        }


                @FXML
                private void showAddScreen(ActionEvent event) throws IOException {
                        FXMLLoader loader = new FXMLLoader(getClass().getResource("Add new Appoitment.fxml"));
                        Parent addRoot = loader.load();
                        Stage stage = new Stage();
                        stage.setScene(new Scene(addRoot));
                        stage.setTitle("Add Appointment");
                        stage.show();
                }

        @FXML
        void handleLogButton(ActionEvent event) {
                try {
                        // Load the FXML file for the signin screen
                        Parent root = FXMLLoader.load(getClass().getResource("sign in.fxml"));
                        Stage stage = (Stage) ok1.getScene().getWindow(); // الحصول على النافذة الحالية
                        stage.setScene(new Scene(root)); // تغيير المشهد
                        stage.show(); // عرض الشاشة الجديدة
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
                } catch (Exception e) {
                        e.printStackTrace();
                }
        }

}


