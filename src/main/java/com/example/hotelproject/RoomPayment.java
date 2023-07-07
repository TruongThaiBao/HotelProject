package com.example.hotelproject;

public class RoomPayment {
    private int paymentID;
    private int bookingID;
    private double roomCharge;
    private double serviceCharge;
    private Double extraCharge;
    private Double discount;
    private boolean isDeleted;

    // Constructor
    public RoomPayment(int paymentID, int bookingID, double roomCharge, double serviceCharge, Double extraCharge, Double discount, boolean isDeleted) {
        this.paymentID = paymentID;
        this.bookingID = bookingID;
        this.roomCharge = roomCharge;
        this.serviceCharge = serviceCharge;
        this.extraCharge = extraCharge;
        this.discount = discount;
        this.isDeleted = isDeleted;
    }

    public int getPaymentID() {
        return paymentID;
    }

    public void setPaymentID(int paymentID) {
        this.paymentID = paymentID;
    }

    public int getBookingID() {
        return bookingID;
    }

    public void setBookingID(int bookingID) {
        this.bookingID = bookingID;
    }

    public double getRoomCharge() {
        return roomCharge;
    }

    public void setRoomCharge(double roomCharge) {
        this.roomCharge = roomCharge;
    }

    public double getServiceCharge() {
        return serviceCharge;
    }

    public void setServiceCharge(double serviceCharge) {
        this.serviceCharge = serviceCharge;
    }

    public Double getExtraCharge() {
        return extraCharge;
    }

    public void setExtraCharge(Double extraCharge) {
        this.extraCharge = extraCharge;
    }

    public Double getDiscount() {
        return discount;
    }

    public void setDiscount(Double discount) {
        this.discount = discount;
    }

    public boolean isDeleted() {
        return isDeleted;
    }

    public void setDeleted(boolean deleted) {
        isDeleted = deleted;
    }
}
