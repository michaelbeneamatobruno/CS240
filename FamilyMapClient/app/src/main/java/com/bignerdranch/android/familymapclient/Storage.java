package com.bignerdranch.android.familymapclient;

import com.bignerdranch.android.familymapclient.Models.EventModel;
import com.bignerdranch.android.familymapclient.Models.EventResult;
import com.bignerdranch.android.familymapclient.Models.PersonModel;
import com.bignerdranch.android.familymapclient.Models.PersonResult;

import java.util.ArrayList;

class Storage {
    private static Storage INSTANCE = null;

    static Storage getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new Storage();
        }
        return INSTANCE;
    }

    private Storage() {
    }

    PersonResult mPersonResult;
    EventResult mEventResult;
    String currentPersonID;
    String currentAuthToken;
    Boolean isLoggedIn;






    public String getCurrentPersonID() {
        return currentPersonID;
    }
    public void setCurrentPersonID(String currentPersonID) {
        this.currentPersonID = currentPersonID;
    }
    public String getCurrentAuthToken() {
        return currentAuthToken;
    }
    public void setCurrentAuthToken(String currentAuthToken) {
        this.currentAuthToken = currentAuthToken;
    }
    public PersonResult getPersonResult() {
        return mPersonResult;
    }
    public void setPersonResult(PersonResult personResult) {
        mPersonResult = personResult;
    }
    public EventResult getEventResult() {
        return mEventResult;
    }
    public void setEventResult(EventResult eventResult) {
        mEventResult = eventResult;
    }
    public PersonModel getPerson(String personID) {
        for (PersonModel person : mPersonResult.getPeople()) {
            if (person.getPersonID().equals(personID)) {
                return person;
            }
        }
        return null;
    }
    public EventModel getEvent(String eventID) {
        for (EventModel event : mEventResult.getEvents()) {
            if (event.getEventID().equals(eventID)) {
                return event;
            }
        }
        return null;
    }
    public Boolean getLoggedIn() {
        return isLoggedIn;
    }
    public void setLoggedIn(Boolean loggedIn) {
        isLoggedIn = loggedIn;
    }
    public ArrayList getEvents(String personID) {
        ArrayList events = new ArrayList<EventModel>();
        for (EventModel event : mEventResult.getEvents()) {
            if (event.getPerson().equals(personID)) {
                events.add(event);
            }
        }
        ArrayList chronologicalEvents = new ArrayList();
        while (events.size() > 0) {
            EventModel current = (EventModel) events.get(0);
            for (int i = 0; i < events.size(); i++) {
                EventModel check = (EventModel) events.get(i);
                if (current.getYear() > check.getYear()) {
                    current = check;
                }
            }
            chronologicalEvents.add(current);
            events.remove(current);
        }
        return chronologicalEvents;
    }
    public EventModel getBirth(String personID) {
        ArrayList events = getEvents(personID);
        for (int i = 0; i < events.size(); i++) {
            EventModel event = (EventModel) events.get(i);
            if (event.getEventType().equals("birth")) {
                return event;
            }
        }
        return null;
    }
    public ArrayList getRelatives(String personID) {
        ArrayList relatives = new ArrayList<String>();
        PersonModel person = getPerson(personID);
        if (person.getFatherID() != null) {
            PersonModel father = getPerson(person.getFatherID());
            String fatherStr = father.getGender() + "/" + father.getPersonID() + "/" + father.getFirstName() + " " + father.getLastName() + "\nFather";
            relatives.add(fatherStr);
        }
        if (person.getMotherID() != null) {
            PersonModel mother = getPerson(person.getMotherID());
            String motherStr = mother.getGender() + "/" + mother.getPersonID() + "/" + mother.getFirstName() + " " + mother.getLastName() + "\nMother";
            relatives.add(motherStr);
        }
        if (person.getSpouseID() != null) {
            PersonModel spouse = getPerson(person.getSpouseID());
            String spouseStr = spouse.getGender() + "/" + spouse.getPersonID() + "/" + spouse.getFirstName() + " " + spouse.getLastName() + "\nSpouse";
            relatives.add(spouseStr);
        }
        for (PersonModel i : mPersonResult.getPeople()) {
            if (i.getMotherID() != null && i.getFatherID() != null) {
                if ((i.getMotherID().equals(personID))) {
                    String childStr = i.getGender() + "/" + i.getPersonID() + "/" + i.getFirstName() + " " + i.getLastName() + "\nChild";
                    relatives.add(childStr);
                }
                else if (i.getFatherID().equals(personID)) {
                    String childStr = i.getGender() + "/" + i.getPersonID() + "/" + i.getFirstName() + " " + i.getLastName() + "\nChild";
                    relatives.add(childStr);
                }
            }
        }
        return relatives;
    }
    public ArrayList getAllEventTypes() {
        ArrayList<String> eventTypes = new ArrayList<>();
        for (EventModel event : mEventResult.getEvents()) {
            eventTypes.add(event.getEventType());
        }
        return eventTypes;
    }
}
