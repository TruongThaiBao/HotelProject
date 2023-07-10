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
    private ComboBox cbbbookingID;
    @FXML
    private RadioButton fKhachVangLai;
    @FXML
    private String userData;
    @FXML
    private TextField fcustomerName, fnumberID, fphoneNumber, fnop, fbookingID;
    private StackPane rightPane;

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


        if (!fKhachVangLai.isSelected()) {
            int bookingID = Integer.parseInt(fbookingID.getText());
            Customer customer = new Customer(fullName, idNumber, phoneNumber);
            RoomCheckIn roomCheckIn = new RoomCheckIn(bookingID, nop);
            try {
                Room_DAO.createCustomer_CheckIn(customer, roomCheckIn);
                // Hiển thị thông báo thành công
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Success");
                alert.setHeaderText(null);
                alert.setContentText("Data saved successfully!");
                alert.showAndWait();
            } catch (SQLException e) {
                // Xử lý lỗi nếu có
                e.printStackTrace();
                // Hiển thị thông báo lỗi
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText(null);
                alert.setContentText("An error occurred while saving data.");
                alert.showAndWait();
            }
        }else {
            Customer customer = new Customer(fullName, idNumber, phoneNumber);
            RoomCheckIn roomCheckIn = new RoomCheckIn(nop);
            try {
                Room_DAO.createCustomer_CheckIn_NotBooking(customer, roomCheckIn);
                // Hiển thị thông báo thành công
//                Alert alert = new Alert(Alert.AlertType.INFORMATION);
//                alert.setTitle("Success");
//                alert.setHeaderText(null);
//                alert.setContentText("Data saved successfully!");
//                alert.showAndWait();
            } catch (SQLException e) {
                // Xử lý lỗi nếu có
                e.printStackTrace();
                // Hiển thị thông báo lỗi
//                Alert alert = new Alert(Alert.AlertType.ERROR);
//                alert.setTitle("Error");
//                alert.setHeaderText(null);
//                alert.setContentText("An error occurred while saving data.");
//                alert.showAndWait();
            }
        }
        roomPrice.getScene().getWindow().hide();
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("RoomDetailView.fxml"));
            Parent roomDetail = loader.load();

            // Cung cấp bất kỳ dữ liệu nào cho RoomDetailController (nếu cần)
//            RoomDetailController roomDetailController = loader.getController();
            // roomDetailController.setData(...);

            rightPane.getChildren().setAll(roomDetail);
        } catch (IOException e) {
            e.printStackTrace();
        }


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
