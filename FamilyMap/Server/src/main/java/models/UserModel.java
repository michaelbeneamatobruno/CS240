package models;

//user model class
public class UserModel {

    private String userName;
    private String password;
    private String email;
    private String firstName;
    private String lastName;
    private String gender;
    private String personID;

    public UserModel(String userName, String password, String email, String firstName, String lastName, String gender, String personID) {
        this.userName = userName;
        this.password = password;
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.gender = gender;
        this.personID = personID;
    }
    public UserModel() {
        userName = null;
        password = null;
        email = null;
        firstName = null;
        lastName = null;
        gender = null;
        personID = null;
    }

    //getters
    public String getUsername() {
        return userName;
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

    //setters
    public void setUsername(String username) {
        this.userName = username;
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
