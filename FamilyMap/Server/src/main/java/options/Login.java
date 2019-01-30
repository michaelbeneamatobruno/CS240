package options;

import database.DataGeneration.UUIDGenerator;
import database.Database;
import models.UserModel;
import requests.LoginRequest;
import results.LoginResult;

//login class, registers a new user with a login request and returns a string result(pass or fail)
public class Login {
    public LoginResult Login(LoginRequest loginRequest) {

        Database database = Database.database;

        database.openConnection();
        UserModel user = database.getLoginUser(loginRequest.getUsername(), loginRequest.getPassword());
        if (user == null) {
            database.closeConnection(false);
            LoginResult result = new LoginResult("Login failed: user does not exist");
            return result;
        }
        database.closeConnection(true);

        database.openConnection();
        UUIDGenerator id = new UUIDGenerator();
        String authToken = id.getUUID(); //generate authToken
        database.createAuthToken(user.getUsername(), authToken);
        database.closeConnection(true);

        LoginResult loginResult = new LoginResult(authToken, user.getUsername(), user.getPersonID());
        return loginResult;
    }
}
