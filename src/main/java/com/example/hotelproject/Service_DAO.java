package com.example.hotelproject;

import java.sql.*;

public class Service_DAO {
    private static Connection connection = Conect.getInstance();
    public static ResultSet read() {
        ResultSet rs;
        try {
            Statement stmt = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
                    ResultSet.CONCUR_UPDATABLE);
            String sql = "SELECT * FROM Services";
            rs = stmt.executeQuery(sql);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return rs;
    }
    public static double getDonGiaByTenMatHang(String tenMatHang) throws SQLException {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        double donGia = 0;

        try {
            connection = Conect.getInstance();
            String query = "SELECT ServicePrice FROM Services WHERE ServiceName = ?";
            statement = connection.prepareStatement(query);
            statement.setString(1, tenMatHang);
            resultSet = statement.executeQuery();

            if (resultSet.next()) {
                donGia = resultSet.getDouble("ServicePrice");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return donGia;
    }

    public static double getIDByTenMatHang(String tenMatHang) throws SQLException {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        double id = 0;

        try {
            connection = Conect.getInstance();
            String query = "SELECT ServiceID FROM Services WHERE ServiceName = ?";
            statement = connection.prepareStatement(query);
            statement.setString(1, tenMatHang);
            resultSet = statement.executeQuery();

            if (resultSet.next()) {
                id = resultSet.getInt("ServiceID");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return id;
    }

    public static String getTenMatHangByID(String serviceID) throws SQLException {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        String tenMatHang = null;

        try {
            connection = Conect.getInstance();
            String query = "SELECT ServiceName FROM Services WHERE ServiceID = ?";
            statement = connection.prepareStatement(query);
            statement.setString(1, serviceID);
            resultSet = statement.executeQuery();

            if (resultSet.next()) {
                tenMatHang = resultSet.getString("ServiceName");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return tenMatHang;
    }
}
