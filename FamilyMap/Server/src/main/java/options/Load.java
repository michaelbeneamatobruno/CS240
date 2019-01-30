package options;

import database.Database;
import models.EventModel;
import models.PersonModel;
import models.UserModel;
import requests.LoadRequest;
import results.LoadResult;

//load result, loads all of the users, peoples, and events given in JSON format. returns a result that includes the number of people, events, and users loaded
public class Load {
    public LoadResult Load(LoadRequest loadRequest) {
        Clear clear = new Clear();
        clear.clear();

        Database database = Database.database;
        database.openConnection();

        UserModel[] users = loadRequest.getUsers();
        PersonModel[] people = loadRequest.getPeople();
        EventModel[] events = loadRequest.getEvents();

        int numUsers = 0;
        for (UserModel user : users) {
            numUsers++;
            database.createUser(user);
        }

        int numPeople = 0;
        for (PersonModel person : people) {
            numPeople++;
            database.createPerson(person);
        }

        int numEvents = 0;
        for (EventModel event : events) {
            numEvents++;
            database.createEvent(event);
        }

        database.closeConnection(true);

        LoadResult loadResult = new LoadResult(numUsers, numPeople, numEvents);
        return loadResult;

    }
}


