module com.fma.bank_account_management_system {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;

    opens com.fma.bank_account_management_system to javafx.fxml;
    exports com.fma.bank_account_management_system;
}