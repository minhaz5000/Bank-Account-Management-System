package com.fma.bank_account_management_system;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class StatementUserController {
    static String UserID;
    public void getUserID(String Id) {
        UserID = Id;
    }
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
        @FXML
        private TableView<Object> transactionsTable;
        @FXML
        private TableColumn<TransactionData, String> userfromid;
        @FXML
        private TableColumn<TransactionData, String> userfromname;
        @FXML
        private TableColumn<TransactionData, String> usertoid;
        @FXML
        private TableColumn<TransactionData, String> usertoname;
        @FXML
        private TableColumn<TransactionData, String> transactiontype;
        @FXML
        private TableColumn<TransactionData, Integer> amount;
        //@FXML
        private ObservableList<Object> data;

    @FXML
    public void LoadTransactionsData() throws SQLException {
        Connection con = DBConnection.ConnectToDB();
        data = FXCollections.observableArrayList();
        PreparedStatement ps = con.prepareStatement("SELECT * From transactions where idfrom=? or idto=?");
        ps.setString(1, UserID);
        ps.setString(2, UserID);
        ResultSet rs = ps.executeQuery();
        while(rs.next()) {
            data.add(new TransactionData(rs.getString("idfrom"), rs.getString("fromname"), rs.getString("idto"), rs.getString("toname"), rs.getString("type"), rs.getInt("amount")));
        }
        userfromid.setCellValueFactory(new PropertyValueFactory<TransactionData,String>("UserFrom"));
        userfromname.setCellValueFactory(new PropertyValueFactory<TransactionData,String>("UserFromName"));
        usertoid.setCellValueFactory(new PropertyValueFactory<TransactionData,String>("UserTo"));
        usertoname.setCellValueFactory(new PropertyValueFactory<TransactionData,String>("UserToName"));
        transactiontype.setCellValueFactory(new PropertyValueFactory<TransactionData,String>("TransactionType"));
        amount.setCellValueFactory(new PropertyValueFactory<TransactionData,Integer>("TransactionAmount"));
//        transactionsTable.setItems(null);
        transactionsTable.setItems(data);
        ps.close();
        rs.close();
    }

    @FXML
    private Button exportButton;
    @FXML
    private void ExportAsPDF() throws Exception{
        try {
            Connection con = DBConnection.ConnectToDB();
            PreparedStatement stmt = con.prepareStatement("SELECT * From transactions where idfrom=? or idto=?");
            stmt.setString(1, UserID);
            stmt.setString(2, UserID);
            /* Define the SQL query */
            ResultSet query_set = stmt.executeQuery();
            /* Step-2: Initialize PDF documents - logical objects */
            Document my_pdf_report = new Document();

            String fileName = "bank_statement_" + UserID + ".pdf";
            PdfWriter.getInstance(my_pdf_report, new FileOutputStream(fileName));
            my_pdf_report.open();
            //we have 6 columns in our table
            PdfPTable my_report_table = new PdfPTable(6);
            //create a cell object
            PdfPCell table_cell;

            while (query_set.next()) {
                String FromID = query_set.getString("idfrom");
                table_cell=new PdfPCell(new Phrase(FromID));
                my_report_table.addCell(table_cell);
                String FromName=query_set.getString("fromname");
                table_cell=new PdfPCell(new Phrase(FromName));
                my_report_table.addCell(table_cell);
                String ToID=query_set.getString("idto");
                table_cell=new PdfPCell(new Phrase(ToID));
                my_report_table.addCell(table_cell);
                String ToName=query_set.getString("toname");
                table_cell=new PdfPCell(new Phrase(ToName));
                my_report_table.addCell(table_cell);
                String TransType=query_set.getString("type");
                table_cell=new PdfPCell(new Phrase(TransType));
                my_report_table.addCell(table_cell);
                String Amount=query_set.getString("amount");
                table_cell=new PdfPCell(new Phrase(Amount));
                my_report_table.addCell(table_cell);
            }
            /* Attach report table to PDF */
            my_pdf_report.add(my_report_table);
            my_pdf_report.close();

            /* Close all DB related objects */
            query_set.close();
            stmt.close();

        } catch (FileNotFoundException | DocumentException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

}
