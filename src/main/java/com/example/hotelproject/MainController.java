package com.example.hotelproject;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.io.IOException;

public class MainController {
    @FXML
    private StackPane rightPane;

    @FXML
    private void onDatPhongButtonClick() {
        rightPane.getChildren().clear();
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("DatPhongView.fxml"));
            AnchorPane datPhongView = loader.load();
            rightPane.getChildren().setAll(datPhongView);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void onHangHoaButtonClick() {
        rightPane.getChildren().clear();
        try {
            AnchorPane qlsp = FXMLLoader.load(getClass().getResource("qlsp.fxml"));
            rightPane.getChildren().setAll(qlsp);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void onPhongButtonClick() {
        rightPane.getChildren().clear();
        try {
            AnchorPane qlp = FXMLLoader.load(getClass().getResource("ttp.fxml"));
            rightPane.getChildren().setAll(qlp);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void onNhanVienButtonClick() {
        rightPane.getChildren().clear();
        try {
            AnchorPane qlnv = FXMLLoader.load(getClass().getResource("qlnv.fxml"));
            rightPane.getChildren().setAll(qlnv);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void onThongKeButtonClick() {
        rightPane.getChildren().clear();
        try {
            AnchorPane thongKeView = FXMLLoader.load(getClass().getResource("ThongKeView.fxml"));
            rightPane.getChildren().setAll(thongKeView);
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
}
