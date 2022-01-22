package com.application;

import com.menu.HeaderMenu;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;

public class StudentSectionController {

   @FXML
   private BorderPane borderPane;

    CommonMethods commonMethods = new CommonMethods();
    HeaderMenu menu = new HeaderMenu();

    // open about stage
    public void aboutStage(){
        menu.getAboutStage();
    }

    //open fee structure
    public void feeStructure(ActionEvent e){
        menu.getFeeStructure(e);
    }

    public void goBack(ActionEvent event) throws Exception{
        commonMethods.getMainSectionWindow(event,"main-switch.fxml");
    }

    //find student scene
    public void findStudentView(ActionEvent e) throws Exception{
        SceneLoader sceneLoader = new SceneLoader();
        Pane view = sceneLoader.getView("find-student.fxml");
        borderPane.setCenter(view);
    }

    //add student scene
    public void addStudentView(ActionEvent e) throws Exception{
        SceneLoader sceneLoader = new SceneLoader();
        Pane view = sceneLoader.getView("add-new-student.fxml");
        borderPane.setCenter(view);
    }

    //remove student scene
    public void removeStudentView(ActionEvent e) throws Exception{
        SceneLoader sceneLoader = new SceneLoader();
        Pane view = sceneLoader.getView("remove-student.fxml");
        borderPane.setCenter(view);
    }

    //update student scene
    public void updateStudentView(ActionEvent e) throws Exception{
        SceneLoader sceneLoader = new SceneLoader();
        Pane view = sceneLoader.getView("update-student.fxml");
        borderPane.setCenter(view);
    }

    //student fees scene
    public void feesStudentView(ActionEvent e) throws Exception{
        SceneLoader sceneLoader = new SceneLoader();
        Pane view = sceneLoader.getView("student-fees.fxml");
        borderPane.setCenter(view);
    }

    //student insight scene
    public void studentInsightView() throws Exception{
        SceneLoader sceneLoader = new SceneLoader();
        Pane view = sceneLoader.getView("student-insights.fxml");
        borderPane.setCenter(view);
    }
}
