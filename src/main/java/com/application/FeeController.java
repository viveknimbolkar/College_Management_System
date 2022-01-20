package com.application;

import com.beans.StudentFee;
import com.constant.AllConstants;
import com.database.DBConnection;
import com.util.CustomAlerts;
import com.util.Validation;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class FeeController implements Initializable {
    Connection connection = DBConnection.getDBConnection();
    CommonMethods commonMethods = new CommonMethods();
    AllConstants constants = new AllConstants();
    CustomAlerts alert = new CustomAlerts();
    Validation validation = new Validation();

    @FXML
    TextField studentId, fullname, searchStudentId;
    @FXML
    Spinner<Integer> fee;
    @FXML
    ChoiceBox<String> branch, semester, educationYear;
    @FXML
    TableView<StudentFee> feeTable;
    @FXML
    TableColumn<StudentFee, String> student_id, student_name, student_branch, student_semester,
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
            if (!validation.validateFieldData(studentFeeInfo))
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
                alert.infoAlert("Fee paid successfully!");
            }else {
               alert.warningAlert("Fees Not Paid. Try again!");
            }
        }catch (Exception e){
            e.printStackTrace();
            alert.errorAlert("Something went Wrong\nPlease try again!");
        }
    }

    public void findFeeHistory(){
        try {
            if (searchStudentId.getText().equals("")) return;
            // store the data fetched by result set
            List<StudentFee> studentFeeList = new ArrayList<>();
            String fetchFeeHistory = "SELECT * FROM `fees` WHERE student_id='"+searchStudentId.getText()+"'";
            PreparedStatement preparedStatement = connection.prepareStatement(fetchFeeHistory);
            ResultSet result = preparedStatement.executeQuery();


            //store fetched data into a list
            while (result.next()){
                studentFeeList.add(new StudentFee(
                        result.getString(2),
                        result.getString(3),
                        result.getString(4),
                        result.getString(5),
                        result.getString(6),
                        result.getString(7),
                        result.getString(8)
                ));
            }

            // add fetched data into the list
            ObservableList<StudentFee> observableList = FXCollections.observableArrayList(studentFeeList);
            student_id.setCellValueFactory(new PropertyValueFactory<>("studentId"));
            student_name.setCellValueFactory(new PropertyValueFactory<>("studentName"));
            student_branch.setCellValueFactory(new PropertyValueFactory<>("feePaid"));
            student_semester.setCellValueFactory(new PropertyValueFactory<>("branch"));
            student_education_year.setCellValueFactory(new PropertyValueFactory<>("semester"));
            fee_paid.setCellValueFactory(new PropertyValueFactory<>("educationYear"));
            date.setCellValueFactory(new PropertyValueFactory<>("date"));

            feeTable.setItems(observableList);

        }catch (Exception e){
            e.printStackTrace();
            alert.errorAlert("Something went Wrong\nPlease try again!");
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
