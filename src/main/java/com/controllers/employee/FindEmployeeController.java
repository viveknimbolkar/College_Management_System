package com.controllers.employee;

import com.application.CommonMethods;
import com.database.DBConnection;
import com.beans.Person;
import com.tools.CreatePDF;
import com.util.CustomAlerts;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.FileChooser;

import java.io.File;
import java.sql.Connection;
import java.sql.ResultSet;

public class FindEmployeeController {

    @FXML
    private TextField employeeid;
    @FXML
    private TableView<Person> table;
    @FXML
    private TableColumn<Person,String> name;
    @FXML
    private TableColumn<Person,String> details;

    CustomAlerts alerts = new CustomAlerts();
    CreatePDF createPDF = new CreatePDF();
    File employeeDetails;
    String[] toWriteIntoPdf;

    //find employee
    public void findEmployeeDetails(){
        String employeeId = employeeid.getText();
        try {
            Connection connection = DBConnection.getDBConnection();
            String query = "SELECT * FROM `employee` WHERE employee_id='"+employeeId+"'";
            ResultSet resultSet = connection.createStatement().executeQuery(query);

            //if admin details found in database then show into the table coulums
            if (resultSet.next()){
                //store received information into array and send to Admin class to show inside tableview
                String[] getEmployeeDataFromDB = {
                        resultSet.getString("firstname"),
                        resultSet.getString("middlename"),
                        resultSet.getString("lastname"),
                        resultSet.getString("email"),
                        resultSet.getString("employee_id"),
                        resultSet.getString("phoneno"),
                        resultSet.getString("homeaddress"),
                        resultSet.getString("city"),
                        resultSet.getString("taluka"),
                        resultSet.getString("district"),
                        resultSet.getString("pincode"),
                        resultSet.getString("state"),
                        resultSet.getString("country"),
                        resultSet.getString("qualification"),
                        resultSet.getString("gender"),
                        resultSet.getString("category"),
                        resultSet.getString("cast"),
                        resultSet.getString("dob"),
                };

                toWriteIntoPdf = getEmployeeDataFromDB;

                //create list to show inside table
                ObservableList<Person> list = FXCollections.observableArrayList(
                        new Person("Firstname",getEmployeeDataFromDB[0]),
                        new Person("Fathername",getEmployeeDataFromDB[1]),
                        new Person("Surname",getEmployeeDataFromDB[2]),
                        new Person("Email Address",getEmployeeDataFromDB[3]),
                        new Person("Employee ID",getEmployeeDataFromDB[4]),
                        new Person("Mobile No.",getEmployeeDataFromDB[5]),
                        new Person("Home Address",getEmployeeDataFromDB[6]),
                        new Person("City",getEmployeeDataFromDB[7]),
                        new Person("Taluka",getEmployeeDataFromDB[8]),
                        new Person("District",getEmployeeDataFromDB[9]),
                        new Person("Pincode",getEmployeeDataFromDB[10]),
                        new Person("State",getEmployeeDataFromDB[11]),
                        new Person("Country",getEmployeeDataFromDB[12]),
                        new Person("Qualification",getEmployeeDataFromDB[13]),
                        new Person("Gender",getEmployeeDataFromDB[14]),
                        new Person("Category",getEmployeeDataFromDB[15]),
                        new Person("Cast",getEmployeeDataFromDB[16]),
                        new Person("Date of Birth",getEmployeeDataFromDB[17])
                );

                //set columns
                name.setCellValueFactory(new PropertyValueFactory<>("property"));
                details.setCellValueFactory(new PropertyValueFactory<>("details"));
                table.setItems(list);

            }else {
                alerts.warningAlert("Employee Details Not Found!\nCheck Employee ID again!");
            }
        }catch (Exception e){
            //e.printStackTrace();
            alerts.errorAlert("Something went Wrong\nPlease try again!");
        }
    }

    //generate pdf file of student details
    public void generatePdfFile() throws Exception{
        FileChooser fileChooser = new FileChooser();
        //only this type of files are allow
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter(".pdf","*.pdf")

        );
        employeeDetails = fileChooser.showSaveDialog(null);
        createPDF.createClientPDFFile("Employee", toWriteIntoPdf, employeeDetails);
    }
}
