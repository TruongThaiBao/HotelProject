package com.example.hotelproject;

import javafx.beans.property.*;

public class Room {
    private int roomID;
    private String roomNumber;
    private int roomTypeID;
    private boolean isDeleted;
    private boolean status;
    private  String roomTypeName;
    private  String roomPrice;
    public Room(int roomID,String roomNumber, String roomTypeName, String roomPrice,int roomTypeID) {
        this.roomID = roomID;
        this.roomTypeName=roomTypeName;
        this.roomPrice=roomPrice;
        this.roomNumber=roomNumber;
        this.roomTypeID=roomTypeID;
    }
    public Room() {

    }

    public Room(int roomID, String roomNumber, boolean status) {
        this.roomID =roomID;
        this.roomNumber = roomNumber;
        this.status = status;
    }

    public String getRoomTypeName() {
        return roomTypeName;
    }

    public void setRoomTypeName(String roomTypeName) {
        this.roomTypeName = roomTypeName;
    }

    public String getRoomPrice() {
        return roomPrice;
    }

    public void setRoomPrice(String roomPrice) {
        this.roomPrice = roomPrice;
    }

    // Constructor
    public Room(int roomID, String roomNumber, int roomTypeID, boolean isDeleted, boolean Status , String roomTypeName , String roomPrice) {
        this.roomID = roomID;
        this.roomNumber = roomNumber;
        this.roomTypeID = roomTypeID;
        this.isDeleted = isDeleted;
        this.status = Status;
        this.roomTypeName=roomTypeName;
        this.roomPrice=roomPrice;
    }


    public int getRoomID() {
        return roomID;
    }

    public void setRoomID(int roomID) {
        this.roomID = roomID;
    }

    public String getRoomNumber() {
        return roomNumber;
    }

    public void setRoomNumber(String roomNumber) {
        this.roomNumber = roomNumber;
    }

    public int getRoomTypeID() {
        return roomTypeID;
    }

    public void setRoomTypeID(int roomTypeID) {
        this.roomTypeID = roomTypeID;
    }

    public boolean isDeleted() {
        return isDeleted;
    }

    public void setDeleted(boolean deleted) {
        isDeleted = deleted;
    }

    public boolean isStatus() {
        return status;
    }
    public void setStatus(boolean status) {
        this.status = status;
    }
    public IntegerProperty roomIDProperty() {
        return new SimpleIntegerProperty(roomID);
    }
    public StringProperty roomNumberProperty() {
        return new SimpleStringProperty(roomNumber);
    }
    public BooleanProperty statusProperty() {
        return new SimpleBooleanProperty(status);
    }
    public StringProperty roomTypeNameProperty() {
        return new SimpleStringProperty(roomTypeName);
    }
    public StringProperty roomPriceProperty() {
        return new SimpleStringProperty(roomPrice);
    }
}

