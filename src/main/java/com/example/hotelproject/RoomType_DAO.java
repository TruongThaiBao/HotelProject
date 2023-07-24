package com.example.hotelproject;

import java.sql.*;

public class RoomType_DAO {
    private static Connection connection = Conect.getInstance();
    public static ResultSet read() {
        ResultSet rs;
        try {
            Statement stmt = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
                    ResultSet.CONCUR_UPDATABLE);
            String sql = "SELECT * FROM RoomTypes";
            rs = stmt.executeQuery(sql);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return rs;
    }

    public static double getBasePriceByRoomID(int roomID) {
        double basePrice = 0;

        try {
            String sqlRoom = "SELECT RoomTypeID FROM Rooms WHERE RoomID = ?";
            PreparedStatement stmtRoom = connection.prepareStatement(sqlRoom);
            stmtRoom.setInt(1, roomID);

            ResultSet rsRoom = stmtRoom.executeQuery();

            if (rsRoom.next()) {
                int roomTypeID = rsRoom.getInt("RoomTypeID");

                String sqlRoomType = "SELECT BasePrice FROM RoomTypes WHERE RoomTypeID = ?";
                PreparedStatement stmtRoomType = connection.prepareStatement(sqlRoomType);
                stmtRoomType.setInt(1, roomTypeID);

                ResultSet rsRoomType = stmtRoomType.executeQuery();

                if (rsRoomType.next()) {
                    basePrice = rsRoomType.getDouble("BasePrice");
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return basePrice;
    }
}
