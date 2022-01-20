package com.college_management_system.backend;

public class StudentFee {
    String studentId;
    String studentName;
    String feePaid;
    String branch;
    String semester;
    String educationYear;
    String date;

    public StudentFee(String studentId, String studentName, String feePaid, String branch, String semester, String educationYear, String date) {
        this.studentId = studentId;
        this.studentName = studentName;
        this.feePaid = feePaid;
        this.branch = branch;
        this.semester = semester;
        this.educationYear = educationYear;
        this.date = date;
    }

    public String getStudentId() {
        return studentId;
    }

    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }

    public String getStudentName() {
        return studentName;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    public String getFeePaid() {
        return feePaid;
    }

    public void setFeePaid(String feePaid) {
        this.feePaid = feePaid;
    }

    public String getBranch() {
        return branch;
    }

    public void setBranch(String branch) {
        this.branch = branch;
    }

    public String getSemester() {
        return semester;
    }

    public void setSemester(String semester) {
        this.semester = semester;
    }

    public String getEducationYear() {
        return educationYear;
    }

    public void setEducationYear(String educationYear) {
        this.educationYear = educationYear;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
