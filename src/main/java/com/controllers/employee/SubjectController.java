package com.controllers.employee;

import com.beans.Subject;
import com.constant.AllConstants;
import com.database.DBConnection;
import com.util.CustomAlerts;
import com.util.Validation;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class SubjectController extends Thread implements Initializable{

    @FXML
    private ChoiceBox<String> branch, semester, subjectType;
    @FXML
    private TextField subjectName,subjectCode, theoryMarks, practicalMarks;
    @FXML
    private TableView<com.beans.Subject> subjectTable;
    @FXML
    private TableColumn<String, Subject> scode, sname, sbranch, ssemester, stheoryMarks, spracticalMarks, stype;
    AllConstants constants = new AllConstants();
    CustomAlerts alerts = new CustomAlerts();
    Validation validation = new Validation();


    public void addSubject(){
        String[] subjectData = {
            subjectCode.getText(),
            subjectName.getText(),
            subjectType.getValue(),
            branch.getValue(),
            semester.getValue(),
            theoryMarks.getText(),
            practicalMarks.getText()
        };
        if (!validation.validateFieldData(subjectData)) return;
        if (semester.getValue().isEmpty() || branch.getValue().isEmpty() || subjectType.getValue().isEmpty()) return;
        try {
            Connection con = DBConnection.getDBConnection();
            String queryAddSubject = "INSERT INTO `subject` (`code`, `subject_name`, `course_type`, `branch`, " +
                    "`semester`, `theory_marks`, `practical_marks`) VALUES (?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement prep = con.prepareStatement(queryAddSubject);

            for (int i = 0; i < 7; i++)
                prep.setString(i+1,subjectData[i]);

            int result = prep.executeUpdate();
            System.out.println(result);
            if (result > 0) alerts.infoAlert("Subject added successfully");
            else alerts.errorAlert("Subject Not added. Try again!");

        }catch (Exception e){
            e.printStackTrace();
            alerts.errorAlert("Something went wrong.\nPlease try again later!");
        }
    }

    public void reset(){
        subjectType.setValue(null);
        subjectCode.setText(null);
        subjectName.setText(null);
        branch.setValue(null);
        semester.setValue(null);
        theoryMarks.setText(null);
        practicalMarks.setText(null);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ObservableList<String> list = FXCollections.observableArrayList(
                "Core",
                "Open Elective",
                "Professional Elective");
        branch.setItems(constants.getBranchList());
        branch.show();
        semester.setItems(constants.getSemestersList());
        semester.show();
        subjectType.setItems(list);
        subjectType.show();

        //fetch all the subjects
        try{
            List<Subject> subjectList = new ArrayList<>();
            Connection con = DBConnection.getDBConnection();
            String queryGetSubjects = "SELECT * FROM `subject`";
            PreparedStatement prep = con.prepareStatement(queryGetSubjects);
            ResultSet result = prep.executeQuery();
            while (result.next()){
                subjectList.add(
                        new Subject(
                                result.getString("code"),
                                result.getString("subject_name"),
                                result.getString("course_type"),
                                result.getString("branch"),
                                result.getString("semester"),
                                result.getString("theory_marks"),
                                result.getString("practical_marks")
                        )
                );
            }
            ObservableList<Subject> subjectObservableListList = FXCollections.observableArrayList(subjectList);
            scode.setCellValueFactory(new PropertyValueFactory<>("subjectCode"));
            sname.setCellValueFactory(new PropertyValueFactory<>("subjectName"));
            stype.setCellValueFactory(new PropertyValueFactory<>("courseType"));
            sbranch.setCellValueFactory(new PropertyValueFactory<>("branch"));
            ssemester.setCellValueFactory(new PropertyValueFactory<>("semester"));
            stheoryMarks.setCellValueFactory(new PropertyValueFactory<>("theoryMarks"));
            spracticalMarks.setCellValueFactory(new PropertyValueFactory<>("practicalMarks"));
            subjectTable.setItems(subjectObservableListList);
        }catch (Exception e){
            e.printStackTrace();
            alerts.errorAlert("Something went wrong.\nPlease try again later!");
        }

    }
}
