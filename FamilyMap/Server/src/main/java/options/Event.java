package options;

import java.util.ArrayList;


import database.Database;
import models.EventModel;
import models.PersonModel;
import results.EventResult;
import results.PersonResult;

//Event class, All events for the given user are returned as an event result
public class Event {

    String username;

    public Event(String username) {
        this.username = username;
    }
    public EventResult getEvents() {

        Person personOption = new Person(username);
        PersonResult personResult = personOption.getPeople();
        PersonModel[] people = personResult.getPeople();

        ArrayList array = new ArrayList<EventModel>();
        for (PersonModel person : people) {
            Database database = Database.database;
            EventModel[] events;
            database.openConnection();
            events = database.getAllEvents(person.getPersonID());
            if (events != null) {
                for (EventModel event : events) {
                    if (!array.contains(event)) {
                        array.add(event);
                    }
                }
            }
            database.closeConnection(true);
        }

        EventModel[] allEvents = new EventModel[array.size()];
        for (int i = 0; i < array.size(); i++) {
            allEvents[i] = (EventModel)array.get(i);
        }

        return new EventResult(allEvents);
    }
}
