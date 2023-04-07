package com.fma.bank_account_management_system;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;


import java.io.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;

public class AdminPageController {
    @FXML
    private Button loadcusinfo;
    @FXML
    private Image image;
    @FXML
    private TextField num;
    @FXML
    private TableView<Object> customertable;
    @FXML
    private TableColumn<CustomerData,String> cusid;
    @FXML
    private TableColumn<CustomerData,String> cusname;
    @FXML
    private TableColumn<CustomerData,String> cusaddress;
    @FXML
    private TableColumn<CustomerData,String> cusemail;
    @FXML
    private TableColumn<CustomerData,String> cusphone;
    @FXML
    private TableColumn<CustomerData,Integer> cusbalance;
    //@FXML
    private ObservableList<Object> data;
    @FXML
    private Label welcome;
    static String AdminID;
    @FXML
    private ImageView adminimage;
    @FXML
    private Label adminname;
    @FXML
    private Label adminid;

    public void initialize() throws SQLException {
        LoadCustomerData();
    }
    public void GetAdminID(String id) throws SQLException, FileNotFoundException, IOException {
        AdminID = id;
        Connection con = DBConnection.ConnectToDB();
        PreparedStatement ps = con.prepareStatement("SELECT * FROM admins WHERE id = ?");
        ps.setString(1, id);
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            adminname.setText(rs.getString("name"));
            adminid.setText(rs.getString("id"));
//            InputStream is = rs.getBinaryStream("image");
//            OutputStream os = new FileOutputStream(new File("adminimage.jpeg"));
//            byte[] content = new byte[1024];
//            int s = 0;
//            while((s= is.read(content))!= -1) {
//                os.write(content, 0, s);
//            }
            Image image = new Image("file:adminimage.jpeg");
            adminimage.setImage(image);
            adminimage.setFitWidth(248);
            adminimage.setFitHeight(186);
            Circle clip = new Circle(93,93,93);
            adminimage.setClip(clip);
        }
        ps.close();
        rs.close();
    }
    @FXML
    public void LoadCustomerData() throws SQLException {
        Connection con = DBConnection.ConnectToDB();
        data = FXCollections.observableArrayList();
        PreparedStatement ps = con.prepareStatement("SELECT * FROM users");
        ResultSet rs = ps.executeQuery();
        while(rs.next()) {
            data.add(new CustomerData(rs.getString("id"), rs.getString("name"), rs.getString("address"), rs.getString("email"), rs.getString("phone"), rs.getInt("balance")));
        }
        cusid.setCellValueFactory(new PropertyValueFactory<CustomerData,String>("CustomerId"));
        cusname.setCellValueFactory(new PropertyValueFactory<CustomerData,String>("CustomerName"));
        cusaddress.setCellValueFactory(new PropertyValueFactory<CustomerData,String>("CustomerAddress"));
        cusemail.setCellValueFactory(new PropertyValueFactory<CustomerData,String>("CustomerEmail"));
        cusphone.setCellValueFactory(new PropertyValueFactory<CustomerData,String>("CustomerPhone"));
        cusbalance.setCellValueFactory(new PropertyValueFactory<CustomerData,Integer>("CustomerBalance"));
//        customertable.setItems(null);
        customertable.setItems(data);
        ps.close();
        rs.close();
        Image image = new Image("file:adminimage.jpeg");
        adminimage.setImage(image);
        adminimage.setFitWidth(248);
        adminimage.setFitHeight(186);
        Circle clip = new Circle(93,93,93);
        adminimage.setClip(clip);
    }
    @FXML
    public void AddCustomerData(ActionEvent event) throws IOException {
        Stage stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("AddCustomer.fxml"));
        loader.load();
        Parent root = loader.getRoot();
        Scene scene = new Scene(root);
        stage.setResizable(false);
        stage.sizeToScene();
        stage.setTitle("Add User Page");
        stage.setScene(scene);
        stage.show();
    }
    @FXML
    public void EditCustomerData(ActionEvent event) throws IOException{
        Stage stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("EditCustomer.fxml"));
        loader.load();
        Parent root = loader.getRoot();
        Scene scene = new Scene(root);
        stage.setResizable(false);
        stage.sizeToScene();
        stage.setTitle("Edit User Page");
        stage.setScene(scene);
        stage.show();

    }
    @FXML
    public void DeleteCustomerData(ActionEvent event) throws IOException{
        Stage stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("DeleteCustomer.fxml"));
        loader.load();
        Parent root = loader.getRoot();
        Scene scene = new Scene(root);
        stage.setResizable(false);
        stage.sizeToScene();
        stage.setTitle("Delete User Page");
        stage.setScene(scene);
        stage.show();
    }
    @FXML
    public void ResetPassword(ActionEvent event) throws IOException{
        Stage stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("PasswordReset.fxml"));
        loader.load();
        Parent root = loader.getRoot();
        Scene scene = new Scene(root);
        stage.setResizable(false);
        stage.sizeToScene();
        stage.setTitle("Password Reset Page");
        stage.setScene(scene);
        stage.show();
    }
    @FXML
    private Button transactionsButton;
    @FXML
    public void TransactionsButton(ActionEvent event) throws IOException {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("TransactionStatement.fxml"));
        loader.load();
        Parent root = loader.getRoot();
        stage.setTitle("Transactions Page");
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
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
}
