package com.college_management_system;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import com.constant.AllConstants;

/*
This controller is used to manager and control main section
1. admin section
2. student section
3. employee section

It is basically section changer class for above three sections
 */
public class MainSectionController {

    AllConstants allConstants = new AllConstants();

    public Button adminBtn;
    public Button studentBtn;
    public Button employeeBtn;

    public void adminSection(ActionEvent event) throws Exception{
        getRespectedWindow(event,"admin.fxml");
    }

    //student section handler
    public void studentSection(ActionEvent event) throws Exception{
        getRespectedWindow(event,"student-main.fxml");
    }

    //employee section handler
    public void employeeSection(ActionEvent event) throws Exception{
        getRespectedWindow(event,"employee-main.fxml");
    }

    //get respected scenes
    public void getRespectedWindow(ActionEvent event, String fxmlName) throws Exception{
        Parent root = FXMLLoader.load((getClass().getResource(fxmlName)));
        Stage stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root,allConstants.WIDTH,allConstants.HEIGHT);
        stage.setX(allConstants.H_POSITION);
        stage.setY(allConstants.V_POSITION);
        stage.setScene(scene);
        stage.show();
    }
}
