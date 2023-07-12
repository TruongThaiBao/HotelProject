package com.example.hotelproject;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;
import java.util.ResourceBundle;

public class RoomsController implements Initializable {
    @FXML
    private TableView<Room> tableView;
    @FXML
    private TableColumn<Room, Integer> roomIDColumn;
    @FXML
    private TableColumn<Room, String> roomNumColumn;
    @FXML
    private TableColumn<Room, Boolean> statusColumn;

    @FXML
    private TableColumn<Room, String> roomTypeNameColumn;
    @FXML
    private TableColumn<Room, String> roomPriceColumn;
    @FXML
    private Label CountRoom;
    @FXML
    private TextField t_idroom, t_status, t_numRoom, t_loai;

    private void handleRowClick() {
        Room selectedRoom = tableView.getSelectionModel().getSelectedItem();
        if (selectedRoom != null) {
            t_idroom.setText(String.valueOf(selectedRoom.getRoomID()));
            t_numRoom.setText(selectedRoom.getRoomNumber());
            String flag = String.valueOf(selectedRoom.getRoomTypeID());
            t_loai.setText(flag);
        }
    }

    public void  show() {
        ResultSet resultSet = RoomList_DAO.showRooms();
        String count = String.valueOf(RoomList_DAO.calculateTotalRoom());
        CountRoom.setText("Tổng " + count + " Phòng");
        ObservableList<Room> rooms = FXCollections.observableArrayList();
        try {
            while (resultSet.next()) {
                int roomID = resultSet.getInt("RoomID");
                int roomtypeID = resultSet.getInt("RoomTypeID");
                String roomNum = resultSet.getString("RoomNumber");
                String roomTypeName = resultSet.getString("RoomTypeName");
                String roomPrice = resultSet.getString("BasePrice");
                Room room = new Room(roomID, roomNum, roomTypeName, roomPrice, roomtypeID);
                rooms.add(room);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        roomNumColumn.setCellValueFactory(cellData -> cellData.getValue().roomNumberProperty());
        roomIDColumn.setCellValueFactory(cellData -> cellData.getValue().roomIDProperty().asObject());
        roomTypeNameColumn.setCellValueFactory(cellData -> cellData.getValue().roomTypeNameProperty());
        roomPriceColumn.setCellValueFactory(cellData -> cellData.getValue().roomPriceProperty());
        tableView.setItems(rooms);
    }
    public void ifQuerry(){
        String roomIDText = t_idroom.getText();
        String roomNumberText = t_numRoom.getText();

        String roomTypeIDText = t_loai.getText();
        if (roomIDText.isEmpty() || roomNumberText.isEmpty()  || roomTypeIDText.isEmpty()) {
            showErrorMessage("Tất cả dữ liệu phải được nhập đầy đủ !");
            return;
        }
        if (!roomIDText.matches("\\d+") && !roomTypeIDText.matches("\\d+")&& !roomNumberText.matches("\\d+")){
            showErrorMessage("Mã Phòng và Số Phòng và Loại Phòng phải  là số !");
            return;
        }
    }
    @FXML
    protected void insertButton() {
        ifQuerry();
        int roomID = Integer.parseInt(t_idroom.getText());
        String roomNumber = String.valueOf(t_numRoom.getText());
        int loaiRoom = Integer.parseInt(t_loai.getText());
        RoomList_DAO.insertRoom(roomID, roomNumber, loaiRoom);
        show();
        t_idroom.clear();
        t_numRoom.clear();
    }
    @FXML
    protected void updateButton() {
        ifQuerry();
        int roomID = Integer.parseInt(t_idroom.getText());
        String roomNumber = String.valueOf(t_numRoom.getText());
        int loaiRoom = Integer.parseInt(t_loai.getText());
        RoomList_DAO.updateRoom(roomID, roomNumber, loaiRoom);
        show();
        t_idroom.clear();
        t_numRoom.clear();
    }
    @FXML
    protected void deleteButton() {
        ifQuerry();
        Alert confirmationAlert = new Alert(Alert.AlertType.CONFIRMATION);
        confirmationAlert.setTitle("Confirmation");
        confirmationAlert.setHeaderText(null);
        confirmationAlert.setContentText("Bạn có chắc chắn muốn xóa ?");
        Optional<ButtonType> result = confirmationAlert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            int roomID = Integer.parseInt(t_idroom.getText());
            String roomNumber = String.valueOf(t_numRoom.getText());
            int loaiRoom = Integer.parseInt(t_loai.getText());
            RoomList_DAO.deleteRoom(roomID, roomNumber, loaiRoom);
            show();
        }
    }
    @FXML
    protected void clearButton() {
        t_idroom.clear();
        t_numRoom.clear();
        t_status.clear();
        t_loai.clear();
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
        show();
        tableView.setOnMouseClicked(event -> handleRowClick());

    }
}
