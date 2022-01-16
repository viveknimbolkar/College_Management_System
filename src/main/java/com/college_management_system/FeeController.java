package com.college_management_system;

import com.college_management_system.backend.AllConstants;
import com.college_management_system.backend.DBConnection;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.time.LocalDate;
import java.util.ResourceBundle;

public class FeeController implements Initializable {
    CommonMethods commonMethods = new CommonMethods();
    AllConstants constants = new AllConstants();
    Alert warningAlert = new Alert(Alert.AlertType.WARNING);

    @FXML
    TextField studentId, fullname, searchStudentId;
    @FXML
    Spinner<Integer> fee;
    @FXML
    ChoiceBox<String> branch, semester, educationYear;

    //clear all the fields of form
    public void resetAllFields(){
        studentId.clear();
        fullname.clear();
        searchStudentId.clear();
        branch.valueProperty().set(null);
        semester.valueProperty().set(null);
        educationYear.valueProperty().set(null);
    }

    // collect the fees of student
    public void collectFees(){
        try {
           String[] studentFeeInfo = {
                   studentId.getText(),
                   fullname.getText(),
                   branch.getValue(),
                   semester.getValue(),
                   educationYear.getValue(),
                   fee.getValue().toString()
           };

           //validate the user data if any entry found then break the operation
            if (!commonMethods.validateAdminData(studentFeeInfo))
                return;

            LocalDate currentDate = LocalDate.now();
            //save the fees data into database
            String saveFeesQuery = "INSERT INTO `fees` (`fullname`, `student_id`, `fee_paid`, `branch`, `semester`, " +
                    "`education_year`, `date`) VALUES (?, ?, ?, ?, ?, ?, '"+currentDate+"')";
            Connection connection = DBConnection.getDBConnection();
            PreparedStatement prepStmt = connection.prepareStatement(saveFeesQuery);

            for (int i = 0; i < studentFeeInfo.length; i++)
                prepStmt.setString(i+1,studentFeeInfo[i]);

            int queryStatus = prepStmt.executeUpdate();
            if (queryStatus == 1){
                warningAlert.setAlertType(Alert.AlertType.INFORMATION);
                warningAlert.setContentText("Fee Paid Successfully!");
                warningAlert.show();
            }else {
                warningAlert.setAlertType(Alert.AlertType.ERROR);
                warningAlert.setContentText("Fees Not Paid. Try again!");
                warningAlert.show();
            }
        }catch (Exception e){
            warningAlert.setAlertType(Alert.AlertType.ERROR);
            warningAlert.setContentText("Something went Wrong\nPlease try again!");
            warningAlert.show();
        }
    }

    public void findFeeHistory(){
        try {
            System.out.println("find fees");
        }catch (Exception e){
            warningAlert.setAlertType(Alert.AlertType.ERROR);
            warningAlert.setContentText("Something went Wrong\nPlease try again!");
            warningAlert.show();
        }
    }

    public void clearBtn(ActionEvent e){

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle){
        branch.setItems(constants.getBranchList());
        branch.show();
        semester.setItems(constants.getSemestersList());
        semester.show();
        educationYear.setItems(constants.getEducationalYearList());
        educationYear.show();
        //set fee values
        SpinnerValueFactory<Integer> stringSpinnerValueFactory =
                new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 21474836,100);

        fee.setValueFactory(stringSpinnerValueFactory);
        fee.setEditable(true);
    }

}
