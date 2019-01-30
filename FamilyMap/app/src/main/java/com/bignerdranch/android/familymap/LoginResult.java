package com.bignerdranch.android.familymap;

//login result, contains the authToken, username, personID, and result string
public class LoginResult {

    String authToken;
    String username;
    String personID;
    String result;

    public LoginResult(String authToken, String username, String personID) {
        this.authToken = authToken;
        this.username = username;
        this.personID = personID;
    }

    public LoginResult(String result) {
        this.result = result;
    }

    //getters
    public String getAuthToken() {
        return authToken;
    }
    public String getUsername() {
        return username;
    }
    public String getPersonID() {
        return personID;
    }
    public String getResult() {
        return result;
    }

    //setters
    public void setUsername(String username) {
        this.username = username;
    }
    public void setPersonID(String personID) {
        this.personID = personID;
    }
    public void setResult(String result) {
        this.result = result;
    }
}
