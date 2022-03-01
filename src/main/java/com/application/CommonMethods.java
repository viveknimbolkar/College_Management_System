package com.application;

import com.database.DBConnection;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Stage;
import com.constant.AllConstants;

import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

public class CommonMethods {

    AllConstants allConstants = new AllConstants();
    Alert alert = new Alert(Alert.AlertType.WARNING);
    Connection conn = DBConnection.getDBConnection();

    Parent root;
    Stage stage;
    Scene scene;

//=============GENERAL OPERATIONS======================================================================
//create a random 6 digit number
    public int getRandomNumber(){
        final int minNum = 100000;
        final int maxNum = 999999;
        return (int) (Math.random() * (maxNum - minNum+1) + minNum);
    }

    //get the main section window
    public void getMainSectionWindow(ActionEvent e, String fxmlName) throws Exception{
        root = FXMLLoader.load((getClass().getResource(fxmlName)));
        stage = (Stage) ((Node)e.getSource()).getScene().getWindow();
        scene = new Scene(root,allConstants.MAIN_SWITCH_WIDTH,allConstants.MAIN_SWITCH_HEIGHT);
        stage.setScene(scene);
        stage.show();
    }


//=============DATABASE RELATED OPERATIONS=======================================================================

    /*it will check the duplicate entry in main db (admin, student, employee)
    This method will return true if any duplicate record found in main database else it will return false
    */
    public boolean checkForDuplicateEntry(String forUser, String email) throws Exception{

        String query = switch (forUser) {
            case "admin" -> "SELECT * FROM `admin` WHERE email='" + email + "'";
            case "student" -> "SELECT * FROM `student` WHERE email='" + email + "'";
            case "com/application/employee" -> "SELECT * FROM `employee` WHERE email='" + email + "'";
            default -> "";
        };

        Statement statement = conn.createStatement();
        ResultSet resultSet = statement.executeQuery(query);
        return resultSet.next();
    }


    /*
    This method add data into main database depends on the type of client
    i.e. admin, student or employee
     */
    public boolean addDataIntoRespectedDB(String client, FileInputStream clientImage, String[] clientData) throws Exception{
        //change query according to the client respective database
        String insertQuery = switch (client) {
            case "admin" -> "INSERT INTO `admin` (`email`, `password`, `mobile_no`, `admin_firstname`, " +
                    "`admin_middlename`, `admin_lastname`, `admin_home_address`, `admin_city`, `admin_district`, " +
                    "`admin_taluka`, `country`, `admin_qualification`, `admin_state`, `admin_pincode`,`admin_gender`, " +
                    "`admin_id`, `admin_category`, `admin_cast`, `admin_dob`, `admin_img`) VALUES (?, ?, ?, " +
                    "?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

            case "student" -> "INSERT INTO `student` (`firstname`, `middlename`, `lastname`, `email`, " +
                    "`phoneno`, `homeaddress`, `city`, `district`, `country`, `taluka`,`pincode`, `ssc_marks`, " +
                    "`hsc_marks`,`category`,`cast`, `student_id`, `fee`, `state`, `mht_cet_percentile`, `branch`, `education_year`," +
                    " `semester`, `gender`, `dob`, `student_img`) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, " +
                    "?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

            case "com/application/employee" -> "INSERT INTO `employee` (`firstname`, `middlename`, `lastname`, `email`, " +
                    "`phoneno`, `homeaddress`, `city`, `district`, `country`, `taluka`, `qualification`, `cast`, " +
                    "`dob`, `experience`, `pincode`, `employee_id`, `salary`, `category`, `teaching_subject`, " +
                    "`gender`, `state`, `employee_img`) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?," +
                    " ?, ?, ?, ?)";
            default -> "";
        };

        PreparedStatement preparedStatement = conn.prepareStatement(insertQuery);
        //assign respected values to the preparestatement
        for (int i = 0; i < clientData.length; i++) {
            preparedStatement.setString(i+1, clientData[i]);
        }

        //setting image to the respected client's query
        switch (client) {
            case "admin" -> preparedStatement.setBinaryStream(20, clientImage);
            case "student" -> preparedStatement.setBinaryStream(25, clientImage);
            case "com/application/employee" -> preparedStatement.setBinaryStream(22, clientImage);
        }

        int result = preparedStatement.executeUpdate();
        //if more than 0 rows affected then return true else false
        return result > 0;
    }


    //This method is used to remove particula user from the database
    public boolean removeClientDataFromDB(String client, String clientId) throws Exception{
        String deleteQuery = switch (client) {
            case "admin" -> "DELETE FROM `admin` WHERE admin_id='" + clientId + "'";
            case "student" -> "DELETE FROM `student` WHERE student_id='" + clientId + "'";
            case "com/application/employee" -> "DELETE FROM `employee` WHERE employee_id='" + clientId + "'";
            default -> "";
        };

        Statement statement = conn.createStatement();
        int result = statement.executeUpdate(deleteQuery);

        if (result > 0){
            alert.setAlertType(Alert.AlertType.INFORMATION);
            alert.setContentText(client+" deleted successfully!");
        }else {
            alert.setAlertType(Alert.AlertType.ERROR);
            alert.setContentText(client+" is not deleted!\nPlease try again!");
        }
        alert.show();
        return true;
    }


    /*
    This method is used to update client data
    we are passing an array of cliend data , clientname and image
     */
    public boolean updateClientData(String clientname,FileInputStream clientImg, String[] clientData, String clientID){
        String updateQuery = switch (clientname) {
            case "admin" -> "UPDATE `admin` SET `email`=?,`password`=?," +
                    "`mobile_no`=?,`admin_firstname`=?,`admin_middlename`=?," +
                    "`admin_lastname`=?,`admin_home_address`=?,`admin_city`=?," +
                    "`admin_district`=?,`admin_taluka`=?,`country`=?," +
                    "`admin_qualification`=?,`admin_state`=?,`admin_pincode`=?, `admin_category`=?," +
                    "`admin_cast`=?, `admin_img`=? WHERE `admin_id`='" + clientID + "'";

            case "student" -> "UPDATE `student` SET `firstname`=?, `middlename`=?, `lastname`=?, `email`=?, " +
                    "`phoneno`=?, `homeaddress`=?, " +
                    "`city`=?, `district`=?, `country`=?, `taluka`=?, `pincode`=?, `ssc_marks`=?, `hsc_marks`=?, " +
                    "`category`=?, `cast`=?, `fee`=?, `state`=?, `mht_cet_percentile`=?, `branch`=?, " +
                    "`education_year`=?, `semester`=?, `student_img`=? WHERE `student_id`='" + clientID + "'";

            case "com/application/employee" -> "UPDATE `employee` SET `firstname`=?, `middlename`=?, `lastname`=?, `email`=?, " +
                    "`phoneno`=?, `homeaddress`=?, " +
                    "`city`=?, `district`=?, `country`=?, `taluka`=?, `qualification`=?, `cast`=?, " +
                    "`experience`=?, " +
                    "`pincode`=?, " +
                    "`salary`=?, `category`=?, `teaching_subject`=?, `state`=?, `employee_img`=? WHERE `employee_id`='" + clientID + "'";
            default -> "";
        };

        try {
            PreparedStatement preparedStatement = conn.prepareStatement(updateQuery);
            //setting values to the prepareStatement
            for (int i = 0; i < clientData.length; i++) {
                preparedStatement.setString(i+1,clientData[i]);
            }

            //setting image to the respected client's query
            switch (clientname) {
                case "admin" -> preparedStatement.setBinaryStream(17, clientImg);
                case "student" -> preparedStatement.setBinaryStream(22, clientImg);
                case "com/application/employee" -> preparedStatement.setBinaryStream(19, clientImg);
            }

            int finalResult = preparedStatement.executeUpdate();
            return finalResult > 0;

        }catch (Exception e){
            alert.setContentText("Something Went Wrong\nPlease try again.");
            alert.show();
        }
        return false;
    }
}
