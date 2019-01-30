package database.DataGeneration;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Random;
import java.util.Scanner;

import database.Database;
import models.EventModel;
import models.PersonModel;
import models.UserModel;
import options.PersonID;

//Generates the People and Events for a specific user. Used when a User is first register, and when the Fill function is called
public class DataGenerator {

    private JsonArray fnames;
    private JsonArray mnames;
    private JsonArray snames;
    private JsonArray locations;
    private int numPeople;
    private int numEvents;
    private String username;
    private int numGenerations;

    //returns the number of generated events
    public int getNumEvents() {
        return numEvents;
    }
    //returns the number of generated people
    public int getNumPeople() {
        return numPeople;
    }
    //constructor, calls for the username of the person whose data will be generated and how many generations should be generated
    public DataGenerator(String username, int numGenerations) {
        this.username = username;
        this.numGenerations = numGenerations;
    }
    //generic function that scans a file and outputs the data found in the file into a JsonArray
    private JsonArray scanData(String path) {
        String data;
        try {
            File file = new File(path);
            Scanner scanner = new Scanner(file);
            StringBuilder out = new StringBuilder();
            String line;
            while (scanner.hasNextLine()) {
                line = scanner.nextLine();
                out.append(line);
            }
            data = out.toString();
            scanner.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return null;
        }

        Gson gson = new Gson();
        JsonObject jsonObject = gson.fromJson(data, JsonObject.class);
        return jsonObject.getAsJsonArray("data");
    }
    //Specifically imports the data from the given paths in the function
    public boolean importData() {

        fnames = scanData("C:\\Users\\micha\\AndroidStudioProjects\\FamilyMapServer1\\Server\\src\\main\\java\\database\\DataGeneration\\fnames.json");
        mnames = scanData("C:\\Users\\micha\\AndroidStudioProjects\\FamilyMapServer1\\Server\\src\\main\\java\\database\\DataGeneration\\mnames.json");
        snames = scanData("C:\\Users\\micha\\AndroidStudioProjects\\FamilyMapServer1\\Server\\src\\main\\java\\database\\DataGeneration\\snames.json");
        locations = scanData("C:\\Users\\micha\\AndroidStudioProjects\\FamilyMapServer1\\Server\\src\\main\\java\\database\\DataGeneration\\locations.json");
        if (fnames == null) {
            return false;
        }
        if (mnames == null) {
            return false;
        }
        if (snames == null) {
            return false;
        }
        if (locations == null) {
            return false;
        }
        return true;
    }
    //Generates a general event(given the event type) in a random location for a certain person. The
    private EventModel generateEvent(PersonModel person, String eventType, int year) {
        EventModel event = new EventModel();
        event.setDescendant(username);
        UUIDGenerator id = new UUIDGenerator();
        event.setEventID(id.getUUID());
        event.setPerson(person.getPersonID());

        JsonObject location;
        Random random = new Random();
        do {
            location = locations.get((int)(random.nextDouble() * (locations.size() - 1))).getAsJsonObject();
        } while(!location.has("longitude") ||
                !location.has("latitude") ||
                !location.has("country") ||
                !location.has("city"));

        event.setLongitude(location.get("longitude").getAsDouble());
        event.setLatitude(location.get("latitude").getAsDouble());
        event.setCountry(location.get("country").getAsString());
        event.setCity(location.get("city").getAsString());
        event.setEventType(eventType);
        event.setYear(year);

        Database database = Database.database;
        database.openConnection();
        database.createEvent(event);
        database.closeConnection(true);

        numEvents++;

        return event;
    }
    //generates a marriage between a father and a mother person.
    private void generateMarriage(PersonModel father, PersonModel mother) {

        Random random = new Random();


        int yearBorn = -1;
        Database database = Database.database;
        database.openConnection();
        EventModel[] events = database.getAllEvents(father.getPersonID());
        database.closeConnection(true);
        //iterates through the father events to get a birth year, bases marriage year off birth year
        for (EventModel singleEvent : events) {
            if (singleEvent.getEventType().equals("birth")) {
                yearBorn = singleEvent.getYear();
            }
        }
        int yearMarried =  yearBorn + (int)((random.nextDouble() * 20) + 18);


        EventModel marriage = generateEvent(father, "marriage", yearMarried);
        marriage.setPerson(mother.getPersonID());
        UUIDGenerator id = new UUIDGenerator();
        marriage.setEventID(id.getUUID());
        database.openConnection();
        database.createEvent(marriage);
        database.closeConnection(true);

        numEvents += 2;
    }
    //generates all the events(other than marriage) of a person. Starts with a predefined birth year.
    private void generateEvents(PersonModel person, int birthYearStart) {

        Random random = new Random();

        int birthYear = birthYearStart;
        int deathYear = birthYear + (int)(random.nextDouble() * 50) + 50;
        int baptism = (int)(random.nextDouble() * 8) + birthYear;
        int christening = (int)(random.nextDouble() * 3) + baptism;
        int censusRecord = random.nextInt(deathYear - birthYear) + birthYear;


        generateEvent(person, "birth", birthYear);
        generateEvent(person, "death", deathYear);

        if((int)(random.nextDouble() * 5) < ((christening - baptism))) {
            generateEvent(person, "christening", christening);
        }
        if((int)(random.nextDouble()) * 12 < (baptism - birthYear)) {
            generateEvent(person, "baptism", baptism);
        }
        if(random.nextDouble() >= random.nextDouble()) {
            generateEvent(person, "census record", censusRecord);
        }
    }
    //generates a person from their gender and birth year. Uses the generateEvents function to create a list of events and returns the person.
    private PersonModel generatePerson(String gender, int birthYear) {
        PersonModel person = new PersonModel();
        person.setDescendant(username);
        UUIDGenerator id = new UUIDGenerator();
        person.setPersonID(id.getUUID());

        Random random = new Random();

        if(gender.equals("m")) {
            person.setFirstName((mnames.get((int)(random.nextDouble() * mnames.size()))).getAsString());
            person.setGender("m");
        }
        else {
            person.setFirstName((fnames.get((int)(random.nextDouble() * fnames.size()))).getAsString());
            person.setGender("f");
        }

        person.setLastName((snames.get((int)(random.nextDouble() * snames.size()))).getAsString());
        generateEvents(person, birthYear);

        return person;

    }
    //recursively generates the lineage for a specific person.
    private PersonModel generateLineage(PersonModel person, int generations, int birthYearStart) {
        Database database = Database.database;
        if(generations <= 0) {
            database.openConnection();
            database.createPerson(person);
            database.closeConnection(true);
            numPeople++;
            return person;
        }

        generations--;

        Random random = new Random();

        PersonModel father = generatePerson("m", birthYearStart);
        PersonModel mother = generatePerson("f", birthYearStart);

        //goes through the father's tree and then the mother's tree, the birth year is based off of the child's birth
        if(father != null && mother != null) {
            generateMarriage(father, mother);
            person.setFatherID(father.getPersonID());
            father.setSpouseID(mother.getPersonID());
            person.setMotherID(mother.getPersonID());
            mother.setSpouseID(father.getPersonID());
            generateLineage(father, generations, (birthYearStart - (int)(random.nextDouble() * 32)) - 18);
            generateLineage(mother, generations, (birthYearStart - (int)(random.nextDouble() * 32)) - 18);
        }


        database.openConnection();
        database.createPerson(person);
        database.closeConnection(true);
        numPeople++;

        return person;
    }
    //an overarching function that imports the data from the example files and generates all the data for the user. returns the personID of the user.
    public String generateData() {
        if (!importData()) {
            return null;
        }
        Database database = Database.database;
        database.openConnection();
        UserModel user = database.getUser(username);
        database.closeConnection(true);
        if (user == null) {
            return null;
        }

        String personID = user.getPersonID();



        database.openConnection();
        database.resetAncestry(username, personID);
        database.closeConnection(true);


        PersonGenerator personGenerator = new PersonGenerator();
        PersonModel person = personGenerator.getPersonFromUser(user);

        Random random = new Random();
        int birthYearStart = (int)(random.nextDouble() * 500) + 1500;

        generateEvents(person, birthYearStart);
        birthYearStart = birthYearStart - (((int)(random.nextDouble() * 32)) + 18);
        person = generateLineage(person, numGenerations, birthYearStart);


        return person.getPersonID();
    }
}
