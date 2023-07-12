package com.example.hotelproject;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
public class BookingInsertController {
    @FXML
    private   ComboBox<Integer> i_cbb ,b_roomid , b_userid;
    @FXML
    private TextField i_cusid , i_fullname , i_number , i_phone
            , i_bookingid;
    @FXML
    private DatePicker  b_checkin, b_checkout ,i_booktime;
    @FXML
    public void initialize() {
        ObservableList<Integer> flag1 = FXCollections.observableArrayList();
        ObservableList<Integer> flag2 = FXCollections.observableArrayList();
        ObservableList<Integer> flag3 = FXCollections.observableArrayList();
        ObservableList<Customer> Cus = FXCollections.observableArrayList();
        ObservableList<Room> roomlist = FXCollections.observableArrayList();
        ResultSet resultSet1 = RoomList_DAO.showRooms();
        try {
            while (resultSet1.next()) {
                int roomID = resultSet1.getInt("RoomID");
                int roomtypeID = resultSet1.getInt("RoomTypeID");
                String roomNum = resultSet1.getString("RoomNumber");
                String roomTypeName = resultSet1.getString("RoomTypeName");
                boolean status = resultSet1.getBoolean("Status");
                String roomPrice = resultSet1.getString("BasePrice");
                Room room = new Room(roomID, roomNum, roomTypeName, roomPrice, roomtypeID);
                roomlist.add(room);
                flag1.add(roomID);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try {
            ResultSet resultSet2 = RoomBooking_DAO.showCus();
            while (resultSet2.next()) {
                int CustomerID = resultSet2.getInt("CustomerID");
                String FullName = resultSet2.getString("FullName");
                String IDNumber = resultSet2.getString("IDNumber");
                String PhoneNumber = resultSet2.getString("PhoneNumber");
                Boolean Deleted = resultSet2.getBoolean("Deleted");
                Customer cus = new Customer(CustomerID,FullName,IDNumber,PhoneNumber,Deleted);
                Cus.add(cus);
                flag2.add(CustomerID);
            }
        }
        catch (SQLException e){
            throw  new RuntimeException(e);
        }

        try {
            ResultSet resultSet = RoomBooking_DAO.showRooms();
            while (resultSet.next()) {
                int cbbuser = resultSet.getInt("UserID");
                flag3.add(cbbuser);
            }
            i_cbb.setItems(flag2);
            b_roomid.setItems(flag1);
            b_userid.setItems(flag3);
        }

        catch (SQLException e){
            throw  new RuntimeException(e);
        }
    }
    @FXML
    protected void InsertCustomerBtn(){
        int cusid = Integer.parseInt(i_cusid.getText());
        String fullName = i_fullname.getText();
        String Number = i_number.getText();
        String Phone = i_phone.getText();
        RoomBooking_DAO.insertCustomer(cusid,fullName,Number,Phone);
        if (!fullName.isEmpty()) {
            showSuccessMessage("Nhập Thành Công!");
            initialize();
        } else {
            showErrorMessage("Nhập thất bại!");
            initialize();
        }
    }
    @FXML
    protected void InsertBookingBtn(){
        int BookingID = Integer.parseInt(i_bookingid.getText());
        LocalDate BookingTime = i_booktime.getValue();
        LocalDate CheckInDate = b_checkin.getValue();
        LocalDate CheckOutDay = b_checkout.getValue();
        int CustomerID =  i_cbb.getValue();
        int RoomID = b_roomid.getValue();
        int UserID = b_userid.getValue();
        RoomBooking_DAO.insertBooking(BookingID, CustomerID, RoomID, BookingTime, CheckInDate, CheckOutDay,UserID);
        if (BookingID != 0) {
            showSuccessMessage("Nhập Thành Công!");
            initialize();
        } else {
            showErrorMessage("Nhập thất bại!");
            initialize();
        }
    }
    private void showSuccessMessage(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Success");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
    private void showErrorMessage(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
