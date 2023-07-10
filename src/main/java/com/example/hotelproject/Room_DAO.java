package com.example.hotelproject;

import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;

public class Room_DAO {
    private static Connection connection = Conect.getInstance();
    public static ResultSet read() {
        ResultSet rs;
        try {
            Statement stmt = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
                    ResultSet.CONCUR_UPDATABLE);
            String sql = "SELECT * FROM Rooms";
            rs = stmt.executeQuery(sql);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return rs;
    }

    public static ResultSet showBookingID() {
        ResultSet rs;
        try {
            Statement stmt = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
                    ResultSet.CONCUR_UPDATABLE);
            String sql = "SELECT BookingID FROM RoomBookings";
            rs = stmt.executeQuery(sql);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return rs;
    }

    public static ResultSet showRoomInformationWithRoomID(String roomID) {
        ResultSet rs;
        try {
            String sql = "SELECT RoomTypes.BasePrice, Rooms.RoomNumber FROM Rooms JOIN RoomTypes ON Rooms.RoomTypeID = RoomTypes.RoomTypeID WHERE Rooms.RoomID = ?";
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setString(1, roomID);

            rs = stmt.executeQuery();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return rs;
    }

    public static void createCustomer_CheckIn(Customer customer, RoomCheckIn roomCheckIn) throws SQLException {
        Connection connection = null;
        PreparedStatement statement1 = null;
        PreparedStatement statement2 = null;
        ResultSet generatedKeys = null;

        try {
            connection = Conect.getInstance();
            String query1 = "INSERT INTO Customers (CustomerID, FullName, IDNumber, PhoneNumber) VALUES (DEFAULT, ?, ?, ?)";
            statement1 = connection.prepareStatement(query1, Statement.RETURN_GENERATED_KEYS);

            statement1.setString(1, customer.getFullName());
            statement1.setString(2, customer.getIdNumber());
            statement1.setString(3, customer.getPhoneNumber());
            statement1.executeUpdate();

            generatedKeys = statement1.getGeneratedKeys();
            int customerID = 0;
            if (generatedKeys.next()) {
                customerID = generatedKeys.getInt(1);
            }

            String query2 = "INSERT INTO RoomCheckIns (CustomerID, BookingID, NOP) VALUES (?, ?, ?)";
            statement2 = connection.prepareStatement(query2);
            statement2.setInt(1, customerID);
            statement2.setInt(2, roomCheckIn.getBookingID());
            statement2.setInt(3, roomCheckIn.getNop());
            statement2.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public static void createCustomer_CheckIn_NotBooking(Customer customer, RoomCheckIn roomCheckIn) throws SQLException {
        Connection connection = null;
        PreparedStatement statement1 = null;
        PreparedStatement statement2 = null;
        ResultSet generatedKeys = null;

        try {
            connection = Conect.getInstance();
            String query1 = "INSERT INTO Customers (CustomerID, FullName, IDNumber, PhoneNumber) VALUES (DEFAULT, ?, ?, ?)";
            statement1 = connection.prepareStatement(query1, Statement.RETURN_GENERATED_KEYS);

            statement1.setString(1, customer.getFullName());
            statement1.setString(2, customer.getIdNumber());
            statement1.setString(3, customer.getPhoneNumber());
            statement1.executeUpdate();

            generatedKeys = statement1.getGeneratedKeys();
            int customerID = 0;
            if (generatedKeys.next()) {
                customerID = generatedKeys.getInt(1);
            }

            String query2 = "INSERT INTO RoomCheckIns (CustomerID, NOP) VALUES ( ?, ?)";
            statement2 = connection.prepareStatement(query2);
            statement2.setInt(1, customerID);
            statement2.setInt(2, roomCheckIn.getNop());
            statement2.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

}
