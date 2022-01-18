package com.college_management_system;

import com.college_management_system.backend.DBConnection;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.PieChart.Data;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;

import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ResourceBundle;

public class Insights implements Initializable {
    @FXML
    private Label totalStudents, totalStaff, totalFeePaid;
    @FXML
    private PieChart genderRatioChart;
    private Connection connection = DBConnection.getDBConnection();
    private Alert alert = new Alert(Alert.AlertType.ERROR);


    //load all the data into dashboard scene
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            //load total number of students
            String loadTotalStudentsQuery = "SELECT COUNT(email) FROM `student`";
            Statement stmt = connection.createStatement();
            ResultSet student = stmt.executeQuery(loadTotalStudentsQuery);
            if (!student.next()) return;
            totalStudents.setText(student.getString(1));

            //load total number of staff
            String loadTotalStaffQuery = "SELECT COUNT(email) FROM `employee`";
            ResultSet staff = stmt.executeQuery(loadTotalStaffQuery);
            if (!staff.next()) return;
            totalStaff.setText(staff.getString(1));

            //load total fee paid by students
            String loadTotalFeeQuery = "SELECT SUM(fee_paid) FROM `fees`";
            ResultSet fee = stmt.executeQuery(loadTotalFeeQuery);
            if (!fee.next()) return;
            totalFeePaid.setText(fee.getString(1));

            //fetch total male present in college
            String loadTotalMale = "SELECT COUNT(gender) FROM `student` WHERE gender='male'";
            ResultSet maleRatio = stmt.executeQuery(loadTotalMale);
            if (!maleRatio.next()) return;
            PieChart.Data male = new PieChart.Data("Boys",Double.parseDouble(maleRatio.getString(1)));

            //fetch total female present in college
            String loadTotalFemale = "SELECT COUNT(gender) FROM `student` WHERE gender='female'";
            ResultSet femaleRatio = stmt.executeQuery(loadTotalFemale);
            if (!femaleRatio.next()) return;
            PieChart.Data female = new PieChart.Data("Girls",Double.parseDouble(femaleRatio.getString(1)));

            genderRatioChart.getData().add(male);
            genderRatioChart.getData().add(female);

        }catch (Exception e){
            e.printStackTrace();
            alert.setAlertType(Alert.AlertType.ERROR);
            alert.setContentText("Something went Wrong\nPlease try again!");
            alert.show();
            String handle = "vivek nimbolkar";
        }
    }
}
