package com.example.hotelproject;

import javafx.event.Event;
import javafx.event.EventType;

public class ThanhToanEvent extends Event {
    public static final EventType<ThanhToanEvent> THANH_TOAN_EVENT = new EventType<>(Event.ANY, "THANH_TOAN_EVENT");

    public ThanhToanEvent() {
        super(THANH_TOAN_EVENT);
    }
}
