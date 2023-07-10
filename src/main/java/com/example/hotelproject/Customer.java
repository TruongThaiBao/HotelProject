package com.example.hotelproject;

public class Customer {
    private int customerID;
    private String fullName;
    private String idNumber;
    private String phoneNumber;
    private boolean isDeleted;

    public Customer(String fullName, String idNumber, String phoneNumber) {
        this.customerID = customerID;
        this.fullName = fullName;
        this.idNumber = idNumber;
        this.phoneNumber = phoneNumber;
        this.isDeleted = isDeleted;
    }

    // Getters and setters

    public int getCustomerID() {
        return customerID;
    }

    public void setCustomerID(int customerID) {
        this.customerID = customerID;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getIdNumber() {
        return idNumber;
    }

    public void setIdNumber(String idNumber) {
        this.idNumber = idNumber;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public boolean isDeleted() {
        return isDeleted;
    }

    public void setDeleted(boolean deleted) {
        isDeleted = deleted;
    }
}

