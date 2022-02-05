package com.beans;

public class Notification {
    String id;
    String department;
    String notification;
    String date;

    public Notification(String id, String department, String notification, String date) {
        this.id = id;
        this.department = department;
        this.notification = notification;
        this.date = date;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getNotification() {
        return notification;
    }

    public void setNotification(String notification) {
        this.notification = notification;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
