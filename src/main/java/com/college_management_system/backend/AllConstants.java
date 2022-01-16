package com.college_management_system.backend;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class AllConstants {

    //defines the position of stage on screen
    public final int H_POSITION = 100;
    public final int V_POSITION = 0;

    //defines the size of stage i.e. width and height
    public final int WIDTH = 1100;
    public final int HEIGHT = 700;
    String handle = "vivek nimbolkar";

    //size of main switch
    public final int MAIN_SWITCH_WIDTH = 600;
    public final int MAIN_SWITCH_HEIGHT = 400;
    //size of main-switch.fxml i.e. Main section

    //This array use to write field name into pdf file
    public final String[] ADMIN_DATA_FIELDS = {
            "Email Address",
            "Password",
            "Mobile no.",
            "Firstname",
            "Fathername",
            "Surname",
            "Home Address",
            "City",
            "District",
            "Taluka",
            "Country",
            "Qualification",
            "State",
            "Pincode",
            "Gender",
            "Admin ID",
            "Category",
            "Cast",
            "Date of Birth"
    };

    //This array use to write field name into pdf file
    public final String[] EMPLOYEE_DATA_FIELDS = {
            "Firstname",
            "Fathername",
            "Surname",
            "Email Address",
            "Employee ID",
            "Mobile no.",
            "Home Address",
            "City",
            "Taluka",
            "District",
            "Pincode",
            "State",
            "Country",
            "Qualification",
            "Gender",
            "Category",
            "Cast",
            "Date of Birth"
    };

    //This array use to write field name into pdf file
    public final String[] STUDENT_DATA_FIELDS = {
            "Firstname",
            "Fathername",
            "Surname",
            "Email Address",
            "Mobile no.",
            "Student ID",
            "Home Address",
            "City",
            "Taluka",
            "District",
            "State",
            "Pincode",
            "Country",
            "Cast",
            "Gender",
            "Category",
            "Date of Birth",
            "MHT-CET Percentile",
            "Branch",
            "Education Year",
            "Semester",
            "Fee paid",
            "SSC Marks",
            "HSC Marks"
    };

    //list of all categories
    public ObservableList<String> getCategories(){
      return FXCollections.observableArrayList(
              "OBC",
              "OPEN",
              "EWS",
              "SC",
              "ST",
              "OTHER"
      );
    }

    //Years in B.E.
    public ObservableList<String> getEducationalYearList(){
        return FXCollections.observableArrayList(
                "1st Year",
                "2nd Year",
                "3rd Year",
                "4th Year"
        );
    }

    //Semeter in B.E.
    public ObservableList<String> getSemestersList(){
        return FXCollections.observableArrayList(
                "1st Semester",
                "2nd Semester",
                "3rd Semester",
                "4th Semester",
                "5th Semester",
                "6th Semester",
                "7th Semester",
                "8th Semester"
        );
    }

    //Branch of Engineering
    public ObservableList<String> getBranchList(){
        return FXCollections.observableArrayList(
                "Computer Science & Engineering",
                "Information Technology",
                "Electronics & Telecommunication Engineering",
                "Mechanical Engineering",
                "Civil Engineering",
                "Architecture Engineering"
        );
    }

    //List of all states
    public ObservableList<String> getStateList(){
        return FXCollections.observableArrayList(
                "Andhra Pradesh",
                "Arunachal Pradesh",
                "Assam",
                "Bihar",
                "Chhattisgarh",
                "Goa","Gujarat",
                "Haryana",
                "Himachal Pradesh",
                "Jharkhand",
                "Karnataka",
                "Kerala",
                "Madhya Pradesh",
                "Maharashtra",
                "Manipur",
                "Meghalaya",
                "Mizoram",
                "Nagaland",
                "Odisha",
                "Punjab",
                "Rajasthan",
                "Sikkim",
                "Tamil Nadu",
                "Telangana",
                "Tripura",
                "Uttar Pradesh",
                "Uttarakhand",
                "West Bengal"
        );
    }
}
