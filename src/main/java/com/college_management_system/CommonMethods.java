package com.college_management_system;

import com.database.DBConnection;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Stage;
import com.constant.AllConstants;
import com.itextpdf.text.Document;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

import java.io.File;
import java.io.FileOutputStream;
import java.util.stream.Stream;

import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

public class CommonMethods {

    AllConstants allConstants = new AllConstants();
    Alert alert = new Alert(Alert.AlertType.WARNING);
    Connection conn = DBConnection.getDBConnection();

    Parent root;
    Stage stage;
    Scene scene;

//=============GENERAL OPERATIONS======================================================================
//create a random 6 digit number
    public int getRandomNumber(){
        final int minNum = 100000;
        final int maxNum = 999999;
        return (int) (Math.random() * (maxNum - minNum+1) + minNum);
    }

    //check each and every field of user data. If it is empty then return false else true
    public boolean validateAdminData(String[] userdata){
        for (String data: userdata) {
            if (data.equals("")){
                alert.setContentText("Please filled up complete form!");
                alert.show();
                return false;
            }
        }
        return true;
    }

    //get the main section window
    public void getMainSectionWindow(ActionEvent e, String fxmlName) throws Exception{
        root = FXMLLoader.load((getClass().getResource(fxmlName)));
        stage = (Stage) ((Node)e.getSource()).getScene().getWindow();
        scene = new Scene(root,allConstants.MAIN_SWITCH_WIDTH,allConstants.MAIN_SWITCH_HEIGHT);
        stage.setScene(scene);
        stage.show();
    }

    //verify confirm password
    public boolean verifyPassword(String password, String confirmPassword){
        //check for password validation
        if (!password.equals(confirmPassword)){
            alert.setContentText("Password not matched!");
            alert.show();
            return false;
        }
        return true;
    }


//=============DATABASE RELATED OPERATIONS=======================================================================

    /*it will check the duplicate entry in main db (admin, student, employee)
    This method will return true if any duplicate record found in main database else it will return false
    */
    public boolean checkForDuplicateEntry(String forUser, String email) throws Exception{

        String query = switch (forUser) {
            case "admin" -> "SELECT * FROM `admin` WHERE email='" + email + "'";
            case "student" -> "SELECT * FROM `student` WHERE email='" + email + "'";
            case "employee" -> "SELECT * FROM `employee` WHERE email='" + email + "'";
            default -> "";
        };

        Statement statement = conn.createStatement();
        ResultSet resultSet = statement.executeQuery(query);
        return resultSet.next();
    }


    /*
    This method add data into main database depends on the type of client
    i.e. admin, student or employee
     */
    public boolean addDataIntoRespectedDB(String client, FileInputStream clientImage, String[] clientData) throws Exception{
        //change query according to the client respective database
        String insertQuery = switch (client) {
            case "admin" -> "INSERT INTO `admin` (`email`, `password`, `mobile_no`, `admin_firstname`, " +
                    "`admin_middlename`, `admin_lastname`, `admin_home_address`, `admin_city`, `admin_district`, " +
                    "`admin_taluka`, `country`, `admin_qualification`, `admin_state`, `admin_pincode`,`admin_gender`, " +
                    "`admin_id`, `admin_category`, `admin_cast`, `admin_dob`, `admin_img`) VALUES (?, ?, ?, " +
                    "?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

            case "student" -> "INSERT INTO `student` (`firstname`, `middlename`, `lastname`, `email`, " +
                    "`phoneno`, `homeaddress`, `city`, `district`, `country`, `taluka`,`pincode`, `ssc_marks`, " +
                    "`hsc_marks`,`category`,`cast`, `student_id`, `fee`, `state`, `mht_cet_percentile`, `branch`, `education_year`," +
                    " `semester`, `gender`, `dob`, `student_img`) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, " +
                    "?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

            case "employee" -> "INSERT INTO `employee` (`firstname`, `middlename`, `lastname`, `email`, " +
                    "`phoneno`, `homeaddress`, `city`, `district`, `country`, `taluka`, `qualification`, `cast`, " +
                    "`dob`, `experience`, `pincode`, `employee_id`, `salary`, `category`, `teaching_subject`, " +
                    "`gender`, `state`, `employee_img`) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?," +
                    " ?, ?, ?, ?)";
            default -> "";
        };

        PreparedStatement preparedStatement = conn.prepareStatement(insertQuery);
        //assign respected values to the preparestatement
        for (int i = 0; i < clientData.length; i++) {
            preparedStatement.setString(i+1, clientData[i]);
        }

        //setting image to the respected client's query
        switch (client) {
            case "admin" -> preparedStatement.setBinaryStream(20, clientImage);
            case "student" -> preparedStatement.setBinaryStream(25, clientImage);
            case "employee" -> preparedStatement.setBinaryStream(22, clientImage);
        }

        int result = preparedStatement.executeUpdate();
        //if more than 0 rows affected then return true else false
        return result > 0;
    }


    //This method is used to remove particula user from the database
    public boolean removeClientDataFromDB(String client, String clientId) throws Exception{
        String deleteQuery = switch (client) {
            case "admin" -> "DELETE FROM `admin` WHERE admin_id='" + clientId + "'";
            case "student" -> "DELETE FROM `student` WHERE student_id='" + clientId + "'";
            case "employee" -> "DELETE FROM `employee` WHERE employee_id='" + clientId + "'";
            default -> "";
        };

        Statement statement = conn.createStatement();
        int result = statement.executeUpdate(deleteQuery);

        if (result > 0){
            alert.setAlertType(Alert.AlertType.INFORMATION);
            alert.setContentText(client+" deleted successfully!");
        }else {
            alert.setAlertType(Alert.AlertType.ERROR);
            alert.setContentText(client+" is not deleted!\nPlease try again!");
        }
        alert.show();
        return true;
    }


    /*
    This method is used to update client data
    we are passing an array of cliend data , clientname and image
     */
    public boolean updateClientData(String clientname,FileInputStream clientImg, String[] clientData, String clientID){
        String updateQuery = switch (clientname) {
            case "admin" -> "UPDATE `admin` SET `email`=?,`password`=?," +
                    "`mobile_no`=?,`admin_firstname`=?,`admin_middlename`=?," +
                    "`admin_lastname`=?,`admin_home_address`=?,`admin_city`=?," +
                    "`admin_district`=?,`admin_taluka`=?,`country`=?," +
                    "`admin_qualification`=?,`admin_state`=?,`admin_pincode`=?, `admin_category`=?," +
                    "`admin_cast`=?, `admin_img`=? WHERE `admin_id`='" + clientID + "'";

            case "student" -> "UPDATE `student` SET `firstname`=?, `middlename`=?, `lastname`=?, `email`=?, " +
                    "`phoneno`=?, `homeaddress`=?, " +
                    "`city`=?, `district`=?, `country`=?, `taluka`=?, `pincode`=?, `ssc_marks`=?, `hsc_marks`=?, " +
                    "`category`=?, `cast`=?, `fee`=?, `state`=?, `mht_cet_percentile`=?, `branch`=?, " +
                    "`education_year`=?, `semester`=?, `student_img`=? WHERE `student_id`='" + clientID + "'";

            case "employee" -> "UPDATE `employee` SET `firstname`=?, `middlename`=?, `lastname`=?, `email`=?, " +
                    "`phoneno`=?, `homeaddress`=?, " +
                    "`city`=?, `district`=?, `country`=?, `taluka`=?, `qualification`=?, `cast`=?, " +
                    "`experience`=?, " +
                    "`pincode`=?, " +
                    "`salary`=?, `category`=?, `teaching_subject`=?, `state`=?, `employee_img`=? WHERE `employee_id`='" + clientID + "'";
            default -> "";
        };

        try {
            PreparedStatement preparedStatement = conn.prepareStatement(updateQuery);
            //setting values to the prepareStatement
            for (int i = 0; i < clientData.length; i++) {
                preparedStatement.setString(i+1,clientData[i]);
            }

            //setting image to the respected client's query
            switch (clientname) {
                case "admin" -> preparedStatement.setBinaryStream(17, clientImg);
                case "student" -> preparedStatement.setBinaryStream(22, clientImg);
                case "employee" -> preparedStatement.setBinaryStream(19, clientImg);
            }

            int finalResult = preparedStatement.executeUpdate();
            return finalResult > 0;

        }catch (Exception e){
            alert.setContentText("Something Went Wrong\nPlease try again.");
            alert.show();
        }
        return false;
    }

    /*
    This method create a pdf file having some client data.
    We are using iText API to do this stuff.
     */
    public void createClientPDFFile(String clientName, String[] clientData, File saveAs) throws Exception{
        Document document = new Document();
        PdfWriter.getInstance(document, new FileOutputStream(saveAs));
        document.open();

        Font font = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 24, BaseColor.BLACK);
        Chunk collegeName = new Chunk("Sipna College Of Engineering & Technology",font);
        document.add(collegeName);
        document.add(Chunk.NEWLINE);
        document.add(new Paragraph(clientName+" details"));
        document.add(Chunk.NEWLINE);
        document.add(Chunk.NEWLINE);

        PdfPTable table = new PdfPTable(2);
        addTableHeader(table);
        addRows(table,clientData,clientName);

        document.add(table);
        document.close();
    }

    private static void addRows(PdfPTable table, String[] clientData, String clientName) {
        AllConstants allConstants = new AllConstants();

        //if client name is admin the run this loop
        switch (clientName){
            case "Admin":
                for (int i = 0; i < clientData.length; i++) {
                    table.addCell(allConstants.ADMIN_DATA_FIELDS[i]);
                    table.addCell(clientData[i]);
                }
                break;

            case "Student":
                for (int i = 0; i < clientData.length; i++) {
                    table.addCell(allConstants.STUDENT_DATA_FIELDS[i]);
                    table.addCell(clientData[i]);
                }
                break;

            case "Employee":
                for (int i = 0; i < clientData.length; i++) {
                    table.addCell(allConstants.EMPLOYEE_DATA_FIELDS[i]);
                    table.addCell(clientData[i]);
                }
                break;
        }
    }

    private static void addTableHeader(PdfPTable table) {
        Stream.of("Name", "Details")
                .forEach(columnTitle -> {
                    PdfPCell header = new PdfPCell();
                    header.setBackgroundColor(BaseColor.LIGHT_GRAY);
                    header.setBorderWidth(2);
                    header.setPhrase(new Phrase(columnTitle));
                    table.addCell(header);
                });
    }
}
