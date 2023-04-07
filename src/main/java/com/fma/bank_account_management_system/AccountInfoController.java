package com.fma.bank_account_management_system;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AccountInfoController {

    String UserID;
    @FXML
    private TextField uid;
    @FXML
    private TextField uname;
    @FXML
    private TextArea uaddress;
    @FXML
    private TextField uemail;
    @FXML
    private TextField uphone;
    @FXML
    private TextField ubalance;
    @FXML
    private Label welcome;
    @FXML
    private PasswordField oldpass;
    @FXML
    private PasswordField newpass;
    @FXML
    private PasswordField passretype;
    @FXML
    private Label changepassconf;
    @FXML
    private TextArea quotediscp;
    @FXML
    private TextArea quotedisai;
    @FXML
    private Button backButton;

    @FXML
    public void BackButton(ActionEvent event) throws IOException {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("UserPage.fxml"));
        loader.load();
        Parent root = loader.getRoot();
        stage.setTitle("User Page");
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public void getUserID(String Id) {
        UserID = Id;
    }

//    public void StqPasswordChangePage() {
//        Quotes qt = new Quotes();
//        String quote = qt.returnQuotes();
//        quotediscp.setText(quote);
//    }
//
//    public void StqAccountInfo() {
//        Quotes qt = new Quotes();
//        String quote = qt.returnQuotes();
//        quotedisai.setText(quote);
//    }

    public void AccountInfo(ActionEvent event) throws SQLException {
        Connection con = DBConnection.ConnectToDB();
        PreparedStatement ps = null;
        ResultSet rs = null;
        ps = con.prepareStatement("SELECT * FROM users WHERE id = ?");
        ps.setString(1, UserID);
        rs = ps.executeQuery();
        while (rs.next()) {
            uname.setText(rs.getString("name"));
            uaddress.setText(rs.getString("address"));
            uemail.setText(rs.getString("email"));
            uphone.setText(rs.getString("phone"));
            ubalance.setText(rs.getString("balance"));
        }
        ps.close();
        rs.close();
    }

    public void ChangePassword(ActionEvent event) throws SQLException, NoSuchAlgorithmException, InvalidKeySpecException {
        if (!newpass.getText().equals(passretype.getText())) {
            changepassconf.setText("Password Confirmation Failed");
            passretype.setText("");
            passretype.setStyle("-fx-border-color:red;-fx-border-width:2;-fx-border-radius:20;-fx-background-radius:22;");
        } else if (oldpass.getText().isEmpty() || newpass.getText().isEmpty() || passretype.getText().isEmpty()) {
            changepassconf.setText("Please Fill Up Everything Correctly.");
        } else {
            Connection con = DBConnection.ConnectToDB();
            PreparedStatement ps = con.prepareStatement("UPDATE users SET password = ? WHERE id ='" + UserID + "' AND password ='" + Encryption.encodeKey(oldpass.getText()) + "'");
            ps.setString(1, Encryption.encodeKey(newpass.getText()));
            int i = ps.executeUpdate();
            if (i > 0) {
                changepassconf.setText("Password Changed.");
            } else {
                changepassconf.setText("Wrong Password.");
            }
            oldpass.setText("");
            newpass.setText("");
            passretype.setText("");
            passretype.setStyle("-fx-border-color: #3b5998;-fx-border-width:2;-fx-border-radius:20;-fx-background-radius:22;");
            ps.close();
        }
    }
}