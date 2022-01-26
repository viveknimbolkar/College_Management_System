package com.examresult.cse;

import com.constant.AllConstants;
import com.database.DBConnection;
import com.util.CustomAlerts;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.TextField;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.ResourceBundle;

public class CSEFirstSemController implements Initializable{

    @FXML
    private TextField studentName;

    @FXML
    private ChoiceBox<String> studentSemester;

    @FXML
    private TextField chemistryMarks;

    @FXML
    private TextField electricalEnggMarks;

    @FXML
    private TextField engineeringGraphicsMarks;

    @FXML
    private TextField cSkillMarks;

    @FXML
    private TextField chemistryLabMarks;

    @FXML
    private TextField electricalEnggLabMarks;

    @FXML
    private TextField enggGraphicsLab;

    @FXML
    private Spinner<Integer> studentID;

    CustomAlerts alerts = new CustomAlerts();
    AllConstants constants = new AllConstants();

    public void saveResult(){
        try {
            String[] studentExamResultData = {
                    studentName.getText(),
                    String.valueOf(studentID.getValue()),
                    studentSemester.getValue().toString(),
                    chemistryMarks.getText(),
                    electricalEnggMarks.getText(),
                    engineeringGraphicsMarks.getText(),
                    cSkillMarks.getText(),
                    chemistryLabMarks.getText(),
                    electricalEnggLabMarks.getText(),
                    enggGraphicsLab.getText()
            };

            Connection connection = DBConnection.getDBConnection();
            String saveStudentResultQuery = "INSERT INTO `cse_first_semester` (`student_id`, `student_name`, " +
                    "`semester`, `chemistry`, `electrical_engineering`, `engineering_graphics`, " +
                    "`communication_skills`, `chemistry_lab`, `electrical_engineering_lab`, `engineering_graphics_lab`, `date`) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, 'current_timestamp(6).000000')";
            PreparedStatement preparedStatement = connection.prepareStatement(saveStudentResultQuery);
            for (int i = 0; i < studentExamResultData.length; i++)
                preparedStatement.setString(i+1,studentExamResultData[i]);
            preparedStatement.executeUpdate();
            connection.close();
        }catch (Exception e){
            e.printStackTrace();
            alerts.errorAlert("Something went wrong.\nPlease try again!");
        }
    }

    public void resetAllFields(){
        studentName.clear();
        studentID.getEditor().clear();
        studentSemester.setValue(null);
        electricalEnggMarks.clear();
        electricalEnggLabMarks.clear();
        enggGraphicsLab.clear();
        engineeringGraphicsMarks.clear();
        chemistryMarks.clear();
        chemistryLabMarks.clear();
        cSkillMarks.clear();
    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        studentSemester.setItems(constants.getSemestersList());
        studentSemester.show();
        SpinnerValueFactory<Integer> stringSpinnerValueFactory =
                new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 21474836,100);
        studentID.setValueFactory(stringSpinnerValueFactory);
        studentID.setEditable(true);
    }
}
