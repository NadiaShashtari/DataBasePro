
package com.example.demo1;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;

import javax.mail.*;
import javax.mail.internet.*;
import java.util.Properties;

        public class ForgetPasswordController {

            @FXML
            private TextField emailField; // Email input field

            @FXML
            private Label messageLabel; // Label to show messages to the user

            @FXML
            public void initialize() {
                // Set up the key press event to handle Enter key
                emailField.setOnKeyPressed(event -> {
                    if (event.getCode() == KeyCode.ENTER) {
                        handleEnterKey();
                    }
                });
            }

            @FXML
            public void handleEnterKey() {
                String email = emailField.getText();

                // Validate the email format
                if (isValidEmail(email)) {
                    sendEmail(email);
                    messageLabel.setText("A message has been sent to: " + email);
                } else {
                    messageLabel.setText("Please enter a valid email address");
                }
            }

            private boolean isValidEmail(String email) {
                // Regular expression for validating email
                String emailRegex = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$";
                return email.matches(emailRegex);
            }

            private void sendEmail(String to) {
                final String from = "baraah.kharraz23@gmail.com"; // Your email address
                final String password =  "baraah231004"; // Your email password

                Properties properties = new Properties();
                properties.put("mail.smtp.auth", "true");
                properties.put("mail.smtp.starttls.enable", "true");
                properties.put("mail.smtp.host", "smtp.gmail.com"); // Your SMTP server
                properties.put("mail.smtp.port", "587"); // Port number

                Session session = Session.getInstance(properties,
                        new javax.mail.Authenticator() {
                            protected PasswordAuthentication getPasswordAuthentication() {
                                return new PasswordAuthentication(from, password);
                            }
                        });

                try {
                    Message message = new MimeMessage(session);
                    message.setFrom(new InternetAddress(from));
                    message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to)); // Use the entered email here
                    message.setSubject("Password Recovery");
                    message.setText("This is the message for password recovery.");

                    Transport.send(message);
                    System.out.println("Message sent successfully");

                } catch (MessagingException e) {
                    e.printStackTrace();
                }
            }
        }
