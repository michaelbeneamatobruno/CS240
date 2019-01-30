package database.DataGeneration;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Random;

import database.Database;
import models.PersonModel;
import models.UserModel;
//a class that generates a person, either from an already existing user, or randomly
public class PersonGenerator {

    private String mNames = "libs/mnames.json";
    private String fNames = "libs/fnames.json";
    private String lNames = "libs/snames.json";

    private String descendant;
    private String firstName;
    private String lastName;
    private String gender;
    private String personID;
    private String fatherID = null;
    private String motherID = null;
    private String spouseID = null;

    //generates a person using all the information from a passed in user
    public PersonModel getPersonFromUser(UserModel user) {
        descendant = user.getUsername();
        firstName = user.getFirstName();
        lastName = user.getLastName();
        gender = user.getGender();
        personID = user.getPersonID();
        PersonModel person = new PersonModel(descendant, firstName, lastName, gender,
                personID, fatherID, motherID, spouseID);
        return person;
    }
    //generates a person randomly, using only the gender and descendant to populate information
    public PersonModel generatePerson(String gender, String descendant) {
        this.descendant = descendant;
        this.gender = gender;
        firstName = firstNameGenerator();
        if (gender == "m") {
            Database database = Database.database;
            database.openConnection();
            this.lastName = database.getPerson(descendant).getLastName();
        }
        else {
            this.lastName = lastNameGenerator();
        }
        personIDGenerator();
        PersonModel person = new PersonModel(descendant, firstName, lastName, gender,
                personID, fatherID, motherID, spouseID);
        return person;
    }
    //generates a personID randomly from a UUID generator
    public String personIDGenerator(){
        UUIDGenerator id = new UUIDGenerator();
        personID = id.getUUID();
        return personID;
    }
    //generates a first name randomly from the example first names file.
    public String firstNameGenerator() {
        String[] firstNames = new String[]{};
        int arraySize = 0;
        JsonParser parser = new JsonParser();
        try {
            JsonObject jsonObject = null;
            if(gender.equals("m")) {
                jsonObject = (JsonObject) parser.parse(new FileReader(mNames));
            }
            else if(gender.equals("f")) {
                jsonObject = (JsonObject) parser.parse(new FileReader(fNames));
            }
            JsonArray jsonArray = jsonObject.getAsJsonArray("data");

            arraySize = jsonArray.size();
            for(int i = 0; i < arraySize; i++) {
                firstNames[i] = (jsonArray.get(i).getAsString());
            }
        }
        catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        Random random = new Random();
        int randInt = random.nextInt(arraySize);
        return firstNames[randInt];
    }
    //generates a last name randomly from the example last names file
    public String lastNameGenerator() {
        String[] last = new String[]{};
        int arraySize = 0;
        JsonParser parser = new JsonParser();
        try {
            JsonObject jsonObject;
            jsonObject = (JsonObject) parser.parse(new FileReader(lNames));
            JsonArray jsonArray = jsonObject.getAsJsonArray("data");
            arraySize = jsonArray.size();
            for(int i = 0; i < arraySize; i++) {
                last[i] = (jsonArray.get(i).getAsString());
            }
        }
        catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        Random random = new Random();
        int randInt = random.nextInt(arraySize);
        return last[randInt];
    }
}
