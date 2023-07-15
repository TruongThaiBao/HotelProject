package com.example.hotelproject;

import javafx.animation.AnimationTimer;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;

public class MainController implements Initializable {
    @FXML
    private StackPane rightPane;
    @FXML
    private Label fullNameLabel, sysTime;
    private GridPane gridPane = new GridPane();
    private int userId;
    public void setUserId(int userId) {
        this.userId = userId;
    }
    public void setUserIDAndInitialize(int userId) {
        this.userId = userId;
        initialize(null, null);
    }
    public void initializeWithData(int userId) {
        setUserId(userId);
        setUserIDAndInitialize(userId);
    }
    private void updateSysTime() {
        LocalDateTime now = LocalDateTime.now();
        String formattedDateTime = now.format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss"));
        sysTime.setText(formattedDateTime);
    }

    private void openRoom(String userData) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("OpenRoom.fxml"));
            Parent root = loader.load();

            OpenRoomController openRoomController = loader.getController();
            openRoomController.setUserData(userData);
            openRoomController.setDataAndInitialize(userData);
            openRoomController.setRightPane(rightPane);
            openRoomController.setUserId(userId);
            openRoomController.setUserIDAndInitialize(userId);

            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setResizable(false);
            stage.initModality(Modality.APPLICATION_MODAL);

            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private void openRoomDetail(String userData){
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("RoomDetailView.fxml"));
            Parent roomDetail = loader.load();

            RoomDetailController roomDetailController = loader.getController();
            roomDetailController.setUserData(userData);
            roomDetailController.setDataAndInitialize(userData);
            roomDetailController.setUserId(userId);
            roomDetailController.setUserIDAndInitialize(userId);

            rightPane.getChildren().setAll(roomDetail);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void onDatPhongButtonClick() {
        rightPane.getChildren().clear();
        rightPane.getChildren().add(gridPane);
        gridPane.setVgap(50);
        gridPane.setHgap(200);

        try {
            int columnCount = 5;
            int rowCount = 0;
            int columnIndex = 0;
            int rowIndex = 0;

            ResultSet resultSet = Room_DAO.read();
            while (resultSet.next()) {
                String tenPhong = resultSet.getString("RoomNumber");
                String id = resultSet.getString("RoomID");
                int status = resultSet.getInt("Status");
                Button button = new Button(tenPhong);
                button.setOnAction(event -> {
                    System.out.println("Đã nhấn vào phòng: " + tenPhong);
                    System.out.println(id);
                    if (status == 1) {
                        openRoomDetail(id);
                    } else {
                        openRoom(id);
                    }
                });

                gridPane.add(button, columnIndex, rowIndex);

                columnIndex++;
                if (columnIndex == columnCount) {
                    columnIndex = 0;
                    rowIndex++;
                    rowCount++;
                }
            }
            gridPane.setPadding(new Insets(30, 0, 0, 30));
        } catch (SQLException e) {
            System.out.println("Failed to fetch data from the database.");
            e.printStackTrace();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    private void onHangHoaButtonClick() {
        rightPane.getChildren().clear();
        try {
            Parent qlsp = FXMLLoader.load(getClass().getResource("qlsp.fxml"));
            rightPane.getChildren().setAll(qlsp);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void onPhongButtonClick() {
        rightPane.getChildren().clear();
        try {
            Parent qlp = FXMLLoader.load(getClass().getResource("ttp.fxml"));
            rightPane.getChildren().setAll(qlp);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void onNhanVienButtonClick() {
        rightPane.getChildren().clear();
        try {
            Parent qlnv = FXMLLoader.load(getClass().getResource("qlnv.fxml"));
            rightPane.getChildren().setAll(qlnv);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void onThongKeButtonClick() {
        rightPane.getChildren().clear();
        try {
            Parent thongKeView = FXMLLoader.load(getClass().getResource("ThongKeView.fxml"));
            rightPane.getChildren().setAll(thongKeView);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    @FXML
    private void onBookingButtonClick() {
        rightPane.getChildren().clear();
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("BookingView.fxml"));
            Parent roomBooking = loader.load();

            RoomBookingController roomBookingController = loader.getController();
            roomBookingController.setUserId(userId);
            roomBookingController.setUserIDAndInitialize(userId);

            rightPane.getChildren().setAll(roomBooking);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    @FXML
    private void onThoatButtonClick() {
        Alert confirmation = new Alert(Alert.AlertType.CONFIRMATION);
        confirmation.setTitle("Xác nhận");
        confirmation.setHeaderText("Bạn có chắc chắn muốn đóng ứng dụng không?");

        if (confirmation.showAndWait().orElse(null) == ButtonType.OK) {
            Stage stage = (Stage) rightPane.getScene().getWindow();
            stage.close();
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        onDatPhongButtonClick();

        try {
            ResultSet resultSet = User_DAO.getNameByUserID(userId);
            while (resultSet.next()) {
                String fullName = resultSet.getString("FullName");
                fullNameLabel.setText(fullName);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        updateSysTime();
        AnimationTimer timer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                updateSysTime();
            }
        };
        timer.start();
    }
}
