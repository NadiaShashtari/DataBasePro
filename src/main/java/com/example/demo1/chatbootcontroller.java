package com.example.demo1;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollBar;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javafx.stage.Stage;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;



public class chatbootcontroller {
    @FXML
    private JFXButton button;

    @FXML
    private JFXTextField text;

    @FXML
    private VBox vbox;
    @FXML
    private Button ok1;


    @FXML
    private ScrollPane scroll;

    @FXML
    public void initialize() {
        // تعيين حدث الضغط على الزر
        button.setOnAction(event -> sendMessage());
    }

    private void sendMessage() {
        String userInput = text.getText().trim(); // الحصول على نص المستخدم
        if (!userInput.isEmpty()) {
            // إنشاء فقاعات نص المستخدم
            HBox userBubble = createMessageBubble("You: " + userInput, Color.LIGHTBLUE);
            vbox.getChildren().add(userBubble); // إضافة فقاعات المستخدم إلى VBox

            // محاكاة رد البوت
            String botResponse = getBotResponse(userInput);
            HBox botBubble = createMessageBubble("Bot: " + botResponse, Color.LIGHTGREEN);
            vbox.getChildren().add(botBubble); // إضافة فقاعات البوت إلى VBox

            text.clear(); // مسح حقل النص بعد الإرسال
            scroll.setVvalue(1.0);
        }
    }

    private HBox createMessageBubble(String message, Color color) {
        // إنشاء دائرة كخلفية للفقاعة
        Circle bubbleBackground = new Circle(500); // يمكنك تعديل الحجم حسب الحاجة
        bubbleBackground.setFill(color); // تعيين لون الخلفية

        // إنشاء نص الرسالة
        Text messageText = new Text(message);
        messageText.setFill(Color.BLACK); // لون النص أبيض

        // استخدام TextFlow لتجميع النص
        TextFlow textFlow = new TextFlow(messageText);
        textFlow.setPadding(new Insets(10)); // إضافة حواف للنص

        // تعيين عرض TextFlow
        textFlow.setMinWidth(200); // تعيين عرض الحد الأدنى

        // تحديث حجم الفقاعة بناءً على النص
        double textWidth = textFlow.getLayoutBounds().getWidth();
        double textHeight = textFlow.getLayoutBounds().getHeight();

        // تحديث حجم الدائرة بناءً على النص
        double radius = Math.max(textWidth, textHeight) / 2 + 20; // إضافة مساحة إضافية
        bubbleBackground.setRadius(radius); // تعيين نصف القطر

        // إنشاء HBox لتجميع الدائرة والنص
        HBox bubbleContainer = new HBox();
        bubbleContainer.getChildren().addAll(bubbleBackground, textFlow);
        bubbleContainer.setSpacing(0); // المسافة بين الدائرة والنص

        return bubbleContainer;
    }
    private String getBotResponse(String input) {
        boolean isArabic = input.matches(".*[\\u0600-\\u06FF].*"); // تحقق مما إذا كانت الرسالة تحتوي على أحرف عربية

        if (isArabic) {
            // الردود باللغة العربية
            if (input.equalsIgnoreCase("مرحبا")) {
                return "مرحبًا! كيف يمكنني مساعدتك؟";
            } else if (input.equalsIgnoreCase("كيف حالك؟")) {
                return "أنا مجرد بوت، لكن شكرًا على سؤالك!";
            } else if (input.equalsIgnoreCase("اعرض المواعيد")) {
                return getAppointments(); // سيتم جلب المواعيد من قاعدة البيانات كما هي، ويمكنك تعديل `getAppointments` لإرجاع النتائج بالعربية
            } else if (input.equalsIgnoreCase("اعرض المصاريف")) {
                return getExpenses();
            } else if (input.equalsIgnoreCase("ما اسمك؟")) {
                return "أنا مساعدك الافتراضي، هنا للمساعدة!";
            } else if (input.equalsIgnoreCase("قل لي نكتة")) {
                return "لماذا وضع الفيل نظارة؟ لأنه أراد أن يرى النملة بوضوح!";
            } else {
                return "عذرًا، لا أستطيع الرد على هذا.";
            }
        } else {
            // الردود باللغة الإنجليزية
            if (input.equalsIgnoreCase("hello")) {
                return "Hi there! How can I help you?";
            } else if (input.equalsIgnoreCase("how are you?")) {
                return "I'm just a bot, but thanks for asking!";
            } else if (input.equalsIgnoreCase("show appointments")) {
                return getAppointments();
            } else if (input.equalsIgnoreCase("show expenses")) {
                return getExpenses();
            } else if (input.equalsIgnoreCase("what's your name?")) {
                return "I am your virtual assistant here to help you!";
            } else if (input.equalsIgnoreCase("can you tell me a joke?")) {
                return "Why did the scarecrow win an award? Because he was outstanding in his field!";
            } else {
                return "I'm not sure how to respond to that.";
            }
        }
    }


    private final String DB_URL = "jdbc:postgresql://localhost:5432/postgres"; // استبدل باسم قاعدة البيانات الخاصة بك
    private final String USER = "postgres"; // استبدل باسم المستخدم الخاص بك
    private final String PASS = "0000";

    private String getAppointments() {
        StringBuilder appointments = new StringBuilder();
        String query = "SELECT * FROM appointments"; // تأكد من أن الجدول موجود
        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            while (rs.next()) {
                String patientID = rs.getString("patientid"); // تأكد من أن هذا هو اسم العمود الصحيح
                String appointmentDate = rs.getString("appointmentdate"); // تأكد من أن هذا هو اسم العمود الصحيح
                String appointmentTime = rs.getString("appointmenttime"); // تأكد من أن هذا هو اسم العمود الصحيح
                String treatmentDetails = rs.getString("treatmentdetails");
                // بناء النص الذي سيتم إضافته إلى منطقة النص
                appointments
                        .append(", Patient ID: ").append(patientID)
                        .append(", Date: ").append(appointmentDate)
                        .append(", Time: ").append(appointmentTime)
                        .append(", Treatment Details: ").append(treatmentDetails).append("\n");
            }
        } catch (Exception e) {
            e.printStackTrace();
            return "Failed to retrieve appointments.";
        }
        return appointments.length() > 0 ? appointments.toString() : "No appointments found."; // إضافة return هنا
    }
    private String getExpenses() {
        StringBuilder expenses = new StringBuilder();
        String query = "SELECT * FROM expenses"; // استعلام لجلب البيانات من جدول النفقات
        String DB_URL = "jdbc:postgresql://localhost:5432/postgres"; // استبدل بقاعدة البيانات الخاصة بك
        String USER = "postgres"; // استبدل باسم المستخدم الخاص بك
        String PASS = "0000"; // استبدل بكلمة المرور الخاصة بك

        double totalAmount = 0; // متغير لتخزين إجمالي النفقات

        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            while (rs.next()) {
                String expenseID = rs.getString("expenseid");
                String expensetype = rs.getString("expensetype");
                double amount = rs.getDouble("amount"); // استخدم getDouble لجلب المبلغ كعدد عشري
                String paymentmethod = rs.getString("paymentmethod");

                expenses
                        .append("Expense ID: ").append(expenseID)
                        .append(", Expensetype: ").append(expensetype)
                        .append(", Amount: ").append(amount)
                        .append(", Pay method: ").append(paymentmethod)
                        .append("\n");

                totalAmount += amount; // إضافة المبلغ إلى إجمالي النفقات
            }
        } catch (Exception e) {
            e.printStackTrace();
            return "Failed to retrieve expenses.";
        }

        // إضافة إجمالي النفقات إلى النتيجة النهائية
        expenses.append("Total Amount: ").append(totalAmount).append("\n");

        return expenses.length() > 0 ? expenses.toString() : "No expenses found.";
    }
    @FXML
    void handlogButton(ActionEvent event) {


        try {
            // Load the FXML file for the half screen
            Parent root = FXMLLoader.load(getClass().getResource("half.fxml"));
            Stage stage = (Stage) ok1.getScene().getWindow(); // الحصول على نافذة الزر الحالي
            stage.setScene(new Scene(root)); // تغيير المشهد
            stage.show(); // عرض المشهد الجديد
            ((Stage) ok1.getScene().getWindow()).close();

        } catch (Exception e) {
            e.printStackTrace(); // التعامل مع الأخطاء
        }}
    }



