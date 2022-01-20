package com.forgetpassword;

import com.application.CommonMethods;
import com.application.SceneLoader;
import com.database.DBConnection;
import com.services.SendSMS;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

public class ForgetPasswordController {

    @FXML
    private TextField contactinfo;
    @FXML
    private PasswordField newpassword, confirmpassword;
    @FXML
    private static PasswordField otp;
    @FXML
    private BorderPane borderPane;

    SceneLoader sceneLoader = new SceneLoader();
    CommonMethods commonMethods = new CommonMethods();
    String userContactInfo;
    Alert alert = new Alert(Alert.AlertType.WARNING);

    public int generatedOTP;

    //send otp
    public void sendOtp(ActionEvent event) throws Exception{
        //now search into database and find the respective admin
        userContactInfo = contactinfo.getText();
        //find the user into the database
        try{
            Connection con = DBConnection.getDBConnection();
            Statement stmt = con.createStatement();
            ResultSet result = stmt.executeQuery(
                            "SELECT * FROM `admin` WHERE email='"+userContactInfo+"' OR mobile_no='"+userContactInfo+"'LIMIT 1"
                    );

            if (result.next()){
                //get random number for otp
                generatedOTP = commonMethods.getRandomNumber();
                String otpStr = Integer.toString(generatedOTP)+" is your otp for College Management System. Please " +
                        "don't share with anyone!";
//                System.out.println(generatedOTP);
                //send otp to registerd mobile number
                SendSMS.sendSms(otpStr,result.getString(4));
                Pane view = sceneLoader.getView("forget-password-1.fxml");
                borderPane.setCenter(view);
            }else {
                alert.setContentText("User Not Found!");
                alert.show();
            }
        }catch (Exception e){
            //e.printStackTrace();
        }
    }

    //verify OTP
    public void verifyOtp(ActionEvent event)throws Exception{

        String enteredOTP = otp.getText();

        if (enteredOTP.equals(String.valueOf(otp))){
            Pane view1 = sceneLoader.getView("forget-password-2.fxml");
            borderPane.setCenter(view1);
        }else {
            alert.setContentText("OTP dosen't matched!\nPlease enter correct OTP.");
            alert.show();
        }
    }

    //reset password
    public void resetPassword(ActionEvent event)throws Exception{
        String newPassword = newpassword.getText();
        String confirmPassword = confirmpassword.getText();

        if (newPassword.equals(confirmPassword)){
            //now update the passwor do respective admin
            try {
                Connection connection = DBConnection.getDBConnection();
                String updatePassword = "UPDATE `admin` SET `password`='"+confirmPassword+"' WHERE mobile_no='"+contactinfo+"'";
                PreparedStatement preparedStatement = connection.prepareStatement(updatePassword);
                int finalResult = preparedStatement.executeUpdate();

                if (finalResult > 0){
                    alert.setAlertType(Alert.AlertType.INFORMATION);
                    alert.setContentText("Congratulations! Password Updated Successfully.");
                    alert.show();
                    commonMethods.getMainSectionWindow(event, "hello-view.fxml");
                }else {
                    alert.setContentText("Password Not Updated!\nPlease try again.");
                    alert.show();
                }

            }catch (Exception e){
                e.printStackTrace();
                alert.setContentText("Something went wrong!\nPlease try again.");
                alert.show();
            }
        }else {
            alert.setContentText("Password dosen't matched!\nPlease enter correct one.");
            alert.show();
        }
    }

    //back to the main login window
    public void backToLogin(ActionEvent event) throws Exception{
        commonMethods.getMainSectionWindow(event,"hello-view.fxml");
    }
}
