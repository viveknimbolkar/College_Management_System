package com.database;
/*
This class use to get the connection for mysql server db
just call getDBConnection method and you will get connection
JDBC DRIVER CONNECTION
 */
import java.sql.Connection;
import java.sql.DriverManager;

public class DBConnection {

    //connection object for jdbc driver connection
    static Connection conn;

    //get the connection object
    public static Connection getDBConnection(){
        //server credentials
        String url = "jdbc:mysql://localhost:3306/college_management_system";
        String username = "root";
        String password = "";

        try {
            Class.forName("com.mysql.jdbc.Driver");
            conn = DriverManager.getConnection(url,username,password);
        }catch (Exception e){
            //e.printStackTrace();
        }
        return conn; //return jdbc connection to mysql
    }
}
