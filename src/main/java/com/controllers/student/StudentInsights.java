package com.controllers.student;

import com.database.DBConnection;
import com.util.CustomAlerts;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Label;

import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ResourceBundle;

public class StudentInsights implements Initializable {
    @FXML
    private Label totalStudents, cseStudents, itStudents, extcStudents, mechanicalStudents, civilStudents, architectureStudents;
    @FXML
    private PieChart genderRatioChart, districtStudentRatio;

    private Connection connection = DBConnection.getDBConnection();
    CustomAlerts alerts = new CustomAlerts();


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
            String loadTotalCSEStudentsQuery = "SELECT COUNT(email) FROM `student` WHERE branch='Computer Science & Engineering'";
            ResultSet cse = stmt.executeQuery(loadTotalCSEStudentsQuery);
            if (!cse.next()) return;
            cseStudents.setText(cse.getString(1));

            //load total fee paid by students
            String loadTotalITStudentsQuery = "SELECT COUNT(email) FROM `student` WHERE branch='Information Technology'";
            ResultSet it = stmt.executeQuery(loadTotalITStudentsQuery);
            if (!it.next()) return;
            itStudents.setText(it.getString(1));

            //load total fee paid by students
            String loadTotalEXTCStudentsQuery = "SELECT COUNT(email) FROM `student` WHERE branch='Electronics & Telecommunication Engineering'";
            ResultSet extc = stmt.executeQuery(loadTotalEXTCStudentsQuery);
            if (!extc.next()) return;
            extcStudents.setText(extc.getString(1));


            //load total fee paid by students
            String loadTotalMECHANICALStudentsQuery = "SELECT COUNT(email) FROM `student` WHERE branch='Mechanical " +
                    "Engineering'";
            ResultSet mechanical = stmt.executeQuery(loadTotalMECHANICALStudentsQuery);
            if (!mechanical.next()) return;
            mechanicalStudents.setText(mechanical.getString(1));

            //load total fee paid by students
            String loadTotalCIVILStudentsQuery = "SELECT COUNT(email) FROM `student` WHERE branch='Civil Engineering'";
            ResultSet civil = stmt.executeQuery(loadTotalCIVILStudentsQuery);
            if (!civil.next()) return;
            civilStudents.setText(civil.getString(1));

            //load total fee paid by students
            String loadTotalARCHITECTUREStudentsQuery = "SELECT COUNT(email) FROM `student` WHERE " +
                    "branch='Architecture Engineering'";
            ResultSet architecture = stmt.executeQuery(loadTotalARCHITECTUREStudentsQuery);
            if (!architecture.next()) return;
            architectureStudents.setText(architecture.getString(1));

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
            connection.close();

        }catch (Exception e){
            e.printStackTrace();
            alerts.errorAlert("Something went Wrong\nPlease try again!");
            String handle = "vivek nimbolkar";
        }
    }
}
