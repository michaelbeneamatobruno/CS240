package com.bignerdranch.android.familymapclient.Models;

//The model class for an event
public class EventModel {

    private String descendant;
    private String eventID;
    private String personID;
    private String country;
    private String city;
    private String eventType;
    private double latitude;
    private double longitude;
    private int year;

    public EventModel(String eventID, String descendant, String personID, String country, String city, String eventType, double latitude, double longitude, int year) {
        this.eventID = eventID;
        this.descendant = descendant;
        this.personID = personID;
        this.country = country;
        this.city = city;
        this.eventType = eventType;
        this.latitude = latitude;
        this.longitude = longitude;
        this.year = year;
    }
    public EventModel() {
        eventID = null;
        descendant = null;
        personID = null;
        country = null;
        city = null;
        eventType = null;
        latitude = -1.0;
        longitude = -1.0;
        year = -1;
    }

    //getters for data
    public String getEventID() {
        return eventID;
    }
    public String getDescendant() {
        return descendant;
    }
    public String getPerson() {
        return personID;
    }
    public String getEventType() {
        return eventType;
    }
    public double getLatitude() {
        return latitude;
    }
    public double getLongitude() {
        return longitude;
    }
    public String getCountry() {
        return country;
    }
    public String getCity() {
        return city;
    }
    public int getYear() {
        return year;
    }

    //setters for data
    public void setEventID(String eventID) {
        this.eventID = eventID;
    }
    public void setDescendant(String descendant) {
        this.descendant = descendant;
    }
    public void setPerson(String person) {
        this.personID = person;
    }
    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }
    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }
    public void setCountry(String country) {
        this.country = country;
    }
    public void setCity(String city) {
        this.city = city;
    }
    public void setEventType(String eventType) {
        this.eventType = eventType;
    }
    public void setYear(int year) {
        this.year = year;
    }
}
