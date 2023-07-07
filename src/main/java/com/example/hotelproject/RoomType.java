package com.example.hotelproject;

public class RoomType {
    private int roomTypeID;
    private String roomTypeName;
    private double basePrice;
    private boolean isDeleted;

    // Constructor
    public RoomType(int roomTypeID, String roomTypeName, double basePrice, boolean isDeleted) {
        this.roomTypeID = roomTypeID;
        this.roomTypeName = roomTypeName;
        this.basePrice = basePrice;
        this.isDeleted = isDeleted;
    }

    public int getRoomTypeID() {
        return roomTypeID;
    }

    public void setRoomTypeID(int roomTypeID) {
        this.roomTypeID = roomTypeID;
    }

    public String getRoomTypeName() {
        return roomTypeName;
    }

    public void setRoomTypeName(String roomTypeName) {
        this.roomTypeName = roomTypeName;
    }

    public double getBasePrice() {
        return basePrice;
    }

    public void setBasePrice(double basePrice) {
        this.basePrice = basePrice;
    }

    public boolean isDeleted() {
        return isDeleted;
    }

    public void setDeleted(boolean deleted) {
        isDeleted = deleted;
    }
}
