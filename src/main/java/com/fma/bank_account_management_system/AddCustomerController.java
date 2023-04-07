package com.fma.bank_account_management_system;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class AddCustomerController {
    @FXML
    private TextField cusid;
    @FXML
    private TextField cusname;
    @FXML
    private TextArea cusaddress;
    @FXML
    private TextField cusphone;
    @FXML
    private Button add;
    @FXML
    private Button backButton;
    @FXML
    private Label addcusconfirm;
    @FXML
    private TextField cusemail;
    @FXML
    private TextField balance;
    @FXML
    private Button browse;
    private File file;
    @FXML
    private ImageView userimage;
    @FXML
    private TextField cuspass;
    private FileInputStream fis;
    int cf;
    @FXML
    public void ChooseFile(ActionEvent event) {
        FileChooser fc = new FileChooser();
        file = fc.showOpenDialog(null);
        if (file != null) {
            Image image = new Image(file.toURI().toString());
            userimage.setImage(image);
            cf = 1;
        } else {
            cf = 0;
        }
    }
    @FXML
    public void AddCustomer(ActionEvent event) throws SQLException, FileNotFoundException {
        try {
            if (cusid.getText().isEmpty() || cuspass.getText().isEmpty() || cusname.getText().isEmpty() || cusaddress.getText().isEmpty() || cusemail.getText().isEmpty() || cusphone.getText().isEmpty() || balance.getText().isEmpty()) {
                addcusconfirm.setText("Please Fill Up Everything");
            } else if (Integer.parseInt(balance.getText()) < 500) {
                addcusconfirm.setText("Minimum Balance Requirement Is 500 Tk.");
            } else {
                if (cf != 1) {
                    addcusconfirm.setText("Please Upload An Image To Add New Customer");
                } else if (cf == 1) {
                    try {
                        Connection con = DBConnection.ConnectToDB();
                        PreparedStatement ps = con.prepareStatement("INSERT INTO users VALUES (?,?,?,?,?,?,?,?)");
                        ps.setString(1, cusid.getText());
                        ps.setString(2, Encryption.encodeKey(cuspass.getText()));
                        ps.setString(3, cusname.getText());
                        ps.setString(4, cusaddress.getText());
                        ps.setString(5, cusemail.getText());
                        ps.setString(6, cusphone.getText());
                        ps.setInt(7, Integer.parseInt(balance.getText()));
//                        fis = new FileInputStream(file);
//                        ps.setBinaryStream(8, (InputStream) fis, (int) file.length());
                        FileInputStream fin = new FileInputStream(file);

                        ps.setBinaryStream(8, fin, (int) file.length());

                        int i = ps.executeUpdate();
                        if (i > 0) {
                            cusid.setText("");
                            cuspass.setText("");
                            cusname.setText("");
                            cusaddress.setText("");
                            cusemail.setText("");
                            cusphone.setText("");
                            balance.setText("");
                            addcusconfirm.setText("New Customer Added Successfully");
                        } else {
                            addcusconfirm.setText("Failed To Add New Customer");
                        }
                        ps.close();
                    } catch (FileNotFoundException | NumberFormatException | SQLException e) {
                        addcusconfirm.setText("Invalid Customer ID or ID Is Not Available");
                    } catch (NoSuchAlgorithmException e) {
                        throw new RuntimeException(e);
                    } catch (InvalidKeySpecException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        } catch (NumberFormatException e) {
            addcusconfirm.setText("Please Enter Everything Correctly");
        }
        cf = 0;
        //Image image = new Image("com/fma/icons/usericon1.png");
        //userimage.setImage(image);
    }
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
}
