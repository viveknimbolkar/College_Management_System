package com.util;

import javafx.scene.control.Alert;

public class CustomAlerts {
    Alert warning = new Alert(Alert.AlertType.WARNING);
    Alert error = new Alert(Alert.AlertType.ERROR);
    Alert info = new Alert(Alert.AlertType.INFORMATION);

    public void errorAlert(String msg){
        error.setContentText(msg);
        error.show();
    }

    public void warningAlert(String msg){
        warning.setContentText(msg);
        warning.show();
    }

    public void infoAlert(String msg){
        info.setContentText(msg);
        info.show();
    }
}
