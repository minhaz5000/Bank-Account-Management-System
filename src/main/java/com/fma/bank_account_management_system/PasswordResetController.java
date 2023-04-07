package com.fma.bank_account_management_system;


import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;
import java.util.Random;

public class PasswordResetController {
    @FXML
    private Button backButton;
    @FXML
    private TextField cusid;
    @FXML
    private TextField cusname;
    @FXML
    private TextArea confirmation;
    @FXML
    public void BackButton(ActionEvent event) throws IOException {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("AdminPage.fxml"));
        loader.load();
        Parent root = loader.getRoot();
        stage.setTitle("Admin Page");
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
    String Email, Password;
    @FXML
    public void PassReset(ActionEvent event) throws SQLException {
        Random rnd = new Random();
        int pass = 1000 + rnd.nextInt(9000);
        Password = "Your New PassWord Is " + pass;
        if (cusid.getText().isEmpty()) {
            confirmation.setText("Please Fill Up The ID First");
        } else {
            try {
                Connection con = DBConnection.ConnectToDB();
                assert con != null;
                PreparedStatement ps = con.prepareStatement("SELECT * FROM users Where id = ?");
                ps.setString(1, cusid.getText());
                ResultSet rs = ps.executeQuery();
                if (rs.next()) {
                    Email = rs.getString("email");
                    PreparedStatement pss = con.prepareStatement("UPDATE users SET password = ? WHERE id = '" + cusid.getText() + "'");
                    pss.setString(1, Encryption.encodeKey(Integer.toString(pass)));
                    int i = pss.executeUpdate();
                    if (i > 0) {
                        confirmation.setText("A New Random Password Is Set To This ID"+"\n");
                        final String username = "outsiderparadise37@gmail.com"; // you have to change your security level
                        final String password = "uoodifobcjztanbi";
                        Properties props = System.getProperties();
                        props.put("mail.smtp.auth", "true");
                        props.put("mail.smtp.starttls.enable", "true");
                        props.put("mail.smtp.host", "smtp.gmail.com");
                        props.put("mail.smtp.port", "587");
                        props.put("mail.smtp.ssl.trust", "smtp.gmail.com");

                        Session session = Session.getInstance(props, new javax.mail.Authenticator() {
                            protected PasswordAuthentication getPasswordAuthentication() {
                                return new PasswordAuthentication(username, password);
                            }
                        });
                        try {
                            Message message = new MimeMessage(session);
                            message.setFrom(new InternetAddress("outsiderparadise37@gmail.com"));
                            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(Email));
                            message.setSubject("Password Reset");
                            message.setText(Password);
                            Transport.send(message);
                            confirmation.appendText("This New Password Has Been Sent To Customer's Email");
                        } catch (MessagingException e) {
                            confirmation.setText("Could Not Send The Email , Please Contract With An Admin "+"\n"+ "New Password :"+ pass);
                        }
                    } else {
                        confirmation.setText("Failed To Change Password , Please Check Customer ID");
                    }
                } else {
                    confirmation.setText("Please Enter A Valid Customer ID");
                }
                ps.close();
                rs.close();
            } catch (NumberFormatException | SQLException e) {
                confirmation.setText("DataBase Error Or Invalid Number Format");
            } catch (NoSuchAlgorithmException e) {
                throw new RuntimeException(e);
            } catch (InvalidKeySpecException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
