package com.college_management_system;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;

public class AdminSectionController {

    @FXML
    private BorderPane borderPane;

    CommonMethods commonMethods = new CommonMethods();

    //go back to the main section
    public void goBack(ActionEvent event) throws Exception{
        commonMethods.getMainSectionWindow(event,"main-switch.fxml");
    }

    //add admin scene
    public void addAdminView(ActionEvent e) throws Exception{
        //System.out.println("add admin scene");
        SceneLoader sceneLoader = new SceneLoader();
        Pane view = sceneLoader.getView("add-new-admin.fxml");
        borderPane.setCenter(view);
    }

    //update admin scene
    public void updateAdminView(ActionEvent e) throws Exception{
        //System.out.println("update admin scene");
        SceneLoader sceneLoader = new SceneLoader();
        Pane view = sceneLoader.getView("update-admin.fxml");
        borderPane.setCenter(view);
    }

    //find admin scene
    public void findAdminView(ActionEvent e) throws Exception{
        //System.out.println("find admin scene");
        SceneLoader sceneLoader = new SceneLoader();
        Pane view = sceneLoader.getView("find-admin.fxml");
        borderPane.setCenter(view);
    }

    //remove admin scene
    public void removeAdminView(ActionEvent e) throws Exception{
        //System.out.println("remove admin scene");
        SceneLoader sceneLoader = new SceneLoader();
        Pane view = sceneLoader.getView("remove-admin.fxml");
        borderPane.setCenter(view);
    }

}
