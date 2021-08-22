package com.college_management_system;

import com.college_management_system.backend.DBConnection;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import com.college_management_system.backend.AllConstants;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

public class CommonMethods {

    AllConstants allConstants = new AllConstants();
    Connection conn = DBConnection.getDBConnection();

    Parent root;
    Stage stage;
    Scene scene;
    Pane pane;

//=============GENERAL OPERATIONS======================================================================
//create a random 6 digit number
    public int getRandomNumber(){
        final int minNum = 100000;
        final int maxNum = 999999;
        return (int) (Math.random() * (maxNum - minNum+1) + minNum);
    }

    //get the main section window
    public void getMainSectionWindow(ActionEvent e, String fxmlName) throws Exception{
        root = FXMLLoader.load((getClass().getResource(fxmlName)));
        stage = (Stage) ((Node)e.getSource()).getScene().getWindow();
        scene = new Scene(root,allConstants.MAIN_SWITCH_WIDTH,allConstants.MAIN_SWITCH_HEIGHT);
        stage.setScene(scene);
        stage.show();
    }

    public void clearAllTextFields(Pane pane){
        for (Node node: pane.getChildren() ) {
            if (node instanceof TextField){
                ((TextField)node).setText("");
            }
        }
    }

//=============DATABASE RELATED OPERATIONS=======================================================================

    /*it will check the duplicate entry in main db (admin, student, employee)
    This method will return true if any duplicate record found in main database else it will return false
    */
    public boolean checkForDuplicateEntry(String forUser, String email) throws Exception{

        String query = switch (forUser) {
            case "admin" -> "SELECT * FROM `admin` WHERE email='" + email + "'";
            case "student" -> "SELECT * FROM `student` WHERE email='" + email + "'";
            case "employee" -> "SELECT * FROM `employee` WHERE email='" + email + "'";
            default -> "";
        };

        Statement statement = conn.createStatement();
        ResultSet resultSet = statement.executeQuery(query);

        return resultSet.next();
    }

}
