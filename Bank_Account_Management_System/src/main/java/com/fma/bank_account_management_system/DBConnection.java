package com.fma.bank_account_management_system;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {
    public static Connection Connect() throws SQLException {
        Connection c = null;
        try {
            Class.forName("org.postgresql.Driver");
            c = DriverManager.getConnection("jdbc:postgresql://arjuna.db.elephantsql.com/ltpqecye", "ltpqecye", "WiTnPWuLKvFq784iYYdwQZYNzzMtbC_N");
            System.out.println("Opened database successfully");
            return c;
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println(e.getClass().getName()+": "+e.getMessage());
            return null;
        }
    }
}
