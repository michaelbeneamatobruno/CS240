package results;

//register result, contains a result, authorization token, username, and personID
public class RegisterResult {
    String result;
    String authToken;
    String username;
    String personID;

    public RegisterResult(String authToken, String username, String personID) {
        this.authToken = authToken;
        this.username = username;
        this.personID = personID;
    }
    public RegisterResult(String result) {
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

    //setters
    public void setUsername(String username) {
        this.username = username;
    }
    public void setPersonID(String personID) {
        this.personID = personID;
    }
    public void setResult(String result) {

    }
}
