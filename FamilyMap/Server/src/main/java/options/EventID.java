package options;

import database.Database;
import models.EventModel;
import results.EventIDResult;

//eventID class, returns an eventID result based off an eventID
public class EventID {

    String eventID;

    public EventID(String eventID) {
        this.eventID = eventID;
    }
    public EventIDResult getEvent() {
        Database database = Database.database;
        database.openConnection();
        EventModel event = database.getEvent(eventID);
        database.closeConnection(true);
        return new EventIDResult(event);
    }
}
