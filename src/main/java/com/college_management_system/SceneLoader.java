package com.college_management_system;
/*
This class used to get a respected page to show into admin section scene.
This class will return object of Pane to show into borderview of amdin scene.
 */

import javafx.fxml.FXMLLoader;
import javafx.scene.layout.Pane;

import java.net.URL;

public class SceneLoader {
    private Pane view;

    public Pane getView(String fxmlName) throws Exception{

        //get url of fxml file as an argument
        URL fxmlURL = AdminSectionController.class.getResource(fxmlName);

        if (fxmlName == null){
            throw new java.io.FileNotFoundException("Fxml file not found");
        }
        view = new FXMLLoader().load(fxmlURL);
        return view;
    }
}
