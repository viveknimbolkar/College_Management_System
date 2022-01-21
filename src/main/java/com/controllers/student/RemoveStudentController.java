package com.controllers.student;

import com.application.CommonMethods;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;

public class RemoveStudentController {

    @FXML
    private TextField studentId;

    CommonMethods commonMethods = new CommonMethods();

    //set confirmation alert
    Alert alert = new Alert(
            Alert.AlertType.CONFIRMATION,
            "Do you really want to delete this Student?\nThis process cannot be undone!",
            ButtonType.YES,ButtonType.CANCEL
    );

    public void removeStudentFromDB() throws Exception {
        String studentid = studentId.getText();
        alert.setContentText("Do you really want to delete this Student?\nThis process cannot be undone!");
        alert.showAndWait();
        //if user press ok then delete reapective record
        if (alert.getResult() == ButtonType.YES){
            commonMethods.removeClientDataFromDB("student",studentid);
        }
    }
}
