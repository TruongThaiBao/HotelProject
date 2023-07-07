package com.example.hotelproject;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.sql.ResultSet;
import java.sql.SQLException;

public class LoginController {

    @FXML
    private TextField txtUsername;
    @FXML
    private TextField txtPassword;


//    @FXML
//    protected void onLoginButtonClick() {
//        String username = txtUsername.getText();
//        String password = txtPassword.getText();
//
//        // Thực hiện kiểm tra đăng nhập và xác thực thông tin người dùng
//        boolean isValidLogin = performLogin(username, password);
//
//        if (isValidLogin) {
//            // Xử lý đăng nhập thành công
//            System.out.println("Đăng nhập thành công!");
//        } else {
//            // Xử lý đăng nhập thất bại
//            System.out.println("Sai tên đăng nhập hoặc mật khẩu!");
//        }
//    }

//    private boolean performLogin(String username, String password) {
//        try {
//            // Kết nối đến cơ sở dữ liệu
//            Connection conn = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD);
//
//            // Chuẩn bị câu truy vấn
//            String sql = "SELECT * FROM Users WHERE Username = ? AND Password = ?";
//            PreparedStatement statement = conn.prepareStatement(sql);
//            statement.setString(1, username);
//            statement.setString(2, password);
//
//            // Thực hiện truy vấn
//            ResultSet rs = statement.executeQuery();
//
//            // Kiểm tra kết quả truy vấn
//            boolean isValidLogin = rs.next();
//
//            // Đóng kết nối và các đối tượng
//            rs.close();
//            statement.close();
//            conn.close();
//
//            return isValidLogin;
//        } catch (SQLException e) {
//            e.printStackTrace();
//            return false;
//        }
//    }

}