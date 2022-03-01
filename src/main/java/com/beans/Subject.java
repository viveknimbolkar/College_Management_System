package com.beans;

public class Subject {
    String subjectCode;
    String subjectName;
    String courseType;
    String branch;
    String semester;
    String theoryMarks;
    String practicalMarks;

    public Subject(
            String subjectCode,
            String subjectName,
            String courseType,
            String branch,
            String semester,
            String theoryMarks,
            String practicalMarks
    ) {
        this.subjectCode = subjectCode;
        this.subjectName = subjectName;
        this.courseType = courseType;
        this.branch = branch;
        this.semester = semester;
        this.theoryMarks = theoryMarks;
        this.practicalMarks = practicalMarks;
    }

    public String getSubjectCode() {
        return subjectCode;
    }

    public void setSubjectCode(String subjectCode) {
        this.subjectCode = subjectCode;
    }
    public String getSubjectName() {
        return subjectName;
    }

    public void setSubjectName(String subjectName) {
        this.subjectName = subjectName;
    }
    public String getCourseType() {
        return courseType;
    }

    public void setCourseType(String courseType) {
        this.courseType = courseType;
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

    public String getTheoryMarks() {
        return theoryMarks;
    }

    public void setTheoryMarks(String theoryMarks) {
        this.theoryMarks = theoryMarks;
    }

    public String getPracticalMarks() {
        return practicalMarks;
    }

    public void setPracticalMarks(String practicalMarks) {
        this.practicalMarks = practicalMarks;
    }
}
