package com.util;
// validation for different-different tasks
public class Validation {
    CustomAlerts alerts = new CustomAlerts();

    //check each and every field of user data. If it is empty then return false else true
    public boolean validateFieldData(String[] userdata){
        for (String data: userdata) {
            if (data.equals("")){
                alerts.warningAlert("Please filled up complete form!");
                return false;
            }
        }
        return true;
    }

    //verify confirm password
    public boolean verifyPassword(String password, String confirmPassword){
        //check for password validation
        if (!password.equals(confirmPassword)){
           alerts.warningAlert("Password not matched!");
            return false;
        }
        return true;
    }
}
