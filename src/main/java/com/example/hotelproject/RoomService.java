package com.example.hotelproject;

public class RoomService {
    private int roomServiceID;
    private int bookingID;
    private int serviceID;
    private int quantity;
    private double servicePrice;
    private boolean isDeleted;

    // Constructor
    public RoomService(int roomServiceID, int bookingID, int serviceID, int quantity, double servicePrice, boolean isDeleted) {
        this.roomServiceID = roomServiceID;
        this.bookingID = bookingID;
        this.serviceID = serviceID;
        this.quantity = quantity;
        this.servicePrice = servicePrice;
        this.isDeleted = isDeleted;
    }

    public int getRoomServiceID() {
        return roomServiceID;
    }

    public void setRoomServiceID(int roomServiceID) {
        this.roomServiceID = roomServiceID;
    }

    public int getBookingID() {
        return bookingID;
    }

    public void setBookingID(int bookingID) {
        this.bookingID = bookingID;
    }

    public int getServiceID() {
        return serviceID;
    }

    public void setServiceID(int serviceID) {
        this.serviceID = serviceID;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public double getServicePrice() {
        return servicePrice;
    }

    public void setServicePrice(double servicePrice) {
        this.servicePrice = servicePrice;
    }

    public boolean isDeleted() {
        return isDeleted;
    }

    public void setDeleted(boolean deleted) {
        isDeleted = deleted;
    }
}

