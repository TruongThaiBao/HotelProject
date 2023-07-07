package com.example.hotelproject;

import java.time.LocalDateTime;

public class RoomCheckIn {
    private int checkInID;
    private int bookingID;
    private LocalDateTime checkInTime;
    private boolean isDeleted;

    public RoomCheckIn(int checkInID, int bookingID, LocalDateTime checkInTime, boolean isDeleted) {
        this.checkInID = checkInID;
        this.bookingID = bookingID;
        this.checkInTime = checkInTime;
        this.isDeleted = isDeleted;
    }

    // Getters and setters

    public int getCheckInID() {
        return checkInID;
    }

    public void setCheckInID(int checkInID) {
        this.checkInID = checkInID;
    }

    public int getBookingID() {
        return bookingID;
    }

    public void setBookingID(int bookingID) {
        this.bookingID = bookingID;
    }

    public LocalDateTime getCheckInTime() {
        return checkInTime;
    }

    public void setCheckInTime(LocalDateTime checkInTime) {
        this.checkInTime = checkInTime;
    }

    public boolean isDeleted() {
        return isDeleted;
    }

    public void setDeleted(boolean deleted) {
        isDeleted = deleted;
    }
}

