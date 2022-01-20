package com.application;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;

public class EmployeeSectionController {

    @FXML
    private BorderPane borderPane;

    CommonMethods commonMethods = new CommonMethods();

    public void goBack(ActionEvent event) throws Exception{
        commonMethods.getMainSectionWindow(event,"main-switch.fxml");
    }

    //find student scene
    public void findEmployeeView(ActionEvent e) throws Exception{
        SceneLoader sceneLoader = new SceneLoader();
        Pane view = sceneLoader.getView("find-employee.fxml");
        borderPane.setCenter(view);
    }

    //add Employee scene
    public void addEmployeeView(ActionEvent e) throws Exception{
        SceneLoader sceneLoader = new SceneLoader();
        Pane view = sceneLoader.getView("add-new-employee.fxml");
        borderPane.setCenter(view);
    }
    //remove Employee scene
    public void removeEmployeeView(ActionEvent e) throws Exception{
        SceneLoader sceneLoader = new SceneLoader();
        Pane view = sceneLoader.getView("remove-employee.fxml");
        borderPane.setCenter(view);
    }

    //update Employee scene
    public void updateEmployeeView(ActionEvent e) throws Exception{
        SceneLoader sceneLoader = new SceneLoader();
        Pane view = sceneLoader.getView("update-employee.fxml");
        borderPane.setCenter(view);
    }
}
