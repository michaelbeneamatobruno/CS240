package options;

import database.DataGeneration.UUIDGenerator;
import database.Database;
import database.DataGeneration.PersonGenerator;
import models.PersonModel;
import models.UserModel;
import requests.RegisterRequest;
import results.RegisterResult;

//Registers a new user, uses a register request to add a new user. returns a string(pass or fail)
public class Register {
    public RegisterResult Register(RegisterRequest registerRequest) {
        if (registerRequest == null) {
            RegisterResult result = new RegisterResult("Register failed: invalid request");
            return result;
        }
        String authToken;
        String username;
        UserModel user = registerRequest.getUser();

        Database database = Database.database;
        if (user == null) {
            return null;
        }

        username = user.getUsername();
        database.openConnection();
        UserModel check = database.getUser(username);
        database.closeConnection(true);
        if (check != null) {
            RegisterResult result = new RegisterResult("Register fail: This username has already been registered");
            return result;
        }

        database.openConnection();
        UUIDGenerator id = new UUIDGenerator();
        authToken = id.getUUID();
        database.createAuthToken(user.getUsername(), authToken);
        database.closeConnection(true);

        database.openConnection();
        PersonGenerator generator = new PersonGenerator();
        PersonModel person = generator.getPersonFromUser(user);
        database.createPerson(person);
        database.closeConnection(true);
        user.setPersonID(person.getPersonID());


        database.openConnection();
        database.createUser(user);
        database.closeConnection(true);

        Fill fill = new Fill(username, 4);
        fill.fill();

        database.openConnection();
        UserModel newUser = database.getUser(username);
        database.closeConnection(true);

        RegisterResult registerResult = new RegisterResult(authToken, username, newUser.getPersonID());
        return registerResult;
    }
}
