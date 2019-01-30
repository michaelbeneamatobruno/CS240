package com.bignerdranch.android.familymapclient.Models;

//login request, used pretty much to store data
public class LoginRequest {

    String userName;
    String password;

    public LoginRequest(){}
    public LoginRequest(String userName, String password) {
        this.userName = userName;
        this.password = password;
    }


    public String getUsername() {
        return userName;
    }
    public void setUsername(String username) {
        this.userName = username;
    }
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }
}
