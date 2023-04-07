package com.fma.bank_account_management_system;

import javafx.event.ActionEvent;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Transaction {
    public static void TransactionInsert(String from, String fromName, String to, String toName, String type, int amount) throws SQLException {
                Connection con = DBConnection.ConnectToDB();
                PreparedStatement ps = con.prepareStatement("INSERT INTO transactions VALUES (?,?,?,?,?,?)");
                ps.setString(1, from);
                ps.setString(2, fromName);
                ps.setString(3, to);
                ps.setString(4, toName);
                ps.setString(5, type);
                ps.setInt(6, amount);
                ps.executeUpdate();
                ps.close();

    }
}
