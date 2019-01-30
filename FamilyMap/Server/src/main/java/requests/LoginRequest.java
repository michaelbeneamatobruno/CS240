package requests;

//load request, essentially just holds the information needed to login
public class LoginRequest {

    String userName;
    String password;

    public LoginRequest(String userName, String password) {
        this.userName = userName;
        this.password = password;
    }

    public String getUsername(){
        return userName;
    }
    public String getPassword() {
        return password;
    }
}
