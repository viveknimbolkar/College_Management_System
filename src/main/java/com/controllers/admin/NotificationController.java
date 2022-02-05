package com.controllers.admin;

import com.beans.Notification;
import com.beans.StudentFee;
import com.database.DBConnection;
import com.util.CustomAlerts;
import javafx.collections.FXCollections;
import javafx.collections.ObservableArray;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class NotificationController implements Initializable {

    @FXML
    private ChoiceBox<String> sendTo, notificationID;
    @FXML
    private TextArea notification;
    @FXML
    private TableView<Notification> notificationTable;
    @FXML
    private TableColumn<String, Notification> id, department, notificationColumn, date;

    private Connection connection = DBConnection.getDBConnection();
    CustomAlerts alerts = new CustomAlerts();
    List<Notification> notificationList = new ArrayList<>();
    ArrayList<String> allId = new ArrayList<>();
    ObservableList<String> notificationObservableID = FXCollections.observableArrayList(allId);

    public void sendNotification(){
        try {
            LocalDate date = LocalDate.now();
            if (notification.getText().isEmpty()) {
                alerts.warningAlert("Notification should not be empty!");
                return;
            }else if (sendTo.getItems().isEmpty()) {
                alerts.warningAlert("Choose the department to send!");
                return;
            }
            String queryNotification = "INSERT INTO `notification` (`department`, `notification`, `date`) VALUES (?,?,?)";
            PreparedStatement prepstmt = connection.prepareStatement(queryNotification);
            prepstmt.setString(1,sendTo.getValue());
            prepstmt.setString(2,notification.getText());
            prepstmt.setString(3,date.toString());
            int result = prepstmt.executeUpdate();
            if (result == 1)
                alerts.infoAlert("Notification Send Successfully!");
            else
                alerts.errorAlert("Failed to send notification.\nPlease try again!");
            connection.close();
        }catch (Exception e){
            e.printStackTrace();
            alerts.errorAlert("Something went wrong.\nPlease try again!");
        }
    }

    public void resetNotificationField(){
        notification.clear();
    }

    public void updateDeleteNotificationSection(){
        try {
            notificationList = new ArrayList<>();
            String queryNotifications = "SELECT * FROM `notification`";
            PreparedStatement preparedStatement = connection.prepareStatement(queryNotifications);
            ResultSet result = preparedStatement.executeQuery();
            while (result.next()){
                notificationList.add(new Notification(
                        result.getString(1),
                        result.getString(2),
                        result.getString(3),
                        result.getString(4)
                ));
                notificationObservableID.add(result.getString(1));
            }
            ObservableList<Notification> observableListOfNotification = FXCollections.observableArrayList(notificationList);
            id.setCellValueFactory(new PropertyValueFactory<>("id"));
            department.setCellValueFactory(new PropertyValueFactory<>("department"));
            notificationColumn.setCellValueFactory(new PropertyValueFactory<>("notification"));
            date.setCellValueFactory(new PropertyValueFactory<>("date"));
            notificationTable.setItems(observableListOfNotification);
        }catch (Exception ex){
            ex.printStackTrace();
            alerts.errorAlert("Something went wrong.\nPlease try again!");
        }
    }

    public void deleteNotification(){
        try {
            String notifiyid = notificationID.getValue();
            String queryDeleteNotificataion = "DELETE FROM `notification` WHERE id="+notifiyid;
            System.out.println(queryDeleteNotificataion);
            Statement statement = connection.createStatement();
            int result = statement.executeUpdate(queryDeleteNotificataion);

            if (result > 0){
                alerts.infoAlert("Notifications deleted successfully!");
                allId.remove(notifiyid);
            }else {
                alerts.warningAlert("Notification is not deleted!\nPlease try again!");
            }
        }catch (Exception e){
            e.printStackTrace();
            alerts.errorAlert("Something went wrong.\nPlease try again!");
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ObservableList<String> sendToList = FXCollections.observableArrayList(
                "All Department",
                "Computer Science & Engineering Department",
                "Information Technology Department",
                "Electronics & Telecommunication Department",
                "Civil Department",
                "Mechanical Department",
                "Architecture Department"
        );
        sendTo.setItems(sendToList);
        sendTo.show();

        notificationID.setItems(notificationObservableID);
        notificationID.show();
    }
}
