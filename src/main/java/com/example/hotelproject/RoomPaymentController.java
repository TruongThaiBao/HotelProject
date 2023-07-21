package com.example.hotelproject;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class RoomPaymentController implements Initializable {
    private String userData;
    private int userId;
    private int roomID;
    private int serviceID;
    List<RoomDetailController.Item> newItems = new ArrayList<>();
    @FXML
    private Button returnMain;
    @FXML
    private TableView paymentTable;
    @FXML
    private TableColumn<RoomDetailController.Item, String> colTenMatHang;
    @FXML
    private TableColumn<RoomDetailController.Item, Integer> colSoLuong;
    @FXML
    private TableColumn<RoomDetailController.Item, Double> colDonGia;
    @FXML
    private TableColumn<RoomDetailController.Item, Double> colThanhTien;
    @FXML
    private Label txtThanhTien, txtPhuThu,txtGiamGia, txtThanhToan;


    public void setUserData(String userData) {
        this.userData = userData;
    }

    public void setDataAndInitialize(String userData) {
        this.userData = userData;
        initialize(null, null);
    }
    public void setUserId(int userId) {
        this.userId = userId;
    }
    public void setUserIDAndInitialize(int userId) {
        this.userId = userId;
        initialize(null, null);
    }
    public void setRoomData(ObservableList<RoomDetailController.Item> itemList, String totalAmount) {
        // Sử dụng itemList và totalAmount để hiển thị dữ liệu trong RoomPaymentView

        // Thiết lập cột và dữ liệu cho bảng TableView
        paymentTable.setItems(itemList);
        TableColumn<RoomDetailController.Item, Integer> colSTT = new TableColumn<>("STT");
        colSTT.setCellValueFactory(new PropertyValueFactory<>("serialNumber"));

        TableColumn<RoomDetailController.Item, String> colTenMatHang = new TableColumn<>("Tên mặt hàng");
        colTenMatHang.setCellValueFactory(new PropertyValueFactory<>("itemName"));

        TableColumn<RoomDetailController.Item, Integer> colSoLuong = new TableColumn<>("Số lượng");
        colSoLuong.setCellValueFactory(new PropertyValueFactory<>("quantity"));

        TableColumn<RoomDetailController.Item, Double> colThanhTien = new TableColumn<>("Thành tiền");
        colThanhTien.setCellValueFactory(new PropertyValueFactory<>("totalAmount"));

        paymentTable.getColumns().clear();
        paymentTable.getColumns().addAll(colSTT, colTenMatHang, colSoLuong, colThanhTien);

        // Hiển thị thông tin về tổng tiền và các phần khác
        txtThanhTien.setText(totalAmount);
        txtPhuThu.setText("0"); // Thiết lập dữ liệu phụ thu tương ứng
        txtGiamGia.setText("0"); // Thiết lập dữ liệu giảm giá tương ứng
        txtThanhToan.setText(totalAmount); // Thiết lập dữ liệu thanh toán tương ứng
    }
    private void switchToMainScreen() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("Main.fxml"));
            Parent root = loader.load();

            MainController mainController = loader.getController();
            mainController.initializeWithData(userId);

            Scene scene = new Scene(root);
            Stage stage = (Stage) returnMain.getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }



    @FXML
    private void onTroVeTruocButtonClick() {
        Stage roomPaymentStage = (Stage) returnMain.getScene().getWindow();
        roomPaymentStage.close();
    }
    @FXML
    private void onTroVeSoDoPhongButtonClick(){
        switchToMainScreen();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
}
