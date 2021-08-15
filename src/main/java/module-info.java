module com.college_management_system {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;


    opens com.college_management_system to javafx.fxml;
    exports com.college_management_system;
}