package com.controllers.employee;

import com.application.CommonMethods;
import com.constant.AllConstants;
import com.util.CustomAlerts;
import com.util.Validation;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.TextField;
import javafx.scene.control.DatePicker;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.net.URL;
import java.util.ResourceBundle;

public class AddNewEmployeeController implements Initializable {

    CommonMethods commonMethods = new CommonMethods();
    ToggleGroup toggleGroup = new ToggleGroup();
    CustomAlerts alerts = new CustomAlerts();
    Validation validation = new Validation();
    File employeeImg;

    @FXML
    private TextField firstname, middlename, lastname, email, phoneno, homeaddress, qualification, employeeid, cast,
            city, taluka, district, pincode,experience, country, subject;
    @FXML
    private ChoiceBox<String> state, category;
    @FXML
    private Spinner<Integer> salary;
    @FXML
    private DatePicker dob;
    @FXML
    private RadioButton male,female;
    @FXML
    private ImageView employeephoto;

    public void addNewEmployee(){
        try {
            RadioButton rb = (RadioButton) toggleGroup.getSelectedToggle();
            String gender = rb.getText(); //store gender
            //get student data and store into string array
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
                    qualification.getText(),
                    cast.getText(),
                    dob.getEditor().getText(),
                    experience.getText(),
                    pincode.getText(),
                    employeeid.getText(),
                    String.valueOf(salary.getValue()),
                    category.getValue(),
                    subject.getText(),
                    gender,
                    state.getValue()
            };

            //validate student data and also check for duplicate entry in student table
            boolean isStudentDataValid = validation.validateFieldData(studentData);
            boolean duplicateEntry = commonMethods.checkForDuplicateEntry("com/application/employee",studentData[3]);

            //if student data is valid and no duplicate record found then add studentdata into database
            if (isStudentDataValid && !duplicateEntry){
                try {
                    //get image of student
                    FileInputStream fileInputStream = new FileInputStream(employeeImg);
                    boolean addStudentData = commonMethods.addDataIntoRespectedDB("com/application/employee", fileInputStream, studentData);

                    if (addStudentData){
                        alerts.infoAlert("Employee Added Successfully!");
                    }
                }catch (Exception e){
                    //e.printStackTrace();
                    alerts.errorAlert("Image size should be less than 1 MB");
                }
            }else if (duplicateEntry){
                //if any duplicate entry found in database
                alerts.warningAlert("This employee already exists!");
            }else {
                //if any error occurs in adding data or any exception
                alerts.errorAlert("Employee data not added.\n Please try again!");
            }
        }catch (Exception e){
            //e.printStackTrace();
           alerts.errorAlert("Something went wrong.\n Select Gender\n Please try again!");
        }
    }



    public void chooseEmployeeImg() throws Exception{
        FileChooser fileChooser = new FileChooser();
        //only this type of files are allow
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter(".jpg","*.jpg"),
                new FileChooser.ExtensionFilter(".jpeg","*.jpeg"),
                new FileChooser.ExtensionFilter(".png","*.png")
        );
        employeeImg = fileChooser.showOpenDialog(null);
        //setting image to ImageView
        InputStream inputStream = new FileInputStream(employeeImg);
        Image image = new Image(inputStream);
        employeephoto.setImage(image);
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

        //set unique employee id
        employeeid.setText(String.valueOf(commonMethods.getRandomNumber()));

        //set fee values
        SpinnerValueFactory<Integer> stringSpinnerValueFactory =
                new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 21474836,100);

        salary.setValueFactory(stringSpinnerValueFactory);
        salary.setEditable(true);
    }
}
