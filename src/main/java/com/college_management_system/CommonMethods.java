package com.college_management_system;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import com.college_management_system.backend.AllConstants;

public class CommonMethods {

    AllConstants allConstants = new AllConstants();

    Parent root;
    Stage stage;
    Scene scene;

    //get the main section window
    public void getMainSectionWindow(ActionEvent e, String fxmlName) throws Exception{
        root = FXMLLoader.load((getClass().getResource(fxmlName)));
        stage = (Stage) ((Node)e.getSource()).getScene().getWindow();
        scene = new Scene(root,allConstants.MAIN_SWITCH_WIDTH,allConstants.MAIN_SWITCH_HEIGHT);
        stage.setScene(scene);
        stage.show();
    }
}
