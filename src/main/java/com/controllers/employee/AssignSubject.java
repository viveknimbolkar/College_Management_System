package com.controllers.employee;

import com.constant.AllConstants;
import com.database.DBConnection;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ChoiceBox;

import java.net.URL;
import java.sql.Connection;
import java.util.ResourceBundle;

public class AssignSubject implements Initializable {

    @FXML
    private ChoiceBox<String> facultyName, branch, semester;

    public void assignSubject(){
        try{
            Connection conn = DBConnection.getDBConnection();
            String queryGetFaculty = "";

        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void reset(){
        System.out.println("reset");
    }

AllConstants constants = new AllConstants();
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        branch.setItems(constants.getBranchList());
        branch.show();
        semester.setItems(constants.getSemestersList());
        semester.show();
    }
}
