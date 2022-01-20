module com.college_management_system {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires itextpdf;


    opens com.college_management_system to javafx.fxml;
    exports com.college_management_system;
    exports com.controllers.student;
    opens com.controllers.student to javafx.fxml;
    exports com.controllers.employee;
    opens com.controllers.employee to javafx.fxml;
    exports com.controllers.admin;
    opens com.controllers.admin to javafx.fxml;
    exports com.forgetpassword;
    opens com.forgetpassword to javafx.fxml;
    exports com.database;
    opens com.database to javafx.fxml;
    exports com.beans;
    opens com.beans to javafx.fxml;
    exports com.constant;
    opens com.constant to javafx.fxml;
    exports com.services;
    opens com.services to javafx.fxml;
    exports com.util;
    opens com.util to javafx.fxml;
}