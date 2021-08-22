package com.college_management_system;

import com.college_management_system.backend.AllConstants;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.TextField;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.RadioButton;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.stage.FileChooser;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.net.URL;
import java.util.ResourceBundle;

public class AddNewStudentController implements Initializable {

    AllConstants allConstants = new AllConstants();
    ToggleGroup toggleGroup = new ToggleGroup();
    File studentImg;

    @FXML
    private TextField firstname, middlename, lastname, email, phoneno, address, qualification, adminid, cast, city, taluka, district, pincode, country, choosephoto;
    @FXML
    private ChoiceBox<String> state, category;
    @FXML
    private DatePicker dob;
    @FXML
    private RadioButton male,female;
    @FXML
    private BorderPane borderpane;
    @FXML
    private ImageView studentphoto;

    public void chooseStudentImg(ActionEvent event) throws Exception{
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

        //radio button value should be one
        male.setToggleGroup(toggleGroup);
        female.setToggleGroup(toggleGroup);
    }
}
