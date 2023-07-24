package com.example.hotelproject;

import java.sql.*;

public class RoomCheckIn_DAO {
    private static Connection connection = Conect.getInstance();
    public static ResultSet read() {
        ResultSet rs;
        try {
            Statement stmt = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
                    ResultSet.CONCUR_UPDATABLE);
            String sql = "SELECT * FROM RoomCheckIns";
            rs = stmt.executeQuery(sql);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return rs;
    }

    public static String showCheckInInformationWithID(int checkInID) {
        String checkInTime = null;
        ResultSet rs = null;
        PreparedStatement stmt = null;
        try {
            String sql = "SELECT CheckInTime FROM RoomCheckIns WHERE CheckInID = ?";
            stmt = connection.prepareStatement(sql);
            stmt.setInt(1, checkInID);

            rs = stmt.executeQuery();

            if (rs.next()) {
                checkInTime = rs.getString("CheckInTime");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return checkInTime;
    }

    //Thai Bao them code
    public static int geCustomerIDWithCheckInID(int checkInID) {
        int customerID = 0;
        ResultSet rs = null;
        PreparedStatement stmt = null;
        try {
            String sql = "SELECT CustomerID FROM RoomCheckIns WHERE CheckInID = ?";
            stmt = connection.prepareStatement(sql);
            stmt.setInt(1, checkInID);

            rs = stmt.executeQuery();

            if (rs.next()) {
                customerID = rs.getInt("CustomerID");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return customerID;
    }
}
