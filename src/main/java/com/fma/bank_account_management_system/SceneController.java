package com.fma.bank_account_management_system;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.event.ActionEvent;


import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class SceneController {
    @FXML
    private TextField useridtf;
    @FXML
    private Label mssg;
    @FXML
    private Label title;
    @FXML
    private Button loginb;
    @FXML
    private PasswordField passwordtf;
    @FXML
    private RadioButton userrb;
    @FXML
    private ToggleGroup UserOrAdmin;
    @FXML
    private RadioButton adminrb;
    @FXML
    private void Login(ActionEvent event) throws SQLException, IOException, NoSuchAlgorithmException, InvalidKeySpecException {
        Connection con = DBConnection.ConnectToDB();
        PreparedStatement ps = null;
        ResultSet rs = null;
        if (userrb.isSelected()) {
            ps = con.prepareStatement("SELECT * FROM users WHERE id = ? and password = ?");
            ps.setString(1, useridtf.getText());
            ps.setString(2, Encryption.encodeKey(passwordtf.getText()));
            rs = ps.executeQuery();
            if (rs.next()) {
                System.out.println("User Found");
                Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(getClass().getResource("UserPage.fxml"));
                loader.load();
                Parent root = loader.getRoot();
                UserPageController upc = loader.getController();
                upc.GetUserID(useridtf.getText(), rs.getString("name"));
                stage.setTitle("User Page");
                Scene scene = new Scene(root);
                stage.setScene(scene);
                stage.show();
                mssg.setText("");
                } else {
                    mssg.setText("Wrong Password Or UserID");
                }
                ps.close();
                rs.close();
            } else if (adminrb.isSelected()) {
                ps = con.prepareStatement("SELECT * FROM admins WHERE id = ? and password = ?");
                ps.setString(1, useridtf.getText());
                ps.setString(2, Encryption.encodeKey(passwordtf.getText()));
                rs = ps.executeQuery();
                if (rs.next()) {
                    Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                    FXMLLoader loader = new FXMLLoader();
                    loader.setLocation(getClass().getResource("AdminPage.fxml"));
                    loader.load();
                    Parent root = loader.getRoot();
                    AdminPageController apc = loader.getController();
                    apc.GetAdminID(useridtf.getText());
                    stage.setTitle("Admin Page");
                    Scene scene = new Scene(root);
                    stage.setScene(scene);
                    stage.show();
                    mssg.setText("");
                } else {
                    mssg.setText("Wrong Password Or AdminID");
                }
                ps.close();
                rs.close();
            }
    }

}