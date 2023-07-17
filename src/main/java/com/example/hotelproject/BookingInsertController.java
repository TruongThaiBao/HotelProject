package com.example.hotelproject;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
public class BookingInsertController {
    private RoomBookingController customerController;

    public void setCustomerController(RoomBookingController customerController) {
        this.customerController = customerController;
    }
    @FXML
    private   ComboBox<Integer>  b_roomid , b_userid;
    @FXML
    private TextField   i_fullname , i_number , i_phone
            ;
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
            b_roomid.setItems(flag1);
            b_userid.setItems(flag3);
        }
        catch (SQLException e){
            throw  new RuntimeException(e);
        }
    }
    @FXML
    protected void InsertBookingBtn(){
        LocalDate BookingTime = i_booktime.getValue();
        LocalDate CheckInDate = b_checkin.getValue();
        LocalDate CheckOutDay = b_checkout.getValue();
        Integer roomIDValue = b_roomid.getValue();
        int RoomID = roomIDValue != null ? roomIDValue.intValue() : 0;
        Integer userIDValue = b_userid.getValue();
        int UserID = userIDValue != null ? userIDValue.intValue() : 0;
        String FullName = i_fullname.getText();
        String PhoneNum = i_phone.getText();
        String IDNum = i_number.getText();
        if (BookingTime == null || CheckInDate == null || CheckOutDay == null || FullName.isEmpty() || PhoneNum.isEmpty() || IDNum.isEmpty() || RoomID == 0  || UserID == 0) {
            showErrorMessage("Vui lòng nhập đầy đủ thông tin!");
            return;
        }
        if (!validatePhoneNumber(PhoneNum)) {
            showErrorMessage("Số điện thoại không hợp lệ!");
            return;
        }

        if (!validateIDNumber(IDNum)) {
            showErrorMessage("Số CMND không hợp lệ!");
            return;
        }
        if (!validateDate(BookingTime)){
            showErrorMessage("Thời Gian Booking Không Hợp Lệ!");
            return;
        }
        if (!validateCheckin(CheckInDate)){
            showErrorMessage("Thời Gian CheckIn Không Hợp Lệ!");
            return;
        }
        if (!validateCheckout(CheckOutDay,CheckInDate)){
            showErrorMessage("Thời Gian CheckOut Không Hợp Lệ!");
            return;
        }
            showSuccessMessage("Insert Thành Công");
            RoomBooking_DAO.insertBooking(BookingTime, CheckInDate, CheckOutDay,RoomID,UserID,FullName,PhoneNum,IDNum);
        Stage currentStage = (Stage) i_fullname.getScene().getWindow();
        customerController.updateTableView();
        currentStage.close();
    }
    private boolean validatePhoneNumber(String phoneNumber) {
        if (phoneNumber.length() != 10) {
            return false;
        }
        if (!phoneNumber.startsWith("0")) {
            return false;
        }
        for (char c : phoneNumber.toCharArray()) {
            if (!Character.isDigit(c)) {
                return false;
            }
        }
        return true;
    }
    private boolean validateIDNumber(String idNumber) {
        int length = idNumber.length();
        if (length != 9 && length != 12) {
            return false;
        }
        for (char c : idNumber.toCharArray()) {
            if (!Character.isDigit(c)) {
                return false;
            }
        }
        return true;
    }
    private boolean validateDate(LocalDate date) {
        LocalDate currentDate = LocalDate.now();
        if (date.isBefore(currentDate)) {
            return false;
        }
        return true;
    }
    private boolean validateCheckin(LocalDate date) {
        LocalDate currentDate = LocalDate.now();
        if (date.isBefore(currentDate)) {
            return false;
        }
        return true;
    }
    private boolean validateCheckout(LocalDate date, LocalDate CHECKIN) {
        LocalDate currentDate = LocalDate.now();
        if (date.isBefore(CHECKIN)) {
            return false;
        }
        else {
            return true;
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
