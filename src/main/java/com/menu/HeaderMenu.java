package com.menu;

import com.application.MainApp;
import com.util.CustomAlerts;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class HeaderMenu {

    CustomAlerts alerts = new CustomAlerts();

    // open cse exam result form stage
    public void getExamResultSemesterwiseStage(String sceneName, String sceneTitle, int width, int height){
        try {
            Stage stage = new Stage();
            FXMLLoader fxmlLoader = new FXMLLoader(MainApp.class.getResource(sceneName));
            Scene scene = new Scene(fxmlLoader.load(), width, height);
            stage.setResizable(false);
            stage.setTitle(sceneTitle);
            stage.setScene(scene);
            stage.show();
        }catch (Exception ex){
            ex.printStackTrace();
//            alerts.errorAlert("Something went wrong.\nPlease try again!");
        }
    }

    // open about stage
    public void getAboutStage(){
        try {
            Stage stage = new Stage();
            FXMLLoader fxmlLoader = new FXMLLoader(MainApp.class.getResource("about.fxml"));
            Scene scene = new Scene(fxmlLoader.load(), 600, 400);
            stage.setResizable(false);
            stage.setX(450);
            stage.setY(250);
            stage.setTitle("About us");
            stage.setScene(scene);
            stage.show();
        }catch (Exception ex){
            //ex.printStackTrace();
            alerts.errorAlert("Something went wrong.\nPlease try again!");
        }
    }

    // open fee structure stage
    public void getFeeStructure(ActionEvent event){
        try {
            Stage stage = new Stage();
            FXMLLoader fxmlLoader = new FXMLLoader(MainApp.class.getResource("fee-structure.fxml"));
            Scene scene = new Scene(fxmlLoader.load(), 600, 400);
            stage.setResizable(false);
            stage.setX(450);
            stage.setY(250);
            stage.setTitle("Fee Structure");
            stage.setScene(scene);
            stage.show();
        }catch (Exception ex){
            //ex.printStackTrace();
            alerts.errorAlert("Something went wrong.\nPlease try again!");
        }
    }

    // menu options
    public void openLink(){
        try {
//            String url = "https://www.youtube.com/c/technovik";
//            Desktop.getDesktop().browse(URI.create(url));
            System.out.println("opened");
        }catch (Exception e){
            e.printStackTrace();
            System.out.println("error in link");
        }

    }
}
