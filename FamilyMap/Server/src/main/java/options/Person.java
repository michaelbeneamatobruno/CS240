package options;

import database.Database;
import models.PersonModel;
import results.PersonResult;

//person class, a lot like the event class, it returns all the people in the database
public class Person {

    String username;

    public Person(String username) {
        this.username = username;
    }
    public PersonResult getPeople() {
        Database database = Database.database;
        database.openConnection();
        PersonModel[] people = database.getPeople(username);
        database.closeConnection(true);
        PersonResult result = new PersonResult(people);
        return result;
    }
}
