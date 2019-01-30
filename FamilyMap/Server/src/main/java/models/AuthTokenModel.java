package models;

//Authorization token model class
public class AuthTokenModel {

    private String authToken;
    private String username;

    //getters
    public String getUsername() {
        return username;
    }

    //setters
    public void setUsername(String username) {
        this.username = username;
    }
}
