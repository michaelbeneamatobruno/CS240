package com.bignerdranch.android.familymap;
/**
 *A login request.
 *<pre>
 *<b>Domain</b>:
 *   username         : String     -- A non-empty string
 *   password        : String     -- A non-empty string
 *
 *   <i>Invariant</i>: username &ne; null AND password &ne; null
 *</pre>
 */
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
