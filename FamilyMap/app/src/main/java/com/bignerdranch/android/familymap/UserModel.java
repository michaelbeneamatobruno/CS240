package com.bignerdranch.android.familymap;

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


//Queries
    /**
     * username getter
     *<pre>
     *<b>Constraints on the input</b>:
     *  None
     *</pre>
     *@return the username
     */
    public String getUsername() {
        return username;
    }
    /**
     * password getter
     *<pre>
     *<b>Constraints on the input</b>:
     *  None
     *</pre>
     *@return the password
     */
    public String getPassword() {
        return password;
    }

    public  String getEmailAddress() {
        return email;
    }
    /**
     * firstName getter
     *<pre>
     *<b>Constraints on the input</b>:
     *  None
     *</pre>
     *@return the firstName
     */
    public String getFirstName() {
        return firstName;
    }
    /**
     * lastName getter
     *<pre>
     *<b>Constraints on the input</b>:
     *  None
     *</pre>
     *@return the lastName
     */
    public String getLastName() {
        return lastName;
    }
    /**
     * gender getter
     *<pre>
     *<b>Constraints on the input</b>:
     *  None
     *</pre>
     *@return the gender
     */
    public String getGender() {
        return gender;
    }
    /**
     * personID getter
     *<pre>
     *<b>Constraints on the input</b>:
     *  None
     *</pre>
     *@return the personID
     */
    public String getPersonID() {
        return personID;
    }
    /**
     * username setter
     *<pre>
     *<b>Constraints on the input</b>:
     *  username &ne; null AND name.matches("[a-zA-Z]+")
     *
     *<b>Result</b>:
     *  this.username = username
     *</pre>
     *@param username the username to set
     */
    public void setUsername(String username) {
        this.username = username;
    }
    /**
     * password setter
     *<pre>
     *<b>Constraints on the input</b>:
     *  password &ne; null AND password.matches("[a-zA-Z]+")
     *
     *<b>Result</b>:
     *  password.name = password
     *</pre>
     *@param password the password to set
     */
    public void setPassword(String password) {
        this.password = password;
    }

    public void setEmailAddress(String email) {
        this.email = email;
    }

    /**
     * firstName setter
     *<pre>
     *<b>Constraints on the input</b>:
     *  firstName &ne; null AND firstName.matches("[a-zA-Z]+")
     *
     *<b>Result</b>:
     *  firstName.name = firstName
     *</pre>
     *@param firstName the firstName to set
     */

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }
    /**
     * lastName setter
     *<pre>
     *<b>Constraints on the input</b>:
     *  lastName &ne; null AND lastName.matches("[a-zA-Z]+")
     *
     *<b>Result</b>:
     *  this.lastName = lastName
     *</pre>
     *@param lastName the lastName to set
     *@throws AssertionError
     */
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
    /**
     * gender setter
     *<pre>
     *<b>Constraints on the input</b>:
     *  gender &ne; null AND gender.matches("[a-zA-Z]+")
     *
     *<b>Result</b>:
     *  gender.name = gender
     *</pre>
     *@param gender the gender to set
     *@throws AssertionError
     */
    public void setGender(String gender) {
        this.gender = gender;
    }
    /**
     * personID setter
     *<pre>
     *<b>Constraints on the input</b>:
     *  personID &ne; null AND personID.matches("[a-zA-Z]+")
     *
     *<b>Result</b>:
     *  this.personID = personID
     *</pre>
     *@param personID the personID to set
     *@throws AssertionError
     */
    public void setPersonID(String personID) {
        this.personID = personID;
    }
}
