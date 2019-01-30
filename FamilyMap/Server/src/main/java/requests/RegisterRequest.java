package requests;

import models.UserModel;

//register request, holds the information needed to perform the register option
public class RegisterRequest {

    String userName;
    String password;
    String email;
    String firstName;
    String lastName;
    String gender;
    String personID;

    public RegisterRequest(String userName, String password, String email, String firstName, String lastName, String gender, String personID) {
        this.userName = userName;
        this.password = password;
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.gender = gender;
        this.personID = personID;
    }
    public UserModel getUser() {
        personID = null;
        UserModel user = new UserModel(userName, password, email, firstName, lastName, gender, personID);
        return user;
    }
}
