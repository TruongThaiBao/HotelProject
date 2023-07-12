package com.example.hotelproject;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class RoomDetailController implements Initializable {
    @FXML
    private Label roomNumber;
    @FXML
    private ComboBox<String> fTenMatHang;
    @FXML
    private TextField fSoLuong;
    @FXML
    private TableView<Item> tableViewRoomDetail;
    @FXML
    private TableColumn<Item, String> colTenMatHang;
    @FXML
    private TableColumn<Item, Integer> colSoLuong;
    @FXML
    private TableColumn<Item, Double> colDonGia;
    @FXML
    private TableColumn<Item, Double> colThanhTien;
    private ObservableList<Item> itemList;
    private int roomID;
    private int serviceID;
    List<Item> newItems = new ArrayList<>();


    private String userData;

    public static class Item {
        private String tenMatHang;
        private int soLuong;
        private double donGia;
        private double thanhTien;
        private int soLanLuu;

        public Item(String tenMatHang, int soLuong, double donGia, double thanhTien) {
            this.tenMatHang = tenMatHang;
            this.soLuong = soLuong;
            this.donGia = donGia;
            this.thanhTien = thanhTien;
        }
        public Item(String tenMatHang, int soLuong, double donGia) {
            this.tenMatHang = tenMatHang;
            this.soLuong = soLuong;
            this.donGia = donGia;
        }

        public double getDonGia() {
            return donGia;
        }

        public void setDonGia(double donGia) {
            this.donGia = donGia;
        }

        public double getThanhTien() {
            return thanhTien;
        }

        public void setThanhTien(double thanhTien) {
            this.thanhTien = thanhTien;
        }

        public String getTenMatHang() {
            return tenMatHang;
        }

        public void setTenMatHang(String tenMatHang) {
            this.tenMatHang = tenMatHang;
        }

        public int getSoLuong() {
            return soLuong;
        }

        public void setSoLuong(int soLuong) {
            this.soLuong = soLuong;
        }
    }

    public void setUserData(String userData) {
        this.userData = userData;
    }

    public void setDataAndInitialize(String userData) {
        this.userData = userData;
        initialize(null, null);
    }
    private void switchToMainScreen() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("Main.fxml"));
            Parent root = loader.load();

            MainController mainController = loader.getController();

            // Truyền dữ liệu cần thiết nếu có
            // mainController.setData(...);

            Scene scene = new Scene(root);
            Stage stage = (Stage) roomNumber.getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    @FXML
    private void onThemButtonClick() {
        String tenMatHang = fTenMatHang.getValue();
        int soLuong = Integer.parseInt(fSoLuong.getText());

        try {
            double donGia = Service_DAO.getDonGiaByTenMatHang(tenMatHang);
            double thanhTien = soLuong * donGia;

            Item item = new Item(tenMatHang, soLuong, donGia, thanhTien);
            itemList.add(item);

            fTenMatHang.getSelectionModel().clearSelection();
            fSoLuong.clear();
        } catch (SQLException e) {
            System.out.println("Failed to fetch data from the database.");
            e.printStackTrace();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    @FXML
    private void onXoaButtonClick() {
        Item selectedItem = tableViewRoomDetail.getSelectionModel().getSelectedItem();
        if (selectedItem != null) {
            itemList.remove(selectedItem);
        }
    }
    @FXML
    private void onThoatButtonClick(){
        switchToMainScreen();
    }
    @FXML
    private void onLuuButtonClick() throws SQLException {
        // Lưu thông tin từ itemList vào bảng RoomServices

        for (Item item : itemList) {
            String tenMatHang = item.getTenMatHang();
            serviceID = (int) Service_DAO.getIDByTenMatHang(item.getTenMatHang());
            int soLuong = item.getSoLuong();
            double donGia = item.getDonGia();

            if (!RoomService_DAO.isRoomServiceExist(roomID, serviceID, tenMatHang)) {
                newItems.add(new Item(tenMatHang, soLuong, donGia));
            }
//            // Tạo đối tượng RoomService và lưu thông tin vào cơ sở dữ liệu
//            RoomService roomService = new RoomService(roomID, serviceID, soLuong, donGia);
//            RoomService_DAO.saveRoomService(roomService);
        }

        for (Item newItem : newItems) {
            String tenMatHang = newItem.getTenMatHang();
            serviceID = (int) Service_DAO.getIDByTenMatHang(newItem.getTenMatHang());
            int soLuong = newItem.getSoLuong();
            double donGia = newItem.getDonGia();

            // Tạo đối tượng RoomService và lưu thông tin vào cơ sở dữ liệu
            RoomService roomService = new RoomService(roomID, serviceID, soLuong, donGia);
            RoomService_DAO.saveRoomService(roomService);
        }
        Room_DAO.updateRoomStatus(roomID, 1);
        switchToMainScreen();
    }



    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // Truyền số phòng
        try {
            ResultSet resultSet = Room_DAO.showRoomInformationWithRoomID(userData);
            if (resultSet.next()) {
                String soPhong = resultSet.getString("RoomNumber");
                int maPhong = resultSet.getInt("RoomID");
                roomNumber.setText(soPhong);
                roomID = maPhong;
            }
        } catch (SQLException e) {
            System.out.println("Failed to fetch data from the database.");
            e.printStackTrace();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        // Truyền Tên mặt hàng/Services
        try {
            ResultSet resultSet = Service_DAO.read();
            ObservableList<String> tenMatHangList = FXCollections.observableArrayList();
            while (resultSet.next()) {
                String tenMatHang = resultSet.getString("ServiceName");
                tenMatHangList.add(tenMatHang);
            }
            fTenMatHang.setItems(tenMatHangList);
        } catch (SQLException e) {
            System.out.println("Failed to fetch data from the database.");
            e.printStackTrace();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        // Khởi tạo TableView và các cột
        itemList = FXCollections.observableArrayList();
        colTenMatHang.setCellValueFactory(new PropertyValueFactory<>("tenMatHang"));
        colSoLuong.setCellValueFactory(new PropertyValueFactory<>("soLuong"));
        colDonGia.setCellValueFactory(new PropertyValueFactory<>("donGia"));
        colThanhTien.setCellValueFactory(new PropertyValueFactory<>("thanhTien"));
        tableViewRoomDetail.setItems(itemList);
        // Căn phải các cột
        colSoLuong.setStyle("-fx-alignment: CENTER-RIGHT;");
        colDonGia.setStyle("-fx-alignment: CENTER-RIGHT;");
        colThanhTien.setStyle("-fx-alignment: CENTER-RIGHT;");
    }


}
