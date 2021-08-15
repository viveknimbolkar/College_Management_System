package com.college_management_system;

import javafx.event.ActionEvent;

public class EmployeeSectionController {

    CommonMethods commonMethods = new CommonMethods();

    public void goBack(ActionEvent event) throws Exception{
        commonMethods.getMainSectionWindow(event,"main-switch.fxml");
    }
}
