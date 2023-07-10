package com.example.hotelproject;

import java.sql.Timestamp;
import java.time.LocalDateTime;

public class RoomCheckIn {
    private int checkInID;
    private int customerID;
    private int bookingID;
    private Timestamp checkInTime;
    private int nop;
    private boolean isDeleted;

    public RoomCheckIn( int bookingID, int nop) {
        this.checkInID = checkInID;
        this.customerID = customerID;
        this.bookingID = bookingID;
        this.checkInTime = checkInTime;
        this.isDeleted = false;
        this.nop =nop;
    }

    public RoomCheckIn(int nop) {
        this.checkInID = checkInID;
        this.customerID = customerID;
        this.bookingID = bookingID;
        this.checkInTime = checkInTime;
        this.isDeleted = false;
        this.nop =nop;
    }

    // Getters and setters

    public int getNop() {
        return nop;
    }

    public void setNop(int nop) {
        this.nop = nop;
    }

    public int getCustomerID() {
        return customerID;
    }

    public void setCustomerID(int customerID) {
        this.customerID = customerID;
    }

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

    public Timestamp getCheckInTime() {
        return checkInTime;
    }

    public void setCheckInTime(Timestamp checkInTime) {
        this.checkInTime = checkInTime;
    }

    public boolean isDeleted() {
        return isDeleted;
    }

    public void setDeleted(boolean deleted) {
        isDeleted = deleted;
    }
}

