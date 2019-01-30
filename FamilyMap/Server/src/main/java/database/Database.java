package database;
import java.io.File;
import java.sql.*;
import java.util.ArrayList;


import database.DAOs.AuthTokenDAO;
import database.DAOs.EventDAO;
import database.DAOs.PersonDAO;
import database.DAOs.UserDAO;
import models.EventModel;
import models.PersonModel;
import models.UserModel;
//The database, this is where all the information is stored.
public class Database {

    //creates the database and the connection to the database
    public static Database database = new Database();
    private Connection myConnection;

    private Database() {
        loadDriver();
    }
    public void loadDriver() {
        try {
            //loads the driver as sqlite
            final String driver = "org.sqlite.JDBC";
            Class.forName(driver);
        }
        catch(ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
    //opens a new database connection
    public void openConnection() {
        File file = new File("database");
        if (!file.exists()) {
            file.mkdirs();
        }

        String name = "database" + File.separator + "database.sqlite";
        String connectionURL = "jdbc:sqlite:" + name;
        myConnection = null;

        try {

            // Open a database connection
            myConnection = DriverManager.getConnection(connectionURL);

            // Start a transaction
            myConnection.setAutoCommit(false);
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }
    //closes the connection, saving if commit is true and rolling back if it's false
    public void closeConnection(boolean commit) {
        try {
            if (commit) {
                myConnection.commit();
            }
            else {
                myConnection.rollback();
            }

            myConnection.close();
            myConnection = null;
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }
    //essentially clears the entire database
    public boolean clearTables() {
        String dropEvents = "DROP TABLE IF EXISTS events; ";
        String dropPeople = "DROP TABLE IF EXISTS users; ";
        String dropUsers = "DROP TABLE IF EXISTS people; ";
        String dropTokens = "DROP TABLE IF EXISTS tokens";


        try {
            PreparedStatement eventPrep = myConnection.prepareStatement(dropEvents);
            PreparedStatement personPrep = myConnection.prepareStatement(dropPeople);
            PreparedStatement userPrep = myConnection.prepareStatement(dropUsers);
            PreparedStatement tokenPrep = myConnection.prepareStatement(dropTokens);
            eventPrep.executeUpdate();
            personPrep.executeUpdate();
            userPrep.executeUpdate();
            tokenPrep.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
        return true;

    }
    //clears a certain user's ancestry(the data needs to be erased before a fill can occur)
    public void resetAncestry(String username, String personID) {
        PersonDAO resetPeople = new PersonDAO();
        resetPeople.deleteAncestry(myConnection, username, personID);
        EventDAO resetEvents = new EventDAO();
        resetEvents.resetEvents(myConnection, username);
        return;
    }
    //adds an authorization token to the database
    public void createAuthToken(String username, String authToken) {
        AuthTokenDAO addAuth = new AuthTokenDAO();
        addAuth.createAuthToken(myConnection, username, authToken);
        return;
    }
    //checks if an authorization token is valid
    public String checkAuthToken(String authToken) {
        AuthTokenDAO checkToken = new AuthTokenDAO();
        return checkToken.checkToken(myConnection, authToken);
    }
    //adds an event to the database
    public void createEvent(EventModel event) {
        EventDAO addEvent = new EventDAO();
        addEvent.createEvent(myConnection, event);
        return;
    }
    //returns an event from the database based on the eventID
    public EventModel getEvent(String eventID) {
        EventDAO getByID = new EventDAO();
        return getByID.readEvent(myConnection, eventID);
    }
    //returns all the events from the database belonging to a specific person
    public EventModel[] getAllEvents(String personID) {
        EventDAO getAllEvents = new EventDAO();
        return getAllEvents.readAllEvents(myConnection, personID);
    }
    //adds a person to the database
    public void createPerson(PersonModel person) {
        PersonDAO addPerson = new PersonDAO();
        addPerson.createPerson(myConnection, person);
    }
    //returns a person based on their ID
    public PersonModel getPerson(String personID) {
        PersonDAO getByID = new PersonDAO();
        return getByID.readPerson(myConnection, personID);
    }
    //returns all people related to a user
    public PersonModel[] getPeople(String username) {
        PersonDAO getAllPeople = new PersonDAO();
        return getAllPeople.readPeople(myConnection, username);
    }
    //deletes a specific person using their ID
    public void deletePerson(String personID) {
        PersonDAO deletePerson = new PersonDAO();
        deletePerson.deletePerson(myConnection, personID);
    }
    //gets a user from their username
    public UserModel getUser(String username) {
        UserDAO getUser = new UserDAO();
        return getUser.readUser(myConnection, username);
    }
    //gets a user based off of their username and password(important for Login)
    public UserModel getLoginUser(String username, String password) {
        UserDAO loginUser = new UserDAO();
        return loginUser.readUser(myConnection, username, password);
    }
    //adds a user to the database
    public void createUser(UserModel user) {
        UserDAO addUser = new UserDAO();
        addUser.createUser(myConnection, user); //boolean?
    }
}
