package com.bignerdranch.android.familymapclient.Models;

import com.bignerdranch.android.familymapclient.Models.EventModel;

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
