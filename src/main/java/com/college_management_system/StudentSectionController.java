package com.college_management_system;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;

public class StudentSectionController {

   @FXML
   private BorderPane borderPane;

    CommonMethods commonMethods = new CommonMethods();

    public void goBack(ActionEvent event) throws Exception{
        commonMethods.getMainSectionWindow(event,"main-switch.fxml");
    }

    //find student scene
    public void findStudentView(ActionEvent e) throws Exception{
        //System.out.println("find admin scene");
        SceneLoader sceneLoader = new SceneLoader();
        Pane view = sceneLoader.getView("find-student.fxml");
        borderPane.setCenter(view);
    }

    //add student scene
    public void addStudentView(ActionEvent e) throws Exception{
        //System.out.println("find admin scene");
        SceneLoader sceneLoader = new SceneLoader();
        Pane view = sceneLoader.getView("add-new-student.fxml");
        borderPane.setCenter(view);
    }
    //remove student scene
    public void removeStudentView(ActionEvent e) throws Exception{
        //System.out.println("find admin scene");
        SceneLoader sceneLoader = new SceneLoader();
        Pane view = sceneLoader.getView("remove-student.fxml");
        borderPane.setCenter(view);
    }

    //update student scene
    public void updateStudentView(ActionEvent e) throws Exception{
        //System.out.println("find admin scene");
        SceneLoader sceneLoader = new SceneLoader();
        Pane view = sceneLoader.getView("update-student.fxml");
        borderPane.setCenter(view);
    }
}
