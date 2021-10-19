package com.college_management_system;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;

public class RemoveAdminController {

    @FXML
    private TextField adminId;

    CommonMethods commonMethods = new CommonMethods();

    //set confirmation alert
    Alert alert = new Alert(
            Alert.AlertType.CONFIRMATION,
            "Do you really want to delete this Administrator?\nThis process cannot be undone!",
            ButtonType.YES,ButtonType.CANCEL
    );

    public void removeAdminFromDB(ActionEvent event) throws Exception {

        String adminid = adminId.getText();

        if (adminid.isEmpty())return; //do nothing if admin id is not entered by the user

        alert.setContentText("Do you really want to delete this Administrator?\nThis process cannot be undone!");
        alert.showAndWait();

        //if user press ok then delete reapective record
        if (alert.getResult() == ButtonType.YES){
            commonMethods.removeClientDataFromDB("admin",adminid);
        }
    }
}
