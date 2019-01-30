package options;

import database.DataGeneration.DataGenerator;
import database.Database;
import models.EventModel;
import models.PersonModel;
import results.FillResult;

//fills the information for a user for a certain number of generations. Returns a fill result with the number of people and events added
public class Fill {

    String username;
    int numGenerations;
    int numEvents;
    int numPeople;

    public int getNumEvents() {
        return numEvents;
    }

    public Fill(String username, int numGenerations) {
        this.username = username;
        if (numGenerations == -1) {
            this.numGenerations = 4;
        }
        else {
            this.numGenerations = numGenerations;
        }
    }
    public FillResult fill() {

        Database database = Database.database;

//        database.openConnection();
//        String originalID = database.getUser(username).getPersonID();
//        database.closeConnection(true);

        DataGenerator dataGenerator = new DataGenerator(username, numGenerations);
        String personID = dataGenerator.generateData();
        if (personID == null) {
            FillResult fillResult = new FillResult("Fill failure");
            return fillResult;
        }

        numEvents = dataGenerator.getNumEvents();
        numPeople = dataGenerator.getNumPeople();

//        database.openConnection();
//        database.updatePersonID(originalID, username);
//        database.closeConnection(true);

        FillResult fillResult = new FillResult("Fill success: Successfully added " + numPeople + " persons and " + numEvents + " events to the database.");
        return fillResult;

    }
}
