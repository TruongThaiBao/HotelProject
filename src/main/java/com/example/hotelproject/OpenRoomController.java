package com.example.hotelproject;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.layout.StackPane;

import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DecimalStyle;
import java.util.ResourceBundle;

public class OpenRoomController implements Initializable {
    @FXML
    private Label roomPrice, roomNumber, timeField;
    @FXML
    private RadioButton fKhachVangLai;
    @FXML
    private String userData;
    @FXML
    private TextField fcustomerName, fnumberID, fphoneNumber, fnop, fbookingID;
    private StackPane rightPane;
    private int roomID;

    public void setRightPane(StackPane rightPane) {
        this.rightPane = rightPane;
    }

    public void setUserData(String userData) {
        this.userData = userData;
    }
    public void setDataAndInitialize(String userData) {
        this.userData = userData;
        initialize(null, null);
    }
//    public void setRoomData(String roomNumber) {
//        try {
//            Room room = Room_DAO.getRoomByRoomNumber(roomNumber);
//            if (room != null) {
//                roomNumberLabel.setText(room.getRoomNumber());
//                roomStatusLabel.setText(room.getStatus() == 1 ? "Occupied" : "Vacant");
//            }
//
//            // Lấy thông tin check-in từ cơ sở dữ liệu dựa trên ID phòng
//            RoomCheckIn roomCheckIn = Room_DAO.getRoomCheckInByRoomID(room.getRoomID());
//            if (roomCheckIn != null) {
//                checkInDateLabel.setText(roomCheckIn.getCheckInDate().toString());
//                checkOutDateLabel.setText(roomCheckIn.getCheckOutDate().toString());
//
//                // Lấy thông tin khách hàng từ cơ sở dữ liệu dựa trên ID khách hàng
//                Customer customer = Room_DAO.getCustomerByCustomerID(roomCheckIn.getCustomerID());
//                if (customer != null) {
//                    customerNameLabel.setText(customer.getFullName());
//                    customerIdLabel.setText(customer.getIdNumber());
//                    customerPhoneLabel.setText(customer.getPhoneNumber());
//                }
//            }
//        } catch (SQLException e) {
//            e.printStackTrace();
//            // Xử lý lỗi nếu cần thiết
//        }
//    }


    @FXML
    public void handleChooseRadioButton(){
        if (fKhachVangLai.isSelected() == true){
            fbookingID.setDisable(true);
        }else {
            fbookingID.setDisable(false);
        }
    }
    @FXML
    private void onCancelButtonClick() {
        roomPrice.getScene().getWindow().hide();
    }
    @FXML
    private void handleOKButtonAction(ActionEvent event) {
        // Lấy thông tin từ FXML
        String fullName = fcustomerName.getText();
        String idNumber = fnumberID.getText();
        String phoneNumber = fphoneNumber.getText();
        int nop = Integer.parseInt(fnop.getText());

        Customer customer = new Customer(fullName, idNumber, phoneNumber);
        roomID = Integer.parseInt(userData);

        try {
            if (!fKhachVangLai.isSelected()) {
                int bookingID = Integer.parseInt(fbookingID.getText());
                RoomCheckIn roomCheckIn = new RoomCheckIn(roomID, bookingID, nop);
                Room_DAO.createCustomer_CheckIn(customer, roomCheckIn);
            } else {
                RoomCheckIn roomCheckIn = new RoomCheckIn(roomID, nop);
                Room_DAO.createCustomer_CheckIn_NotBooking(customer, roomCheckIn);
            }

//            Alert alert = new Alert(Alert.AlertType.INFORMATION);
//            alert.setTitle("Success");
//            alert.setHeaderText(null);
//            alert.setContentText("Data saved successfully!");
//            alert.showAndWait();

            roomPrice.getScene().getWindow().hide();
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("RoomDetailView.fxml"));
                Parent roomDetail = loader.load();

                RoomDetailController roomDetailController = loader.getController();
                roomDetailController.setUserData(userData);
                roomDetailController.setDataAndInitialize(userData);

                rightPane.getChildren().setAll(roomDetail);
            } catch (IOException e) {
                e.printStackTrace();
            }
        } catch (SQLException e) {
//            e.printStackTrace();
//            Alert alert = new Alert(Alert.AlertType.ERROR);
//            alert.setTitle("Error");
//            alert.setHeaderText(null);
//            alert.setContentText("An error occurred while saving data.");
//            alert.showAndWait();
        }

        //
//        try {
//            Room room2 = Room_DAO.getRoomByRoomNumber(roomNumber.getText());
//            RoomCheckIn roomCheckIn2 = Room_DAO.getRoomCheckInByRoomID(room2.getRoomID());
//            Customer customer2 = Room_DAO.getCustomerByCustomerID(roomCheckIn2.getCustomerID());
//
//            FXMLLoader loader = new FXMLLoader(getClass().getResource("RoomDetailView.fxml"));
//            Parent roomDetail = loader.load();
//
//            RoomDetailController roomDetailController = loader.getController();
//            roomDetailController.setRoomData(room2);
//            roomDetailController.setCustomerData(customer2);
//            roomDetailController.setRoomCheckInData(roomCheckIn2);
//
//            rightPane.getChildren().setAll(roomDetail);
//        } catch (IOException | SQLException e) {
//            e.printStackTrace();
//            Alert alert = new Alert(Alert.AlertType.ERROR);
//            alert.setTitle("Error");
//            alert.setHeaderText(null);
//            alert.setContentText("An error occurred while fetching room data.");
//            alert.showAndWait();
//        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            ResultSet resultSet = Room_DAO.showRoomInformationWithRoomID(userData);
            while (resultSet.next()) {
                String giaPhong = resultSet.getString("BasePrice");
                String soPhong = resultSet.getString("RoomNumber");
                roomPrice.setText(giaPhong);
                roomNumber.setText(soPhong);
            }
        } catch (SQLException e) {
            System.out.println("Failed to fetch data from the database.");
            e.printStackTrace();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        //Ngày giờ hiện tại
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime now = LocalDateTime.now();
        String formattedDateTime = now.format(formatter);
        timeField.setText(formattedDateTime);

        //Truyền BookingID vào ComboBox
//        ObservableList<String> bookingIDList = FXCollections.observableArrayList();
//        bookingIDList.add("");
//        cbbbookingID.setItems(bookingIDList);
//        try {
//            ResultSet resultSet = Room_DAO.showBookingID();
//            while (resultSet.next()) {
//                String maBooking = resultSet.getString("BookingID");
//                bookingIDList.add(maBooking);
//                cbbbookingID.setValue(bookingIDList.get(0));
//            }
//        } catch (SQLException e) {
//            System.out.println("Failed to fetch data from the database.");
//            e.printStackTrace();
//        } catch (Exception e) {
//            throw new RuntimeException(e);
//        }
//        cbbbookingID.getSelectionModel().select(0);
    }
}
