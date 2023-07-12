package com.example.hotelproject;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Optional;
import java.util.ResourceBundle;

public class BookingFixController implements Initializable {
    @FXML
    private TextField i_bookingid1 , i_customer , i_roomid , i_userid;
    @FXML
    private DatePicker b_checkin1, b_checkout1 ,i_booktime1;
    @FXML
    private TableView<RoomBooking> tableView, tableView1;
    @FXML
    private TableColumn<RoomBooking, Integer> IDc, IDc1;
    @FXML
    private TableColumn<RoomBooking, Integer> bookIDColumn, bookIDColumn1;
    @FXML
    private TableColumn<RoomBooking, Integer> roomIDColumn, roomIDColumn1;
    @FXML
    private TableColumn<RoomBooking, String> nameCustomer, nameCustomer1;
    @FXML
    private TableColumn<RoomBooking, String> BookingTimeColumn, BookingTimeColumn1;
    @FXML
    private TableColumn<RoomBooking, String> CustomerIDColumn, CustomerIDColumn1;
    @FXML
    private TableColumn<RoomBooking, String> PhoneCustomerColumn, PhoneCustomerColumn1;
    @FXML
    private TableColumn<RoomBooking, String> CheckinColumn, CheckinColumn1;
    @FXML
    private TableColumn<RoomBooking, String> CheckOutColumn, CheckOutColumn1;
    @FXML
    private TableColumn<RoomBooking, String> UserID, UserID1;
    public void ifQuerry(){
        int bookid = Integer.parseInt(i_bookingid1.getText());
        int customerid = Integer.parseInt(i_customer.getText());
        int userid = Integer.parseInt(i_userid.getText());
        LocalDate bookingtime = i_booktime1.getValue();
        LocalDate checkin = b_checkin1.getValue();
        LocalDate checkout = b_checkout1.getValue();
        String BookIDtext = i_bookingid1.getText();
        String CustomerIDText = i_customer.getText();
        String UserIDtext = i_userid.getText();
        int roomid = Integer.parseInt(i_roomid.getText());
        if (bookid != 0 || customerid != 0 || userid != 0 || roomid != 0) {
            showErrorMessage("Customer ID và User ID và RoomID không được để trống");
            return;
        }
        if (!BookIDtext.matches("\\d+") && !CustomerIDText.matches("\\d+")&& !UserIDtext.matches("\\d+")){
            showErrorMessage("Customer ID và User ID Phải là 1 số nguyên ");
            return;
        }
        if (!BookIDtext.matches("\\d+") && !CustomerIDText.matches("\\d+")&& !UserIDtext.matches("\\d+")){
            showErrorMessage("Customer ID và User ID Phải là 1 số nguyên ");
            return;
        }

    }
    @FXML
    protected void FixBookingBtn(){
        Alert confirmationAlert = new Alert(Alert.AlertType.CONFIRMATION);
        confirmationAlert.setTitle("Confirmation");
        confirmationAlert.setHeaderText(null);
        confirmationAlert.setContentText("Bạn có chắc chắn muốn xóa ?");
        Optional<ButtonType> result = confirmationAlert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            int bookid = Integer.parseInt(i_bookingid1.getText());
            int customerid = Integer.parseInt(i_customer.getText());
            int userid = Integer.parseInt(i_userid.getText());
            LocalDate bookingtime = i_booktime1.getValue();
            LocalDate checkin = b_checkin1.getValue();
            LocalDate checkout = b_checkout1.getValue();
            int roomid = Integer.parseInt(i_roomid.getText());
            RoomBooking_DAO.updateBooking(bookid, customerid, roomid,bookingtime,checkin,checkout,userid);
            if (bookid != 0) {
                showSuccessMessage("Cập Nhật Thành Công!");
                show42();
            } else {
                showErrorMessage("Cập Nhật Thất Bại!");
                 show42();
            }
        }
    }
    public void show42() {
        ResultSet resultSet = RoomBooking_DAO.showRooms();
        ObservableList<RoomBooking> roomBookings = FXCollections.observableArrayList();
        try {
            while (resultSet.next()) {
                int bookingID = resultSet.getInt("BookingID");
                int CustomerID = resultSet.getInt("CustomerID");
                String fullName = resultSet.getString("FullName");
                String IDNumber = resultSet.getString("IDNumber");
                String phoneNumber = resultSet.getString("PhoneNumber");
                int roomID = resultSet.getInt("RoomID");
                LocalDate BookingTime = resultSet.getDate("BookingTime").toLocalDate();
                LocalDate checkInDate = resultSet.getDate("CheckInDate").toLocalDate();
                LocalDate checkOutDate = resultSet.getDate("CheckOutDate").toLocalDate();
                int userID = resultSet.getInt("UserID");
                RoomBooking roomBooking = new RoomBooking(bookingID,CustomerID, fullName, IDNumber, phoneNumber, roomID,BookingTime, checkInDate, checkOutDate, userID);
                roomBookings.add(roomBooking);

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        bookIDColumn.setCellValueFactory(new PropertyValueFactory<>("bookingID"));
        IDc.setCellValueFactory(new PropertyValueFactory<>("customerID"));
        nameCustomer.setCellValueFactory(new PropertyValueFactory<>("fullName"));
        CustomerIDColumn.setCellValueFactory(new PropertyValueFactory<>("IDNumber"));
        PhoneCustomerColumn.setCellValueFactory(new PropertyValueFactory<>("phoneNumber"));
        roomIDColumn.setCellValueFactory(new PropertyValueFactory<>("roomID"));
        BookingTimeColumn.setCellValueFactory(new PropertyValueFactory<>("bookingTime"));
        CheckinColumn.setCellValueFactory(new PropertyValueFactory<>("checkInDate"));
        CheckOutColumn.setCellValueFactory(new PropertyValueFactory<>("checkOutDate"));
        UserID.setCellValueFactory(new PropertyValueFactory<>("userID"));
        tableView.setItems(roomBookings);
    }
        public void handleRowClick() {
        RoomBooking selectedRoom = tableView.getSelectionModel().getSelectedItem();
        if (selectedRoom != null) {
            int BookID = selectedRoom.getBookingID();
            int IDCustomer = selectedRoom.getCustomerID();
            int RoomID = selectedRoom.getRoomID();
            LocalDate BookingTime = LocalDate.from(selectedRoom.getBookingTime());
            LocalDate CheckinRoom = selectedRoom.getCheckInDate();
            LocalDate CheckoutRoom = selectedRoom.getCheckOutDate();
            int UserID = selectedRoom.getUserID();
            RoomBooking bx = new RoomBooking(BookID,IDCustomer,RoomID,BookingTime,CheckinRoom,CheckoutRoom,UserID);
            i_bookingid1.setText(String.valueOf(BookID));
            i_customer.setText(String.valueOf(IDCustomer));
            i_roomid.setText(String.valueOf(RoomID));
            i_userid.setText(String.valueOf(UserID));
            LocalDate iIn = LocalDate.from(BookingTime);
            i_booktime1.setValue(iIn);
            b_checkin1.setValue(CheckinRoom);
            b_checkout1.setValue(CheckoutRoom);
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
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        show42();
        tableView.setOnMouseClicked(event -> handleRowClick());
    }
}
