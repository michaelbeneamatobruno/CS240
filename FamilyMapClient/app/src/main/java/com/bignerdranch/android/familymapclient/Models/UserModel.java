package com.bignerdranch.android.familymapclient.Models;

/**
 *A registered User in the database.
 *<pre>
 *<b>Domain</b>:
 *   username           : String     -- A unique non-empty string
 *   password         : String     -- A non-empty string
 *   firstName        : String     -- A non-empty string
 *   lastName         : String     -- A non-empty string
 *   gender         : String     -- either f or m
 *   personID         : String     -- A unique personID assigned to the User from a generated person object, not necessarily a string
 *
 *   <i>Invariant</i>: id &ge; 0 AND isAlphabetic(name) AND |name| &ge; 1 //TODO fix this!!!
 *</pre>
 */




public class UserModel {

    public String username;
    public String password;
    public String email;
    public String firstName;
    public String lastName;
    public String gender;
    public String personID;

//Constructors
    /**
     *The constructor for the User
     *constructor
     *<pre>
     *<b>Constraints on the input</b>:
     *  id &ge; 0 AND name &ne; null AND phoneNumbers &ne; null AND //TODO fix this!!!
     *  name.matches("[a-zA-Z]+")
     *
     *<b>Result:</b>"
     *  this.id = id AND this.name = name AND this.phoneNumbers = phoneNumbers
     *</pre>
     *@param username the username of the User
     *@param password the password of the User
     *@param firstName the first name of the User
     *@param lastName the last name of the User
     *@param gender the gender of the User
     *@param personID
     */
    public UserModel(String username, String password, String email, String firstName, String lastName, String gender, String personID) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.gender = gender;
        this.personID = personID;
    }

    public UserModel() {
        username = null;
        password = null;
        email = null;
        firstName = null;
        lastName = null;
        gender = null;
        personID = null;
    }

    public String getUsername() {
        return username;
    }
    public String getPassword() {
        return password;
    }
    public  String getEmailAddress() {
        return email;
    }
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

    public void setUsername(String username) {
        this.username = username;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    public void setEmailAddress(String email) {
        this.email = email;
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
}
