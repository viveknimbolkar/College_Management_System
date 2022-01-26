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

    // get cse first sem result form stage
    public void getCSEFirstSemStage(){
        menu.getExamResultSemesterwiseStage("result/cse/cse-first-sem-result.fxml","FIRST SEMESTER EXAM RESULT", 400, 600);
    }

    // get cse second sem result form stage
    public void getCSESecondSemStage(){
        menu.getExamResultSemesterwiseStage("result/cse/cse-second-sem-result.fxml","SECOND SEMESTER RESULT ", 400, 650);
    }

    // get cse second sem result form stage
    public void getCSEThirdSemStage(){
        menu.getExamResultSemesterwiseStage("result/cse/cse-third-sem-result.fxml","THIRD SEMESTER RESULT ", 400, 650);
    }

    // get cse second sem result form stage
    public void getCSEFourthSemStage(){
        menu.getExamResultSemesterwiseStage("result/cse/cse-fourth-sem-result.fxml","FOURTH SEMESTER RESULT ", 400, 650);
    }

    // get cse second sem result form stage
    public void getCSEFifthSemStage(){
        menu.getExamResultSemesterwiseStage("result/cse/cse-fifth-sem-result.fxml","FIFTH SEMESTER RESULT ", 400, 650);
    }

    // get cse second sem result form stage
    public void getCSESixthSemStage(){
        menu.getExamResultSemesterwiseStage("result/cse/cse-sixth-sem-result.fxml","SIXTH SEMESTER RESULT ", 400, 650);
    }

    // get cse second sem result form stage
    public void getCSESeventhSemStage(){
        menu.getExamResultSemesterwiseStage("result/cse/cse-seventh-sem-result.fxml","SEVENTH SEMESTER RESULT ", 400, 650);
    }

    // get cse second sem result form stage
    public void getCSEEightSemStage(){
        menu.getExamResultSemesterwiseStage("result/cse/cse-eight-sem-result.fxml","EIGHT SEMESTER RESULT ", 400, 650);
    }
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
