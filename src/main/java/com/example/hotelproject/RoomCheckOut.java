package com.example.hotelproject;

import java.time.LocalDateTime;

public class RoomCheckOut {
    private int checkOutID;
    private int bookingID;
    private LocalDateTime checkOutTime;
    private boolean isDeleted;

    public RoomCheckOut(int checkOutID, int bookingID, LocalDateTime checkOutTime, boolean isDeleted) {
        this.checkOutID = checkOutID;
        this.bookingID = bookingID;
        this.checkOutTime = checkOutTime;
        this.isDeleted = isDeleted;
    }

    // Getters and setters

    public int getCheckOutID() {
        return checkOutID;
    }

    public void setCheckOutID(int checkOutID) {
        this.checkOutID = checkOutID;
    }

    public int getBookingID() {
        return bookingID;
    }

    public void setBookingID(int bookingID) {
        this.bookingID = bookingID;
    }

    public LocalDateTime getCheckOutTime() {
        return checkOutTime;
    }

    public void setCheckOutTime(LocalDateTime checkOutTime) {
        this.checkOutTime = checkOutTime;
    }

    public boolean isDeleted() {
        return isDeleted;
    }

    public void setDeleted(boolean deleted) {
        isDeleted = deleted;
    }
}

