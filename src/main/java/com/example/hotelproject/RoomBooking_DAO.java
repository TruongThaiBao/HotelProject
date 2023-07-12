package com.example.hotelproject;

import java.sql.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

public class RoomBooking_DAO {
    private static Connection connection = Conect.getInstance();
    public static ResultSet ReadCus(){
        ResultSet rs ;
        try {
            Statement stmt = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
                    ResultSet.CONCUR_UPDATABLE);
            String sql = "SELECT  * FROM customer ";
            rs = stmt.executeQuery(sql);
        }
        catch (SQLException e){
            throw  new RuntimeException(e);
        }
        return rs;
    }
    public static ResultSet showRooms() {
        ResultSet rs;
        try {
            String query = "SELECT roombookings.BookingID,roombookings.CustomerID, customers.FullName," +
                    "customers.IDNumber,customers.PhoneNumber," +
                    "roombookings.RoomID,roombookings.CheckInDate ,"+
                    "roombookings.CheckOutDate,roombookings.UserID ,customers.Deleted,roombookings.BookingTime" +
                    " FROM customers " +
                    "JOIN roombookings ON roombookings.CustomerID = customers.CustomerID";
            PreparedStatement stmt =  connection.prepareStatement(query);
            rs = stmt.executeQuery();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return rs;
    }
    public static ResultSet showCus() {
        ResultSet rs;
        try {
            String query = "SELECT * FROM customers";
            PreparedStatement stmt =  connection.prepareStatement(query);
            rs = stmt.executeQuery();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return rs;
    }
    public static ResultSet searchData(String option, String searchText) {
        ResultSet rs;
        try {
            String query = "SELECT * FROM roombookings " +
                    "JOIN customers ON roombookings.CustomerID = customers.CustomerID " +
                    "WHERE " + option + " = ?";
            PreparedStatement stmt =  connection.prepareStatement(query);
            stmt.setString(1, searchText);
            rs = stmt.executeQuery();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return rs;
    }
    public static void insertCustomer( int CustomerID, String FullName , String IDNumber , String PhoneNumber ) {
        try {
            String query = "INSERT INTO customers (CustomerID, FullName, IDNumber,PhoneNumber) " +
                    "VALUES (?,?,?,?)";
            PreparedStatement stmt = connection.prepareStatement(query);
            stmt.setInt(1, CustomerID);
            stmt.setString(2, FullName);
            stmt.setString(3, IDNumber);
            stmt.setString(4, PhoneNumber);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void insertBooking(int BookingID, int CustomerID, int RoomID , LocalDate BookingTime, LocalDate CheckinDate, LocalDate CheckOutDate, int UserID) {
        try {
            String query = "INSERT INTO roombookings (BookingID, CustomerID, RoomID,BookingTime,CheckInDate,CheckOutDate,UserID) " +
                    "VALUES (?, ?, ?,?,?,?,?)";
            PreparedStatement stmt = connection.prepareStatement(query);
            stmt.setInt(1, BookingID);
            stmt.setInt(2, CustomerID);
            stmt.setInt(3, RoomID);
            stmt.setDate(4, java.sql.Date.valueOf(BookingTime));
            stmt.setDate(5, java.sql.Date.valueOf(CheckinDate));
            stmt.setDate(6, java.sql.Date.valueOf(CheckOutDate));
            stmt.setInt(7,UserID);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public static void updateCustomer(int CustomerID , String nameCustomer,String phoneCustomer,String IDNumber){
        try {
            String query = "UPDATE customers (IDCustomer,FullName, IDNumber, PhoneNumber) " +
                    "VALUES (?, ?, ?,?)";
            PreparedStatement stmt = connection.prepareStatement(query);
            stmt.setInt(1,CustomerID);
            stmt.setString(2,nameCustomer);
            stmt.setString(3,IDNumber);
            stmt.setString(4,phoneCustomer);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void updateBooking(int bookID,int CustomerID, int roomID, LocalDate BookingTime,LocalDate checkinRoom, LocalDate checkoutRoom, int userID) {
        try {
            String query = "UPDATE roombookings SET  CustomerID = ?, RoomID = ?,BookingTime = ?,CheckInDate = ?,CheckOutDate = ?,UserID = ? WHERE BookingID = ? ";
            PreparedStatement stmt = connection.prepareStatement(query);
            stmt.setInt(1, CustomerID);
            stmt.setInt(2, roomID);
            stmt.setDate(3, java.sql.Date.valueOf(BookingTime));
            stmt.setDate(4, java.sql.Date.valueOf(checkinRoom));
            stmt.setDate(5, java.sql.Date.valueOf(checkoutRoom));
            stmt.setInt(6,userID);
            stmt.setInt(7, bookID);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public static void deleteBooking(int BookingID) {
        try {
            String query = "DELETE FROM roombookings WHERE BookingID = ?";
            PreparedStatement stmt = connection.prepareStatement(query);
            stmt.setInt(1, BookingID);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
