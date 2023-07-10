package com.example.hotelproject;

import java.sql.*;

public class RoomList_DAO {
    private static Connection connection = Conect.getInstance();
    public static ResultSet Read(){
        ResultSet rs ;
        try {
            Statement stmt = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
                    ResultSet.CONCUR_UPDATABLE);
            String sql = "SELECT * FROM Rooms";
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
            String query = "SELECT rooms.RoomID, rooms.RoomNumber, rooms.Status,rooms.RoomTypeID,roomtypes.BasePrice, roomtypes.RoomTypeName " +
                    "FROM rooms " +
                    "JOIN roomtypes ON rooms.RoomTypeID = roomtypes.RoomTypeID ORDER BY RoomID ASC";
            PreparedStatement stmt =  connection.prepareStatement(query);
            rs = stmt.executeQuery();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return rs;
    }
    public static int calculateTotalRoom() {
        int totalRoom = 0;
        try {
            String query = "SELECT COUNT(RoomID) FROM rooms";
            PreparedStatement stmt = connection.prepareStatement(query);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                totalRoom = rs.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return totalRoom;
    }
    public static void insertRoom(int roomID, String roomNumber, boolean status, int loaiRoom) {
        try {
            String query = "INSERT INTO rooms (RoomID, RoomNumber, Status,RoomTypeID) " +
                    "VALUES (?, ?, ?,?)";
            PreparedStatement stmt = connection.prepareStatement(query);
            stmt.setInt(1, roomID);
            stmt.setString(2, roomNumber);
            stmt.setBoolean(3, status);
            stmt.setInt(4,loaiRoom);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public static void updateRoom(int roomID, String roomNumber, boolean status, int roomTypeID) {
        try {
            String query = "UPDATE rooms SET RoomNumber = ?, Status = ?, RoomTypeID = ? WHERE RoomID = ?";
            PreparedStatement stmt = connection.prepareStatement(query);
            stmt.setString(1, roomNumber);
            stmt.setBoolean(2, status);
            stmt.setInt(3, roomTypeID);
            stmt.setInt(4, roomID);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public static void deleteRoom(int roomID, String roomNumber, boolean status, int roomTypeID) {
        try {
            String query = "DELETE FROM rooms WHERE RoomID = ? AND RoomNumber = ? AND Status = ? AND RoomTypeID = ?";
            PreparedStatement stmt = connection.prepareStatement(query);
            stmt.setInt(1, roomID);
            stmt.setString(2, roomNumber);
            stmt.setBoolean(3, status);
            stmt.setInt(4, roomTypeID);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
