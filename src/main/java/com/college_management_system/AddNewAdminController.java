package com.college_management_system;
/*
This is add new admin fxml page controller
everythong related to add-new-admin.fxml page will be handle and mange here
 */
import com.college_management_system.backend.AllConstants;
import com.college_management_system.backend.DBConnection;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.stage.FileChooser;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class AddNewAdminController implements Initializable {

    CommonMethods commonMethods = new CommonMethods();
    ToggleGroup toggleGroup = new ToggleGroup();
    Alert alert = new Alert(Alert.AlertType.WARNING);
    File userImg;

    @FXML
    private TextField firstname, middlename, lastname, email, phoneno, address, qualification, adminid, cast, city,
    taluka, district, pincode, country;
    @FXML
    private ChoiceBox<String> state, category;
    @FXML
    private DatePicker dob;
    @FXML
    private RadioButton male,female;
    @FXML
    private BorderPane borderpane;
    @FXML
    private ImageView adminphoto;
    @FXML
    private PasswordField pwd1, pwd2;

    //method calls when submit button event occurs
    @FXML
    protected void addNewAdmin() throws Exception{
        //get the selected gender into string formate from the toggleGroup (Typecasting)
        try {
            RadioButton rb = (RadioButton) toggleGroup.getSelectedToggle();
            String gender = rb.getText(); //store gender

        //get all the data from textfield and store it into the array
        String[] adminData = {
                email.getText(),
                pwd1.getText(),
                phoneno.getText(),
                firstname.getText(),
                middlename.getText(),
                lastname.getText(),
                address.getText(),
                city.getText(),
                district.getText(),
                taluka.getText(),
                qualification.getText(),
                state.getValue(),
                pincode.getText(),
                gender,
                adminid.getText(),
                category.getValue(),
                cast.getText(),
                dob.getEditor().getText()
        };

        //check for empty fields and alert respective value
        boolean isAdminDataValid = validateAdminData(adminData);
        boolean duplicateEntry = commonMethods.checkForDuplicateEntry("admin",adminData[0]);

        //if admin data is valid and no duplicate entry found then add info into main DB
            if (isAdminDataValid && !duplicateEntry){
                //insert data into main database
                try {
                    FileInputStream fileInputStream = new FileInputStream(userImg);
                    Connection connection = DBConnection.getDBConnection();

                    String query = "INSERT INTO `admin` (`email`, `password`, `mobile_no`, `admin_firstname`, " +
                            "`admin_middlename`, `admin_lastname`, `admin_home_address`, `admin_city`, `admin_district`, " +
                            "`admin_taluka`, `admin_qualification`, `admin_state`, `admin_pincode`, `admin_gender`, " +
                            "`admin_id`, `admin_category`, `admin_cast`, `admin_dob`, `admin_img`) VALUES (?, ?, ?, " +
                            "?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

                    PreparedStatement preparedStatement = connection.prepareStatement(query);

                    //Create prepare statements
                    for (int i = 0; i < adminData.length; i++) {
                        preparedStatement.setString(i+1,adminData[i]);
                    }

                    preparedStatement.setBinaryStream(19,fileInputStream);
                    preparedStatement.executeUpdate();
                    connection.close();

                }catch (Exception e){
                    alert.setContentText("Image size should be less than 1MB");
                    alert.show();
                }
            }
        }catch (NullPointerException e){
            alert.setContentText("Gender not selected!");
            alert.show();
        }
    }

    //check each and every field of user data. If it is empty then return false else true
    protected boolean validateAdminData(String[] userdata){
        for (String data: userdata) {
            if (data.equals("")){
                alert.setContentText("Please filled up complete form!");
                alert.show();
                return false;
            }
        }
        //check for password validation
        if (!pwd1.getText().equals(pwd2.getText())){
            alert.setContentText("Password not matched!");
            alert.show();
        }
        return true;
    }

    public void chooseAdminImg() throws Exception{
        FileChooser fileChooser = new FileChooser();
        //only this type of files are allow
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("*.jpg","*.jpg"),
                new FileChooser.ExtensionFilter("*.jpeg","*.jpeg"),
                new FileChooser.ExtensionFilter("*.png","*.png")
        );
        userImg = fileChooser.showOpenDialog(null);
        //setting image to ImageView
        InputStream inputStream = new FileInputStream(userImg.getPath());
        Image image = new Image(inputStream);
        adminphoto.setImage(image);
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

        //radio button value should be one
        male.setToggleGroup(toggleGroup);
        female.setToggleGroup(toggleGroup);

        //set random id to the admin id textfield
        adminid.setText(String.valueOf(commonMethods.getRandomNumber()));

    }
}
