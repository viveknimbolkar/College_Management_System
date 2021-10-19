package com.college_management_system;
/*
This is add new admin fxml page controller
everythong related to add-new-admin.fxml page will be handle and mange here
 */
import com.college_management_system.backend.AllConstants;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.DatePicker;
import javafx.scene.control.RadioButton;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.net.URL;
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
                    country.getText(),
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
            boolean isAdminDataValid = commonMethods.validateAdminData(adminData);
            boolean verifyPassword = verifyPassword();
            boolean duplicateEntry = commonMethods.checkForDuplicateEntry("admin",adminData[0]);

            //if admin data is valid and no duplicate entry found then add info into main DB
                if (isAdminDataValid && verifyPassword && !duplicateEntry){
                    //insert data into main database
                    try {
                        FileInputStream fileInputStream = new FileInputStream(userImg);
                        boolean status = commonMethods.addDataIntoRespectedDB("admin",fileInputStream,adminData);
                        //acknowledge user that data added successfully
                        if (status){
                            Alert successAlert = new Alert(Alert.AlertType.INFORMATION);
                            successAlert.setContentText("Admin Added Successfully!");
                            successAlert.show();
                        }
                    }catch (Exception e){
                        //e.printStackTrace();
                        alert.setContentText("Image size should be less than 1MB");
                        alert.show();
                    }
                }else if (duplicateEntry){
                    alert.setContentText("This administrator already exist!");
                    alert.show();
                }
        }catch (NullPointerException e){
            //e.printStackTrace();
            alert.setContentText("Something went wrong.\nPlease try again!");
            alert.show();
        }
    }

    //verify confirm password
    public boolean verifyPassword(){
        //check for password validation
        if (!pwd1.getText().equals(pwd2.getText())){
            alert.setContentText("Password not matched!");
            alert.show();
            return false;
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
