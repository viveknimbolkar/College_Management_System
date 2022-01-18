package com.college_management_system.backend;

import javafx.beans.property.SimpleStringProperty;

public class StudentFeeDetails {
    SimpleStringProperty studentId;
    SimpleStringProperty fullname;
    SimpleStringProperty branch;
    SimpleStringProperty semester;
    SimpleStringProperty educationYear;
    SimpleStringProperty fees;
    SimpleStringProperty date;

    public StudentFeeDetails(String studentId, String fullname, String branch, String semester, String educationYear,
                             String fees, String date){
        this.studentId = new SimpleStringProperty(studentId);
        this.fullname = new SimpleStringProperty(fullname);
        this.branch = new SimpleStringProperty(branch);
        this.semester = new SimpleStringProperty(semester);
        this.educationYear = new SimpleStringProperty(educationYear);
        this.fees = new SimpleStringProperty(fees);
        this.date = new SimpleStringProperty(date);
    }

    public String getStudentId() {
        return studentId.get();
    }

    public SimpleStringProperty studentIdProperty() {
        return studentId;
    }

    public void setStudentId(String studentId) {
        this.studentId.set(studentId);
    }

    public String getFullname() {
        return fullname.get();
    }

    public SimpleStringProperty fullnameProperty() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname.set(fullname);
    }

    public String getBranch() {
        return branch.get();
    }

    public SimpleStringProperty branchProperty() {
        return branch;
    }

    public void setBranch(String branch) {
        this.branch.set(branch);
    }

    public String getSemester() {
        return semester.get();
    }

    public SimpleStringProperty semesterProperty() {
        return semester;
    }

    public void setSemester(String semester) {
        this.semester.set(semester);
    }

    public String getEducationYear() {
        return educationYear.get();
    }

    public SimpleStringProperty educationYearProperty() {
        return educationYear;
    }

    public void setEducationYear(String educationYear) {
        this.educationYear.set(educationYear);
    }

    public String getFees() {
        return fees.get();
    }

    public SimpleStringProperty feesProperty() {
        return fees;
    }

    public void setFees(String fees) {
        this.fees.set(fees);
    }

    public String getDate() {
        return date.get();
    }

    public SimpleStringProperty dateProperty() {
        return date;
    }

    public void setDate(String date) {
        this.date.set(date);
    }
}
