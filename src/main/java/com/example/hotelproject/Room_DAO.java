package com.example.hotelproject;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.sql.*;
import java.time.LocalDate;
import java.util.Optional;
import java.util.ResourceBundle;

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

    public static ResultSet getIDByRoomNumber(String roomNumber){
        ResultSet rs;
        try {
            String sql = "SELECT * FROM Rooms WHERE RoomNumber = ?";
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setString(1, roomNumber);
            rs = stmt.executeQuery();

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
            String sql = "SELECT RoomTypes.BasePrice, Rooms.RoomNumber, Rooms.RoomID FROM Rooms JOIN RoomTypes ON Rooms.RoomTypeID = RoomTypes.RoomTypeID WHERE Rooms.RoomID = ?";
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

            String query2 = "INSERT INTO RoomCheckIns (RoomID, CustomerID, BookingID, NOP) VALUES (?, ?, ?, ?)";
            statement2 = connection.prepareStatement(query2);
            statement2.setInt(1, roomCheckIn.getRoomID());
            statement2.setInt(2, customerID);
            statement2.setInt(3, roomCheckIn.getBookingID());
            statement2.setInt(4, roomCheckIn.getNop());
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

            String query2 = "INSERT INTO RoomCheckIns (RoomID, CustomerID, NOP) VALUES ( ?, ?, ?)";
            statement2 = connection.prepareStatement(query2);
            statement2.setInt(1, roomCheckIn.getRoomID());
            statement2.setInt(2, customerID);
            statement2.setInt(3, roomCheckIn.getNop());
            statement2.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public static void updateRoomStatus(int roomID, int status) {
        Connection connection = null;
        PreparedStatement statement = null;

        try {
            connection = Conect.getInstance();
            String query = "UPDATE Rooms SET Status = ? WHERE RoomID = ?";
            statement = connection.prepareStatement(query);

            statement.setInt(1, status);
            statement.setInt(2, roomID);
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public static Room getRoomByRoomNumber(String roomNumber) throws SQLException {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        Room room = null;

        try {
            connection = Conect.getInstance();
            String query = "SELECT * FROM Rooms WHERE RoomNumber = ?";
            statement = connection.prepareStatement(query);
            statement.setString(1, roomNumber);
            resultSet = statement.executeQuery();

            if (resultSet.next()) {
                int roomID = resultSet.getInt("RoomID");
                boolean status = resultSet.getBoolean("Status");

                room = new Room(roomID, roomNumber, status);
            }
        } catch (SQLException e) {
            throw new SQLException("Failed to fetch room data from the database.", e);
        }
        return room;
    }
    public static RoomCheckIn getRoomCheckInByRoomID(int roomID) throws SQLException {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        RoomCheckIn roomCheckIn = null;

        try {
            connection = Conect.getInstance();
            String query = "SELECT * FROM RoomCheckIns WHERE RoomID = ?";
            statement = connection.prepareStatement(query);
            statement.setInt(1, roomID);
            resultSet = statement.executeQuery();

            if (resultSet.next()) {
                int checkInID = resultSet.getInt("CheckInID");
                int customerID = resultSet.getInt("CustomerID");
                int nop = resultSet.getInt("NOP");

                roomCheckIn = new RoomCheckIn(checkInID, roomID, customerID, nop);
            }
        } catch (SQLException e) {
            throw new SQLException("Failed to fetch room check-in data from the database.", e);
        }

        return roomCheckIn;
    }
    public static Customer getCustomerByCustomerID(int customerID) throws SQLException {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        Customer customer = null;

        try {
            connection = Conect.getInstance();
            String query = "SELECT * FROM Customers WHERE CustomerID = ?";
            statement = connection.prepareStatement(query);
            statement.setInt(1, customerID);
            resultSet = statement.executeQuery();

            if (resultSet.next()) {
                String fullName = resultSet.getString("FullName");
                String idNumber = resultSet.getString("IDNumber");
                String phoneNumber = resultSet.getString("PhoneNumber");

                customer = new Customer(customerID, fullName, idNumber, phoneNumber);
            }
        } catch (SQLException e) {
            throw new SQLException("Failed to fetch customer data from the database.", e);
        }

        return customer;
    }

}
