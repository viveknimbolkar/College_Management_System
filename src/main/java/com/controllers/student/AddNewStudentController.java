package com.controllers.student;

import com.application.CommonMethods;
import com.constant.AllConstants;
import com.database.DBConnection;
import com.util.CustomAlerts;
import com.util.Validation;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.TextField;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.RadioButton;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.time.LocalDate;
import java.util.ResourceBundle;

public class AddNewStudentController implements Initializable {

    AllConstants allConstants = new AllConstants();
    ToggleGroup toggleGroup = new ToggleGroup();
    CommonMethods commonMethods = new CommonMethods();
    CustomAlerts alerts = new CustomAlerts();
    Validation validation = new Validation();
    CustomAlerts alert = new CustomAlerts();
    File studentImg;

    @FXML
    private TextField firstname, middlename, lastname, email, phoneno, homeaddress, cast, city,
    taluka, district, cetpercentage, pincode, studentid, country, sscmarks, hscmarks;
    @FXML
    private ChoiceBox<String> state, category, educationyear, semester, branch;
    @FXML
    private DatePicker dob;
    @FXML
    private Spinner<Integer> fee;
    @FXML
    private RadioButton male,female;
    @FXML
    private ImageView studentphoto;

    //add new student into database
    public void addNewStudent(){

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
                    pincode.getText(),
                    sscmarks.getText(),
                    hscmarks.getText(),
                    category.getValue(),
                    cast.getText(),
                    studentid.getText(),
                    String.valueOf(fee.getValue()),
                    state.getValue(),
                    cetpercentage.getText(),
                    branch.getValue(),
                    educationyear.getValue(),
                    semester.getValue(),
                    gender,
                    dob.getEditor().getText()
            };

            //validate student data and also check for duplicate entry in student table
            boolean isStudentDataValid = validation.validateFieldData(studentData);
            boolean duplicateEntry = commonMethods.checkForDuplicateEntry("student",studentData[3]);

            //if student data is valid and no duplicate record found then add studentdata into database
            if (isStudentDataValid && !duplicateEntry){
                try {
                    // make entry into fee table
                    LocalDate currentDate = LocalDate.now();
                    Connection connection = DBConnection.getDBConnection();
                    String saveFeesQuery = "INSERT INTO `fees` (`student_name`, `student_id`, `fee_paid`, `branch`, " +
                            "`semester`, `education_year`, `date`) VALUES (?, ?, ?, ?, ?, ?,'"+currentDate+"')";
                    PreparedStatement prepStmt = connection.prepareStatement(saveFeesQuery);

                    prepStmt.setString(1,studentData[0]+studentData[1]+studentData[2]);
                    prepStmt.setString(2,studentData[15]);
                    prepStmt.setString(3,studentData[16]);
                    prepStmt.setString(4,studentData[19]);
                    prepStmt.setString(5,studentData[21]);
                    prepStmt.setString(6,studentData[20]);

                    int queryStatus = prepStmt.executeUpdate();
                    if (queryStatus == 1){
                        alert.infoAlert("Fee paid successfully!");
                    }else {
                        alert.warningAlert("Fees Not Paid. Try again!");
                        return;
                    }

                    //get image of student
                    FileInputStream fileInputStream = new FileInputStream(studentImg);
                    boolean addStudentData = commonMethods.addDataIntoRespectedDB("student", fileInputStream, studentData);

                    if (addStudentData){
                        alerts.infoAlert("Student Added Successfully!");
                    }
                }catch (Exception e){
                    //e.printStackTrace();
                    alerts.errorAlert("Image size should be less than 1MB");
                }
            }else if (duplicateEntry){
                //if any duplicate entry found in database
                alerts.warningAlert("This student already exists!");
            }else {
                //if any error occurs in adding data or any exception
               alerts.errorAlert("Student data not added.\n Please try again!");
            }
        }catch (Exception e){
            //e.printStackTrace();
            alerts.errorAlert("Something went wrong.\n Select Gender\n Please try again!");
        }
    }

    public void chooseStudentImg() throws Exception{
        FileChooser fileChooser = new FileChooser();
        //only this type of files are allow
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter(".jpg","*.jpg"),
                new FileChooser.ExtensionFilter(".jpeg","*.jpeg"),
                new FileChooser.ExtensionFilter(".png","*.png")
        );
        studentImg = fileChooser.showOpenDialog(null);
        //setting image to ImageView
        InputStream inputStream = new FileInputStream(studentImg);
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

        //set semester list
        educationyear.setItems(allConstants.getEducationalYearList());
        educationyear.show();

        //radio button value should be one
        male.setToggleGroup(toggleGroup);
        female.setToggleGroup(toggleGroup);

        //set student id
        studentid.setText(String.valueOf(commonMethods.getRandomNumber()));

        //set fee values
        SpinnerValueFactory<Integer> stringSpinnerValueFactory =
                new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 21474836,100);

        fee.setValueFactory(stringSpinnerValueFactory);
        fee.setEditable(true);
    }
}
