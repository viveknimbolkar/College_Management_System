package com.college_management_system;

import com.college_management_system.backend.DBConnection;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputDialog;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

import javafx.scene.Scene;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import javafx.scene.Parent;

public class MainController{

    TextInputDialog tid = new TextInputDialog();
    Alert alert = new Alert(Alert.AlertType.NONE);
    CommonMethods commonMethods = new CommonMethods();

    @FXML
    private TextField email;
    @FXML
    private PasswordField pwd;
    @FXML
    private BorderPane borderpane;

    SceneLoader sceneLoader = new SceneLoader();

    //login logic for the main login window
    @FXML
    public void Login(ActionEvent e) {
        //get the credentials from user
        String useremail = email.getText();
        String userpwd = pwd.getText();

        try {
            //search into database for correct credentials
            Connection con = DBConnection.getDBConnection();
            Statement stmt = con.createStatement();
            ResultSet result =
                    stmt.executeQuery(
                            "SELECT * FROM `admin` WHERE email='"+useremail+"' AND password='"+userpwd+"'LIMIT 1"
                    );

            //if admin found
            if (result.next()){
                //Uncomment this code for without sms authentication (login)
                 Parent root = FXMLLoader.load((getClass().getResource("main-switch.fxml")));
                 Stage stage = (Stage) ((Node) e.getSource()).getScene().getWindow();
                 Scene scene = new Scene(root,600,400);
                 stage.setScene(scene);
                 stage.show();

//===============================================================================
// Uncomment this code to use sms authentication
//                int otp = commonMethods.getRandomNumber();
//                //System.out.println(otp);
//
//                String otpStr = Integer.toString(otp)+" is your otp for CMS. Please don't share with anyone!";
//                //send otp to registerd mobile number
//                SendSMS.sendSms(otpStr,result.getString(4));
//
//                //open dialog box to enter otp
//                tid.setHeaderText("Enter otp sent on your mobile number");
//                tid.showAndWait();
//
//                if (tid.getEditor().getText().equals(Integer.toString(otp))){
//                    //IF LOGIN SUCCESSFUL
//                    //System.out.println("Otp matched! Login successful.");
//                    //change scene
//                    Parent root1 = FXMLLoader.load((getClass().getResource("main-switch.fxml")));
//                    Stage stage1 = (Stage) ((Node) e.getSource()).getScene().getWindow();
//                    Scene scene1 = new Scene(root1,600,400);
//                    stage1.setScene(scene1);
//                    stage1.show();
//                }else {
//                    //IF LOGIN FAILS
//                    //System.out.println("NOT matched. ERROR");
//                    alert.setAlertType(Alert.AlertType.ERROR);
//                    alert.setContentText("OTP Not Matched! Please try again.");
//                }
//comment upto this

            }else {
                //if admin not found
                alert.setAlertType(Alert.AlertType.ERROR);
                //set alert if no admin found
                alert.setContentText("Email address or Password dosen't matched! Please enter correct one.");
                alert.show();
            }
            con.close();

        }catch (Exception ex){
            ex.printStackTrace();
            alert.setAlertType(Alert.AlertType.ERROR);
            alert.setContentText("Internal Server Error!\n Please try again later.");
            alert.show();
        }
    }

    //open forget password section
    public void openForgetPasswordSection(ActionEvent event) throws Exception{
        Parent root = FXMLLoader.load((getClass().getResource("forget-password.fxml")));
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root,600,400);
        stage.setScene(scene);
        stage.show();
    }

    //create a random 6 digit number
    protected int getRandomNumber(){
        final int minNum = 100000;
        final int maxNum = 999999;
        return (int) (Math.random() * (maxNum - minNum+1) + minNum);
    }

    public void enterKeyPressed(KeyEvent keyEvent) {
    }
}