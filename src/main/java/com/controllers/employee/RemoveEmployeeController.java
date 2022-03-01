package com.controllers.employee;

import com.application.CommonMethods;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;

public class RemoveEmployeeController {

    @FXML
    private TextField employeeId;

    CommonMethods commonMethods = new CommonMethods();

    //set confirmation alert
    Alert alert = new Alert(
            Alert.AlertType.CONFIRMATION,
            "Do you really want to delete this Employee?\nThis process cannot be undone!",
            ButtonType.YES,ButtonType.CANCEL
    );

    public void removeEmployeeFromDB(ActionEvent event) throws Exception {
        String employeeid = employeeId.getText();
        alert.setContentText("Do you really want to delete this Employee?\nThis process cannot be undone!");
        alert.showAndWait();
        //if user press ok then delete reapective record
        if (alert.getResult() == ButtonType.YES){
            commonMethods.removeClientDataFromDB("com/application/employee",employeeid);
        }
    }
}
