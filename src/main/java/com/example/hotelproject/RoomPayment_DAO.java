package com.example.hotelproject;

import java.sql.*;

public class RoomPayment_DAO {
    private static Connection connection = Conect.getInstance();
    public static ResultSet read() {
        ResultSet rs;
        try {
            Statement stmt = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
                    ResultSet.CONCUR_UPDATABLE);
            String sql = "SELECT * FROM RoomPayments";
            rs = stmt.executeQuery(sql);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return rs;
    }

    public static void createRoomPayment(int customerID, int roomID, int checkInID, int checkOutID, double roomCharge,
                                         double extraCharge, double discount, int userID) {
        String sql = "INSERT INTO RoomPayments (CustomerID, RoomID, CheckInID, CheckOutID, RoomCharge, ExtraCharge, Discount, UserID) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

        try (
            PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, customerID);
            statement.setInt(2, roomID);
            statement.setInt(3, checkInID);
            statement.setInt(4, checkOutID);
            statement.setDouble(5, roomCharge);
            statement.setDouble(6, extraCharge);
            statement.setDouble(7, discount);
            statement.setInt(8, userID);

            statement.executeUpdate();
            System.out.println("Lưu bill thành công !");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
