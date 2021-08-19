package com.college_management_system;

import com.college_management_system.backend.AllConstants;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.layout.BorderPane;
import java.net.URL;
import java.util.ResourceBundle;

public class AddNewEmployeeController implements Initializable {

    CommonMethods commonMethods = new CommonMethods();
    ToggleGroup toggleGroup = new ToggleGroup();

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

    public void sendDataToAddIntoDB(){
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
    }
}
