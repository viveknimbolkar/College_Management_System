package com.college_management_system;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import com.college_management_system.backend.AllConstants;

public class CommonMethods {

    AllConstants allConstants = new AllConstants();

    Parent root;
    Stage stage;
    Scene scene;
    Pane pane;

//=============GENERAL OPERATIONS======================================================================
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

//=============DATABASE RELATED OPERATIONS===========================================================
    /*It will perform create operation on MySQL Database
    It will receive data from admin, student and employee addition page and insert into main database
     */
    public void addDataIntoDB(String whoCalledMe, String[] data){
        System.out.println("addDataIntoDB");
    }
}
