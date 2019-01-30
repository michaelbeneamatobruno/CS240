package com.bignerdranch.android.familymapclient.Models;


//person model class, used all over the place
public class PersonModel {
    private String descendant;
    private String firstName;
    private String lastName;
    private String gender;
    private String personID;
    private String father;
    private String mother;
    private String spouse;

    public PersonModel(String descendant, String firstName, String lastName, String gender, String personID, String father, String mother, String spouse) {
        this.descendant = descendant;
        this.firstName = firstName;
        this.lastName = lastName;
        this.gender = gender;
        this.personID = personID;
        this.father = father;
        this.mother = mother;
        this.spouse = spouse;
    }
    public PersonModel() {
        descendant = null;
        firstName = null;
        lastName = null;
        gender = null;
        personID = null;
        father = null;
        mother = null;
        spouse = null;
    }
    //getters
    public String getFirstName() {
        return firstName;
    }
    public String getLastName() {
        return lastName;
    }
    public String getGender() {
        return gender;
    }
    public String getPersonID() {
        return personID;
    }
    public String getFatherID() {
        return father;
    }
    public String getMotherID() {
        return mother;
    }
    public String getSpouseID() {
        return spouse;
    }

    //setters
    public void setDescendant(String descendant) {
        this.descendant = descendant;
    }
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
    public void setGender(String gender) {
        this.gender = gender;
    }
    public void setPersonID(String personID) {
        this.personID = personID;
    }
    public void setFatherID(String fatherID) {
        this.father = fatherID;
    }
    public void setMotherID(String motherID) {
        this.mother = motherID;
    }
    public void setSpouseID(String spouseID) {
        this.spouse = spouseID;
    }
}
