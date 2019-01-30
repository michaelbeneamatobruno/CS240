package results;

import models.EventModel;

//eventID result, an event
public class EventIDResult {

    EventModel event;

    public EventIDResult(EventModel event) {
        this.event = event;
    }

    //getter
    public EventModel getEvent() {
        return event;
    }

}
