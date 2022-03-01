package com.controllers.employee;

import com.application.CommonMethods;
import com.constant.AllConstants;
import com.database.DBConnection;
import com.util.CustomAlerts;
import com.util.Validation;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.control.ChoiceBox;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.URL;
import java.sql.Blob;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ResourceBundle;

public class UpdateEmployeeController implements Initializable {

    CommonMethods commonMethods = new CommonMethods();
    Connection conn = DBConnection.getDBConnection();
    CustomAlerts alerts = new CustomAlerts();
    Validation validation = new Validation();
    Blob blob;
    byte[] imageBytes;
    File tempemployeeImage;

    @FXML
    private TextField firstname,
            middlename,
            experience,
            lastname,
            email,
            phoneno,
            address,
            qualification,
            cast,
            city,
            taluka,
            district,
            findemployeeid,
            salary,
            pincode,
            country,
            teachingsubject;
    @FXML
    private ChoiceBox<String> state, category;
    @FXML
    private ImageView employeephoto;

    public void chooseEmployeeImg() throws Exception{
        FileChooser fileChooser = new FileChooser();
        //only this type of files are allow
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("*.jpg","*.jpg"),
                new FileChooser.ExtensionFilter("*.jpeg","*.jpeg"),
                new FileChooser.ExtensionFilter("*.png","*.png")
        );
        tempemployeeImage = fileChooser.showOpenDialog(null);
        //setting image to ImageView
        InputStream inputStream = new FileInputStream(tempemployeeImage.getPath());
        Image image = new Image(inputStream);
        employeephoto.setImage(image);
    }

    //search into the database for admin ID
    public void searchEmployeeId(){
        //get the admin id to search into the database
        String employeeId = findemployeeid.getText();

        //find admin data into database
        try {
            String findEmployeeQuery = "SELECT * FROM `employee` WHERE employee_id='"+ employeeId +"' LIMIT 1";
            Statement statement = conn.createStatement();
            ResultSet resultSet = statement.executeQuery(findEmployeeQuery);

            //get values and set to the respective field
            if (resultSet.next()){
                firstname.setText(resultSet.getString("firstname"));
                middlename.setText(resultSet.getString("middlename"));
                lastname.setText(resultSet.getString("lastname"));
                email.setText(resultSet.getString("email"));
                phoneno.setText(resultSet.getString("phoneno"));
                address.setText(resultSet.getString("homeaddress"));
                city.setText(resultSet.getString("city"));
                district.setText(resultSet.getString("district"));
                country.setText(resultSet.getString("country"));
                taluka.setText(resultSet.getString("taluka"));
                qualification.setText(resultSet.getString("qualification"));
                cast.setText(resultSet.getString("cast"));
                experience.setText(resultSet.getString("experience"));
                pincode.setText(resultSet.getString("pincode"));
                salary.setText(resultSet.getString("salary"));
                category.setValue(resultSet.getString("category"));
                teachingsubject.setText(resultSet.getString("teaching_subject"));
                state.setValue(resultSet.getString("state"));

                //now retrieve student image
                tempemployeeImage = new File("src/main/resources/com/college_management_system/images" +
                        "/tempemployeeimage.png");
                FileOutputStream fileOutputStream = new FileOutputStream(tempemployeeImage);
                blob = resultSet.getBlob("employee_img");
                imageBytes = blob.getBytes(1,(int) blob.length());
                fileOutputStream.write(imageBytes);

                //set image to the imageview
                InputStream inputStream = new FileInputStream(tempemployeeImage);
                Image image = new Image(inputStream);
                employeephoto.setImage(image);

            }else {
               alerts.errorAlert("Employee Not Found.\nPlease try again!");
            }
        }catch (Exception e){
            //e.printStackTrace();
           alerts.errorAlert("Something went wrong.\nPlease try again!");
        }

    }

    //update the updated data
    public void updateEmployeeData(){
        String employeeId = findemployeeid.getText();
        //get all the data from textfield and store it into the array
        String[] employeeData = {
                firstname.getText(),
                middlename.getText(),
                lastname.getText(),
                email.getText(),
                phoneno.getText(),
                address.getText(),
                city.getText(),
                district.getText(),
                country.getText(),
                taluka.getText(),
                qualification.getText(),
                cast.getText(),
                experience.getText(),
                pincode.getText(),
                salary.getText(),
                category.getValue(),
                teachingsubject.getText(),
                state.getValue()
        };

        //check for empty fields and alert respective value
        boolean isStudentDataValid = validation.validateFieldData(employeeData);

        //if admin data is valid and no duplicate entry found then add info into main DB
        if (isStudentDataValid){
            //insert data into main database
            try {
                FileInputStream fileInputStream = new FileInputStream(tempemployeeImage);
                boolean status = commonMethods.updateClientData("com/application/employee", fileInputStream, employeeData, employeeId);
                //acknowledge user that data added successfully
                if (status){
                    Alert successAlert = new Alert(Alert.AlertType.INFORMATION);
                    successAlert.setContentText("Employee Updated Successfully!");
                    successAlert.show();
                }
            }catch (Exception e){
                //e.printStackTrace();
               alerts.errorAlert("Image size should be less than 1 MB");
            }
        }
    }


    AllConstants allConstants = new AllConstants();
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        //set state list
        state.setItems(allConstants.getStateList());
        state.show();

        //set category list
        category.setItems(allConstants.getCategories());
        category.show();
    }
}
