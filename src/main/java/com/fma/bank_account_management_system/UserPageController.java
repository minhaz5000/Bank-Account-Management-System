package com.fma.bank_account_management_system;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;

import java.io.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserPageController {
    @FXML
    private Button backButton;
    @FXML
    private Button logoutButton;
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
    @FXML
    public void LogoutButton(ActionEvent event) throws IOException {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("Scene.fxml"));
        loader.load();
        Parent root = loader.getRoot();
        stage.setTitle("Login");
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
    @FXML
    private Label welcome;
    @FXML
    private ImageView userimage;
    @FXML
    private Label username;
    @FXML
    private Label userid;

    static String UserID, UserName;

    public void GetUserID(String id, String Name) throws SQLException, IOException {
        UserID = id;
        UserName = Name;
        welcome.setText(Name);
        Connection con = DBConnection.ConnectToDB();
        PreparedStatement ps = con.prepareStatement("SELECT * FROM users WHERE id = ?");
        ps.setString(1, id);
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            username.setText(rs.getString("name"));
            userid.setText(rs.getString("id"));
            InputStream is = rs.getBinaryStream("image");
            OutputStream os = new FileOutputStream(new File("userimage.jpeg"));
            byte[] content = new byte[1024];
            int s = 0;
            while((s= is.read(content))!= -1) {
                os.write(content, 0, s);
            }
            Image image = new Image("file:userimage.jpeg");
            userimage.setImage(image);
            userimage.setFitWidth(248);
            userimage.setFitHeight(186);
            Circle clip = new Circle(93,93,93);
            userimage.setClip(clip);
        }
        ps.close();
        rs.close();
    }

    public void CheckBalance(ActionEvent event) throws SQLException, IOException {
        Connection con = DBConnection.ConnectToDB();
        PreparedStatement ps = null;
        ResultSet rs = null;
        ps = con.prepareStatement("SELECT * FROM users WHERE id = ? AND name = ?");
        ps.setString(1, UserID);
        ps.setString(2, UserName);
        rs = ps.executeQuery();
        while (rs.next()) {
            String Balance = Integer.toString(rs.getInt("balance"));
            Stage stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("BalancePage.fxml"));
            loader.load();
            Parent root = loader.getRoot();
            BalancePageController bpc = loader.getController();
            bpc.SetBalance(Balance);
            Scene scene = new Scene(root);
            stage.setResizable(false);
            stage.sizeToScene();
            stage.setTitle("Balance Page");
            stage.setScene(scene);
            stage.show();
        }
        ps.close();
        rs.close();
    }
    @FXML
    public void Withdraw(ActionEvent event) throws IOException, SQLException {
        Stage stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("WithdrawPage.fxml"));
        loader.load();
        Parent root = loader.getRoot();
        BalancePageController bpc = loader.getController();
        bpc.getUserID(UserID);
//        bpc.StqWithdrawPage();
        Scene scene = new Scene(root);
        stage.setResizable(false);
        stage.sizeToScene();
        stage.setTitle("Withdraw Page");
        stage.setScene(scene);
        stage.show();
    }
    @FXML
    void DepositMoney(ActionEvent event) throws IOException {
        Stage stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("DepositPage.fxml"));
        loader.load();
        Parent root = loader.getRoot();
        BalancePageController bpc = loader.getController();
        bpc.getUserID(UserID);
//        bpc.StqDepositPage();
        Scene scene = new Scene(root);
        stage.setResizable(false);
        stage.sizeToScene();
        stage.setTitle("Deposit Page");
        stage.setScene(scene);
        stage.show();
    }
    @FXML
    void AccountInfo(ActionEvent event) throws IOException {
        Stage stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("AccountInfo.fxml"));
        loader.load();
        Parent root = loader.getRoot();
        AccountInfoController aic = loader.getController();
        aic.getUserID(UserID);
    //    aic.StqAccountInfo();
        Scene scene = new Scene(root);
        stage.setResizable(false);
        stage.sizeToScene();
        stage.setTitle("Account Information");
        stage.setScene(scene);
        stage.show();
    }
    @FXML
    void ChangePassword(ActionEvent event) throws IOException {
        Stage stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("ChangePassword.fxml"));
        loader.load();
        Parent root = loader.getRoot();
        AccountInfoController aic = loader.getController();
        aic.getUserID(UserID);
//        aic.StqPasswordChangePage();
        Scene scene = new Scene(root);
        stage.setResizable(false);
        stage.sizeToScene();
        stage.setTitle("Change Password");
        stage.setScene(scene);
        stage.show();
    }
    @FXML
    void BalanceTransfer(ActionEvent event) throws IOException {
        Stage stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("BalanceTransfer.fxml"));
        loader.load();
        Parent root = loader.getRoot();
        BalancePageController bpc = loader.getController();
        bpc.getUserID(UserID);
 //       bpc.StqBalanceTransPage();
        Scene scene = new Scene(root);
        stage.setResizable(false);
        stage.sizeToScene();
        stage.setTitle("Balance Transfer");
        stage.setScene(scene);
        stage.show();
    }
    @FXML
    public void StatementButton(ActionEvent event) throws IOException, SQLException {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("StatementUser.fxml"));
        loader.load();
        Parent root = loader.getRoot();
        StatementUserController stu = loader.getController();
        stu.getUserID(UserID);
        stu.LoadTransactionsData();
        Scene scene = new Scene(root);
        stage.setResizable(false);
        stage.sizeToScene();
        stage.setTitle("Statement Page");
        stage.setScene(scene);
        stage.show();
    }


}
