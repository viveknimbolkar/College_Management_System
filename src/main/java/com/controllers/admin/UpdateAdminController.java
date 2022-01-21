package com.controllers.admin;

import com.application.CommonMethods;
import com.constant.AllConstants;
import com.database.DBConnection;
import com.util.CustomAlerts;
import com.util.Validation;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
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

public class UpdateAdminController implements Initializable {

    AllConstants allConstants = new AllConstants();
    ToggleGroup toggleGroup = new ToggleGroup();
    FileChooser fileChooser;
    File tempAdminImage;

    @FXML
    private TextField firstname, middlename, lastname,
    adminid, findadminid, email, phoneno, homeaddress, qualification, cast, city, taluka, district, pincode, country, pwd1, pwd2;
    @FXML
    private ChoiceBox<String> state, category;
    @FXML
    private ImageView adminphoto;

    CommonMethods commonMethods = new CommonMethods();
    Connection conn = DBConnection.getDBConnection();
    CustomAlerts alerts = new CustomAlerts();
    Validation validation = new Validation();
    Blob blob;
    byte[] imageBytes;

    //search into the database for admin ID
    public void searchAdminId(){
        //get the admin id to search into the database
        String adminId = findadminid.getText();

        //find admin data into database
        try {
            String findAdminQuery = "SELECT * FROM `admin` WHERE admin_id='"+adminId+"' LIMIT 1";
            Statement statement = conn.createStatement();
            ResultSet resultSet = statement.executeQuery(findAdminQuery);

            //get values and set to the respective field
            if (resultSet.next()){

                firstname.setText(resultSet.getString("admin_firstname"));
                middlename.setText(resultSet.getString("admin_middlename"));
                lastname.setText(resultSet.getString("admin_lastname"));
                email.setText(resultSet.getString("email"));
                phoneno.setText(resultSet.getString("mobile_no"));
                homeaddress.setText(resultSet.getString("admin_home_address"));
                city.setText(resultSet.getString("admin_city"));
                district.setText(resultSet.getString("admin_district"));
                country.setText(resultSet.getString("country"));
                taluka.setText(resultSet.getString("admin_taluka"));
                state.setValue(resultSet.getString("admin_state"));
                category.setValue(resultSet.getString("admin_category"));
                cast.setText(resultSet.getString("admin_cast"));
                adminid.setText(resultSet.getString("admin_id"));
                qualification.setText(resultSet.getString("admin_qualification"));
                pincode.setText(resultSet.getString("admin_pincode"));
                pwd1.setText(resultSet.getString("password"));
                pwd2.setText(resultSet.getString("password"));

                //now retrieve admin image
                tempAdminImage = new File("src/main/resources/com/college_management_system/images" +
                        "/tempadminimage.png");
                FileOutputStream fileOutputStream = new FileOutputStream(tempAdminImage);
                blob = resultSet.getBlob("admin_img");
                imageBytes = blob.getBytes(1,(int) blob.length());
                fileOutputStream.write(imageBytes);

                //set image to the imageview
                InputStream inputStream = new FileInputStream(tempAdminImage);
                Image image = new Image(inputStream);
                adminphoto.setImage(image);

            }else {
                alerts.warningAlert("No Admin Found.\nPlease try again!");
            }
        }catch (Exception e){
            //e.printStackTrace();
            alerts.errorAlert("Something went wrong.\nPlease try again!");
        }

    }

    //update the updated data
    public void updateAdminData() throws Exception{

        String adminId = findadminid.getText();

        //get all the data from textfield and store it into the array
        String[] adminData = {
                email.getText(),
                pwd2.getText(),
                phoneno.getText(),
                firstname.getText(),
                middlename.getText(),
                lastname.getText(),
                homeaddress.getText(),
                city.getText(),
                district.getText(),
                taluka.getText(),
                country.getText(),
                qualification.getText(),
                state.getValue(),
                pincode.getText(),
                category.getValue(),
                cast.getText(),
        };


        //check for empty fields and alert respective value
        boolean isAdminDataValid = validation.validateFieldData(adminData);
        boolean verifyPassword = validation.verifyPassword(pwd1.getText(),pwd2.getText()); //verify two passwords

        //if admin data is valid and no duplicate entry found then add info into main DB
        if (isAdminDataValid && verifyPassword){
            //insert data into main database
            try {
                FileInputStream fileInputStream = new FileInputStream(tempAdminImage);
                boolean status = commonMethods.updateClientData("admin",fileInputStream,adminData, adminId);
                //acknowledge user that data added successfully
                if (status){
                    Alert successAlert = new Alert(Alert.AlertType.INFORMATION);
                    successAlert.setContentText("Admin Updated Successfully!");
                    successAlert.show();
                }
            }catch (Exception e){
                //e.printStackTrace();
                alerts.errorAlert("Image size should be less than 1 MB");
            }
        }
    }

    public void chooseAdminImg() throws Exception{
        FileChooser fileChooser = new FileChooser();
        //only this type of files are allow
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("*.jpg","*.jpg"),
                new FileChooser.ExtensionFilter("*.jpeg","*.jpeg"),
                new FileChooser.ExtensionFilter("*.png","*.png")
        );
        tempAdminImage = fileChooser.showOpenDialog(null);
        //setting image to ImageView
        InputStream inputStream = new FileInputStream(tempAdminImage.getPath());
        Image image = new Image(inputStream);
        adminphoto.setImage(image);
    }

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
