package com.fma.bank_account_management_system;


import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class TransactionData {
    private final StringProperty UserFrom;
    private final StringProperty UserFromName;
    private final StringProperty UserTo;
    private final StringProperty UserToName;
    private final StringProperty TransactionType;
    private final IntegerProperty TransactionAmount;
    public TransactionData(String UserFrom, String UserFromName, String UserTo, String UserToName, String TransactionType, Integer TransactionAmount) {
        this.UserFrom = new SimpleStringProperty(UserFrom);
        this.UserFromName = new SimpleStringProperty(UserFromName);
        this.UserTo = new SimpleStringProperty(UserTo);
        this.UserToName = new SimpleStringProperty(UserToName);
        this.TransactionType = new SimpleStringProperty(TransactionType);
        this.TransactionAmount = new SimpleIntegerProperty(TransactionAmount);
    }
    public String getUserFrom(){
        return UserFrom.get();
    }
    public String getUserFromName(){
        return UserFromName.get();
    }
    public String getUserTo(){
        return UserTo.get();
    }
    public String getUserToName(){
        return UserToName.get();
    }
    public String getTransactionType(){
        return TransactionType.get();
    }
    public Integer getTransactionAmount(){
        return TransactionAmount.get();
    }
}

