package com.bignerdranch.android.familymap;

import java.util.List;

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

    private PersonResult mPersonResult;
    private EventResult mEventResult;
    private String currentPersonID;
    private String currentAuthToken;
    private Boolean isLoggedIn;






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
    public void setIsLoggedIn(boolean isLoggedIn) {
        this.isLoggedIn = isLoggedIn;
    }
    public boolean getIsLoggedIn() {
        return isLoggedIn;
    }
}
