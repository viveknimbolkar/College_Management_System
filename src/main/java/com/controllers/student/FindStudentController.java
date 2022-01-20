package com.controllers.student;
/*
This controller is used to show specific details about Student inside a table
 */
import com.application.CommonMethods;
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
import javafx.stage.FileChooser;

import java.io.File;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.util.ResourceBundle;

public class FindStudentController implements Initializable {

    @FXML
    private TextField studentid;
    @FXML
    private TableView<Person> table;
    @FXML
    private TableColumn<Person,String> name;
    @FXML
    private TableColumn<Person,String> details;

    Alert alert = new Alert(Alert.AlertType.WARNING);
    CommonMethods commonMethods = new CommonMethods();
    File studentDetails;
    String[] toWriteIntoPdf;

    //find admin details by search into main database
    public void findStudentDetails(ActionEvent event){

        String studentId = studentid.getText();

        try {
            Connection connection = DBConnection.getDBConnection();
            String query = "SELECT * FROM `student` WHERE student_id='"+studentId+"'";
            ResultSet resultSet = connection.createStatement().executeQuery(query);

            //if admin details found in database then show into the table coulums
            if (resultSet.next()){
                //store received information into array and send to Admin class to show inside tableview
                String[] getStudentDataFromDB = {
                        resultSet.getString("firstname"),
                        resultSet.getString("middlename"),
                        resultSet.getString("lastname"),
                        resultSet.getString("email"),
                        resultSet.getString("phoneno"),
                        resultSet.getString("student_id"),
                        resultSet.getString("homeaddress"),
                        resultSet.getString("city"),
                        resultSet.getString("taluka"),
                        resultSet.getString("district"),
                        resultSet.getString("state"),
                        resultSet.getString("pincode"),
                        resultSet.getString("country"),
                        resultSet.getString("cast"),
                        resultSet.getString("gender"),
                        resultSet.getString("category"),
                        resultSet.getString("dob"),
                        resultSet.getString("mht_cet_percentile"),
                        resultSet.getString("branch"),
                        resultSet.getString("education_year"),
                        resultSet.getString("semester"),
                        resultSet.getString("fee"),
                        resultSet.getString("ssc_marks"),
                        resultSet.getString("hsc_marks"),
                };

                toWriteIntoPdf = getStudentDataFromDB;

                //create list to show inside table
                ObservableList<Person> list = FXCollections.observableArrayList(
                        new Person("Firstname", getStudentDataFromDB[0]),
                        new Person("Fathername", getStudentDataFromDB[1]),
                        new Person("Surname", getStudentDataFromDB[2]),
                        new Person("Email Address", getStudentDataFromDB[3]),
                        new Person("Mobile No.", getStudentDataFromDB[4]),
                        new Person("Student ID", getStudentDataFromDB[5]),
                        new Person("Home Address", getStudentDataFromDB[6]),
                        new Person("City", getStudentDataFromDB[7]),
                        new Person("Taluka", getStudentDataFromDB[8]),
                        new Person("District", getStudentDataFromDB[9]),
                        new Person("State", getStudentDataFromDB[10]),
                        new Person("Pincode", getStudentDataFromDB[11]),
                        new Person("Country", getStudentDataFromDB[12]),
                        new Person("Cast", getStudentDataFromDB[13]),
                        new Person("Gender", getStudentDataFromDB[14]),
                        new Person("Category", getStudentDataFromDB[15]),
                        new Person("Date of Birth", getStudentDataFromDB[16]),
                        new Person("MHT-CET Percentile", getStudentDataFromDB[17]),
                        new Person("Branch", getStudentDataFromDB[18]),
                        new Person("Education Year", getStudentDataFromDB[19]),
                        new Person("Semester", getStudentDataFromDB[20]),
                        new Person("Fee Paid", getStudentDataFromDB[21]),
                        new Person("SSC Marks", getStudentDataFromDB[22]),
                        new Person("HSC Marks", getStudentDataFromDB[23])
                );

                //set columns
                name.setCellValueFactory(new PropertyValueFactory<>("property"));
                details.setCellValueFactory(new PropertyValueFactory<>("details"));
                table.setItems(list);

            }else {
                alert.setContentText("Student Details Not Found!\nCheck Student ID again!");
                alert.show();
            }
        }catch (Exception e){
            e.printStackTrace();
            alert.setAlertType(Alert.AlertType.ERROR);
            alert.setContentText("Something went Wrong\nPlease try again!");
            alert.show();

        }
    }


    //generate pdf file of student details
    public void generatePdfFile(ActionEvent event) throws Exception{
        FileChooser fileChooser = new FileChooser();
        //only this type of files are allow
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter(".pdf","*.pdf")

        );
        studentDetails = fileChooser.showSaveDialog(null);
        commonMethods.createClientPDFFile("Student", toWriteIntoPdf, studentDetails);
    }

    @Override
    public void initialize(URL location, ResourceBundle resourceBundle){
    }
}
