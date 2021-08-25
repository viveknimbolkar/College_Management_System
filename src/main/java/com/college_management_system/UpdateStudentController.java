package com.college_management_system;

import com.college_management_system.backend.AllConstants;
import com.college_management_system.backend.DBConnection;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert;
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

public class UpdateStudentController implements Initializable {

    AllConstants allConstants = new AllConstants();
    File tempstudentImage;

    @FXML
    private TextField firstname, middlename, lastname, findstudentid,fee,cetpercentage, email, phoneno, homeaddress, cast, city,
            taluka, district,
            pincode,
            country, sscmarks,hscmarks;
    @FXML
    private ChoiceBox<String> state, category,branch,semester,educationyear;
    @FXML
    private ImageView studentphoto;


    CommonMethods commonMethods = new CommonMethods();
    Connection conn = DBConnection.getDBConnection();
    Alert alert = new Alert(Alert.AlertType.ERROR);
    Blob blob;
    byte[] imageBytes;

    //search into the database for admin ID
    public void searchStudentId(){
        //get the admin id to search into the database
        String studentId = findstudentid.getText();

        //find admin data into database
        try {
            String findStudentQuery = "SELECT * FROM `student` WHERE student_id='"+studentId+"' LIMIT 1";
            Statement statement = conn.createStatement();
            ResultSet resultSet = statement.executeQuery(findStudentQuery);

            //get values and set to the respective field
            if (resultSet.next()){

                firstname.setText(resultSet.getString("firstname"));
                middlename.setText(resultSet.getString("middlename"));
                lastname.setText(resultSet.getString("lastname"));
                email.setText(resultSet.getString("email"));
                phoneno.setText(resultSet.getString("phoneno"));
                homeaddress.setText(resultSet.getString("homeaddress"));
                city.setText(resultSet.getString("city"));
                district.setText(resultSet.getString("district"));
                country.setText(resultSet.getString("country"));
                taluka.setText(resultSet.getString("taluka"));
                pincode.setText(resultSet.getString("pincode"));
                sscmarks.setText(resultSet.getString("ssc_marks"));
                hscmarks.setText(resultSet.getString("hsc_marks"));
                category.setValue(resultSet.getString("category"));
                cast.setText(resultSet.getString("cast"));
                fee.setText(resultSet.getString("fee"));
                state.setValue(resultSet.getString("state"));
                cetpercentage.setText(resultSet.getString("mht_cet_percentile"));
                branch.setValue(resultSet.getString("branch"));
                educationyear.setValue(resultSet.getString("education_year"));
                semester.setValue(resultSet.getString("semester"));

                //now retrieve student image
                tempstudentImage = new File("src/main/resources/com/college_management_system/images" +
                        "/tempstudentimage.png");
                FileOutputStream fileOutputStream = new FileOutputStream(tempstudentImage);
                blob = resultSet.getBlob("student_img");
                imageBytes = blob.getBytes(1,(int) blob.length());
                fileOutputStream.write(imageBytes);

                //set image to the imageview
                InputStream inputStream = new FileInputStream(tempstudentImage);
                Image image = new Image(inputStream);
                studentphoto.setImage(image);

            }else {
                alert.setContentText("No student Found.\nPlease try again!");
                alert.show();
            }
        }catch (Exception e){
            e.printStackTrace();
            alert.setContentText("Something went wrong.\nPlease try again!");
            alert.show();
        }

    }

    //update the updated data
    public void updateStudentData(){

        String studentId = findstudentid.getText();

        //get all the data from textfield and store it into the array
        String[] studentData = {
                firstname.getText(),
                middlename.getText(),
                lastname.getText(),
                email.getText(),
                phoneno.getText(),
                homeaddress.getText(),
                city.getText(),
                district.getText(),
                country.getText(),
                taluka.getText(),
                pincode.getText(),
                sscmarks.getText(),
                hscmarks.getText(),
                category.getValue(),
                cast.getText(),
                fee.getText(),
                state.getValue(),
                cetpercentage.getText(),
                branch.getValue(),
                educationyear.getValue(),
                semester.getValue(),
        };


        //check for empty fields and alert respective value
        boolean isStudentDataValid = commonMethods.validateAdminData(studentData);

        //if admin data is valid and no duplicate entry found then add info into main DB
        if (isStudentDataValid){
            //insert data into main database
            try {
                FileInputStream fileInputStream = new FileInputStream(tempstudentImage);
                boolean status = commonMethods.updateClientData("student", fileInputStream, studentData, studentId);
                //acknowledge user that data added successfully
                if (status){
                    Alert successAlert = new Alert(Alert.AlertType.INFORMATION);
                    successAlert.setContentText("Student Updated Successfully!");
                    successAlert.show();
                }
            }catch (Exception e){
                e.printStackTrace();
                alert.setContentText("Image size should be less than 1 MB");
                alert.show();
            }
        }
    }

    public void chooseAdminImg(ActionEvent event) throws Exception{
        FileChooser fileChooser = new FileChooser();
        //only this type of files are allow
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("*.jpg","*.jpg"),
                new FileChooser.ExtensionFilter("*.jpeg","*.jpeg"),
                new FileChooser.ExtensionFilter("*.png","*.png")
        );
        tempstudentImage = fileChooser.showOpenDialog(null);
        //setting image to ImageView
        InputStream inputStream = new FileInputStream(tempstudentImage.getPath());
        Image image = new Image(inputStream);
        studentphoto.setImage(image);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        //set state list
        state.setItems(allConstants.getStateList());
        state.show();

        //set category list
        category.setItems(allConstants.getCategories());
        category.show();

        //set branch list
        branch.setItems(allConstants.getBranchList());
        branch.show();

        //set semester list
        semester.setItems(allConstants.getSemestersList());
        semester.show();

        //set education year list
        educationyear.setItems(allConstants.getEducationalYearList());
        educationyear.show();
    }
}
