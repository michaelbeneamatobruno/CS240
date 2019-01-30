package options;

import database.Database;
import models.PersonModel;
import results.PersonIDResult;

//personID class, like the eventID class, it uses a personID and returns a person in a personIDResult
public class PersonID {

    String personID;

    public PersonID(String personID) {
        this.personID = personID;
    }
    public PersonIDResult getPerson() {
        Database database = Database.database;
        database.openConnection();
        PersonModel person = database.getPerson(personID);
        database.closeConnection(true);
        return new PersonIDResult(person);
    }
}
