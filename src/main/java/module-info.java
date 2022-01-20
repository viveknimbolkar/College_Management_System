module com.college_management_system {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires itextpdf;


    opens com.college_management_system to javafx.fxml;
    exports com.college_management_system;
    exports com.college_management_system.backend;
    opens com.college_management_system.backend to javafx.fxml;
    exports com.controllers.student;
    opens com.controllers.student to javafx.fxml;
    exports com.controllers.employee;
    opens com.controllers.employee to javafx.fxml;
    exports com.controllers.admin;
    opens com.controllers.admin to javafx.fxml;
}