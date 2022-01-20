package com.controllers.admin;
/*
This controller is used to show specific details about admin inside a table
 */
import com.college_management_system.CommonMethods;
import com.beans.Person;
import com.database.DBConnection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

import java.io.File;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.util.ResourceBundle;

public class FindAdminController implements Initializable {

    @FXML
    private TextField adminid;
    @FXML
    private TableView<Person> table;
    @FXML
    private TableColumn<Person,String> name;
    @FXML
    private TableColumn<Person,String> details;

    Alert alert = new Alert(Alert.AlertType.WARNING);
    CommonMethods commonMethods = new CommonMethods();
    File adminDetails;

    String[] toWriteIntoPdf;

    //find admin details by search into main database
    public void findAdminDetails(ActionEvent event){
        String adminId = adminid.getText();

        try {
            Connection connection = DBConnection.getDBConnection();
            String query = "SELECT * FROM `admin` WHERE admin_id='"+adminId+"'";
            ResultSet resultSet = connection.createStatement().executeQuery(query);

            //if admin details found in database then show into the table coulums
            if (resultSet.next()){
                //store received information into array and send to Admin class to show inside tableview
                String[] getAdminDataFromDB = {
                        resultSet.getString("email"),
                        resultSet.getString("password"),
                        resultSet.getString("mobile_no"),
                        resultSet.getString("admin_firstname"),
                        resultSet.getString("admin_middlename"),
                        resultSet.getString("admin_lastname"),
                        resultSet.getString("admin_home_address"),
                        resultSet.getString("admin_city"),
                        resultSet.getString("admin_district"),
                        resultSet.getString("admin_taluka"),
                        resultSet.getString("country"),
                        resultSet.getString("admin_qualification"),
                        resultSet.getString("admin_state"),
                        resultSet.getString("admin_pincode"),
                        resultSet.getString("admin_gender"),
                        resultSet.getString("admin_id"),
                        resultSet.getString("admin_category"),
                        resultSet.getString("admin_cast"),
                        resultSet.getString("admin_dob"),
                };
                toWriteIntoPdf = getAdminDataFromDB;

                //create list to show inside table
                ObservableList<Person> list = FXCollections.observableArrayList(
                        new Person("Firstname", getAdminDataFromDB[3]),
                        new Person("Fathername", getAdminDataFromDB[4]),
                        new Person("Surname", getAdminDataFromDB[5]),
                        new Person("Email Address", getAdminDataFromDB[0]),
                        new Person("Mobile No.", getAdminDataFromDB[2]),
                        new Person("Admin ID", getAdminDataFromDB[15]),
                        new Person("Home Address", getAdminDataFromDB[6]),
                        new Person("City", getAdminDataFromDB[9]),
                        new Person("Taluka", getAdminDataFromDB[9]),
                        new Person("District", getAdminDataFromDB[8]),
                        new Person("State", getAdminDataFromDB[12]),
                        new Person("Pincode", getAdminDataFromDB[13]),
                        new Person("Country", getAdminDataFromDB[10]),
                        new Person("Qualification", getAdminDataFromDB[11]),
                        new Person("Cast", getAdminDataFromDB[17]),
                        new Person("Category", getAdminDataFromDB[16]),
                        new Person("Date of Birth", getAdminDataFromDB[18])
                );

                //set columns
                name.setCellValueFactory(new PropertyValueFactory<>("property"));
                details.setCellValueFactory(new PropertyValueFactory<>("details"));
                table.setItems(list);

            }else {
                alert.setContentText("Admin Details Not Found!\nCheck Admin ID again!");
                alert.show();
            }

        }catch (Exception e){
            //e.printStackTrace();
            alert.setAlertType(Alert.AlertType.ERROR);
            alert.setContentText("Something went Wrong\nPlease try again!");
            alert.show();
            String handle = "vivek nimbolkar";
        }
    }

    public void generatePdfFile(ActionEvent event) throws Exception{
        commonMethods.createClientPDFFile("Admin", toWriteIntoPdf, adminDetails);
    }

    @Override
    public void initialize(URL location, ResourceBundle resourceBundle){
    }
}
