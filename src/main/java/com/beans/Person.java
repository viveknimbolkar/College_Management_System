package com.beans;
/*
This class use to represent admin
First we create data members according to the main database, then we create getters and for setters we are using
constructor. In constructor, we are passing array of data which is received from main database into the constructor.
 */

import javafx.beans.property.SimpleStringProperty;

public class Person {

    private final SimpleStringProperty property;
    private final SimpleStringProperty details;

    public Person(String property, String details){
        this.property = new SimpleStringProperty(property);
        this.details = new SimpleStringProperty(details);
    }

    public String getDetails() {
        return details.get();
    }

    public String getProperty() {
        return property.get();
    }
}
