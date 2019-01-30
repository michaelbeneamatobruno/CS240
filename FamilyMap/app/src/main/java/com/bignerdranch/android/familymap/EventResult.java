package com.bignerdranch.android.familymap;

//event result, an array of events
public class EventResult {
    EventModel[] events;

    public EventResult(EventModel[] events) {
        this.events = events;
    }

    //getter
    public EventModel[] getEvents() {
        return events;
    }
}