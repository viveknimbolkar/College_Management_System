package com.application;

import com.menu.HeaderMenu;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;

public class AdminSectionController {

    @FXML
    private BorderPane borderPane;

    CommonMethods commonMethods = new CommonMethods();
    SceneLoader sceneLoader = new SceneLoader();
    HeaderMenu menu = new HeaderMenu();

    // menu options
    public void openLink(){
        menu.openLink();
    }

    // open about stage
    public void aboutStage(){
        menu.getAboutStage();
    }

    //open fee structure
    public void feeStructure(ActionEvent e){
        menu.getFeeStructure(e);
    }

    //go back to the main section
    public void goBack(ActionEvent event) throws Exception{
        commonMethods.getMainSectionWindow(event,"main-switch.fxml");
    }

    //add admin scene
    public void addAdminView() throws Exception{
        Pane view = sceneLoader.getView("add-new-admin.fxml");
        borderPane.setCenter(view);
    }

    //update admin scene
    public void updateAdminView() throws Exception{
        Pane view = sceneLoader.getView("update-admin.fxml");
        borderPane.setCenter(view);
    }

    //find admin scene
    public void findAdminView() throws Exception{
        Pane view = sceneLoader.getView("find-admin.fxml");
        borderPane.setCenter(view);
    }

    //remove admin scene
    public void removeAdminView() throws Exception{
        Pane view = sceneLoader.getView("remove-admin.fxml");
        borderPane.setCenter(view);
    }

    //Insights scene
    public void insightsView() throws Exception{
        Pane view = sceneLoader.getView("admin-dashboard.fxml");
        borderPane.setCenter(view);
    }

    //Notification scene
    public void notificationView() throws Exception{
        Pane view = sceneLoader.getView("notifications.fxml");
        borderPane.setCenter(view);
    }
}
