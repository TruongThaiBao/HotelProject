package com.example.hotelproject;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ResourceBundle;

public class BookingInsertController implements Initializable {
    @FXML
    private   ComboBox  b_roomNumber ;
    @FXML
    private TextField   i_fullname , i_number , i_phone;
    @FXML
    private DatePicker  b_checkin, b_checkout ,i_booktime;
    @FXML
    private Label userIDLabel;
    private int userId;
    private int roomID = 0;

    public void setUserId(int userId) {
        this.userId = userId;
    }
    public void setUserIDAndInitialize(int userId) {
        this.userId = userId;
        initialize(null, null);
    }

    @FXML
    protected void InsertBookingBtn(){
        LocalDate BookingTime = i_booktime.getValue();
        LocalDate CheckInDate = b_checkin.getValue();
        LocalDate CheckOutDay = b_checkout.getValue();
        String FullName = i_fullname.getText();
        String PhoneNum = i_phone.getText();
        String IDNum = i_number.getText();

        //Lấy roomID bằng roomNumber
        try {
            ResultSet resultSet = Room_DAO.getRoomIDByRoomNumber(b_roomNumber.getValue().toString());
            while (resultSet.next()) {
                int maPhong = resultSet.getInt("RoomID");
                roomID = maPhong;
            }
        }
        catch (SQLException e){
            throw  new RuntimeException(e);
        }

        //Validation input
        if (BookingTime == null || CheckInDate == null || CheckOutDay == null || FullName.isEmpty() || PhoneNum.isEmpty() || IDNum.isEmpty() || roomID == 0) {
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
        RoomBooking_DAO.insertBooking(BookingTime, CheckInDate, CheckOutDay,roomID,userId,FullName,PhoneNum,IDNum);
        RoomBookingController roomBookingController = new RoomBookingController();
        roomBookingController.showinl();

        i_booktime.getScene().getWindow().hide();
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
    private void showData(){
        ObservableList flag1 = FXCollections.observableArrayList();
        ObservableList<Integer> flag2 = FXCollections.observableArrayList();
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
            ResultSet resultSet = Room_DAO.read();
            while (resultSet.next()) {
                String soPhong = resultSet.getString("RoomNumber");
                flag1.add(soPhong);
                b_roomNumber.setItems(flag1);
            }

        }
        catch (SQLException e){
            throw  new RuntimeException(e);
        }

        //Gọi userID sau khi truyền
        try {
            ResultSet resultSet = User_DAO.getNameByUserID(userId);
            while (resultSet.next()) {
                String fullName = resultSet.getString("FullName");
                userIDLabel.setText(fullName);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
    showData();
    }
}
