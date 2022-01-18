package com.college_management_system;

import com.college_management_system.backend.AllConstants;
import com.college_management_system.backend.DBConnection;
import com.college_management_system.backend.StudentFeeDetails;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class FeeController implements Initializable {
    Connection connection = DBConnection.getDBConnection();
    CommonMethods commonMethods = new CommonMethods();
    AllConstants constants = new AllConstants();
    Alert warningAlert = new Alert(Alert.AlertType.WARNING);

    @FXML
    TextField studentId, fullname, searchStudentId;
    @FXML
    Spinner<Integer> fee;
    @FXML
    ChoiceBox<String> branch, semester, educationYear;
    @FXML
    TableColumn<StudentFeeDetails, String> student_id, student_name, student_branch, student_semester,
            student_education_year, fee_paid, date;

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
                   fullname.getText(),
                   studentId.getText(),
                   fee.getValue().toString(),
                   branch.getValue(),
                   semester.getValue(),
                   educationYear.getValue()
           };

           //validate the user data if any entry found then break the operation
            if (!commonMethods.validateAdminData(studentFeeInfo))
                return;

            LocalDate currentDate = LocalDate.now();
            //save the fees data into database
            String saveFeesQuery = "INSERT INTO `fees` (`student_name`, `student_id`, `fee_paid`, `branch`, " +
                    "`semester`, `education_year`, `date`) VALUES (?, ?, ?, ?, ?, ?,'"+currentDate+"')";
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
            e.printStackTrace();
            warningAlert.setAlertType(Alert.AlertType.ERROR);
            warningAlert.setContentText("Something went Wrong\nPlease try again!");
            warningAlert.show();
        }
    }

    public void findFeeHistory(){
        try {
            if (searchStudentId.getText().equals("")) return;
            String fetchFeeHistory = "SELECT * FROM `fees` WHERE student_id='"+searchStudentId.getText()+"'";
            ResultSet resultSet = connection.createStatement().executeQuery(fetchFeeHistory);

                    System.out.println(resultSet.getString(1));


//            if (!resultSet.next()){
//                warningAlert.setAlertType(Alert.AlertType.ERROR);
//                warningAlert.setContentText("Something went Wrong\nPlease try again!");
//                warningAlert.show();
//                return;
//            }

//            String[] feesHistory = {
//                    resultSet.getString(2),
//                    resultSet.getString(3),
//                    resultSet.getString(4),
//                    resultSet.getString(5),
//                    resultSet.getString(6),
//                    resultSet.getString(7),
//                    resultSet.getString(8),
//            };

            // TODO: Fetch the fee data from the db and display it in table
//            for (String s: feesHistory)
//                System.out.println(s);

            //add fetched data into table
            ArrayList<StudentFeeDetails> feeData = new ArrayList<>();
//            for (int i = 0; i < feesHistory.length; i++) {
//                feeData.add(new StudentFeeDetails(feesHistory[i]))
//            }
            ObservableList<StudentFeeDetails> listObject = FXCollections.observableArrayList(feeData);

        }catch (Exception e){
            e.printStackTrace();
            warningAlert.setAlertType(Alert.AlertType.ERROR);
            warningAlert.setContentText("Something went Wrong\nPlease try again!");
            warningAlert.show();
        }
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
