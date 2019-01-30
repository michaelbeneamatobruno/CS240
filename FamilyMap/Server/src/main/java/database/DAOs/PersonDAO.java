package database.DAOs;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import database.DataGeneration.UUIDGenerator;
import models.PersonModel;

public class PersonDAO {

    //static queries to make it easier to call multiple times
    public static final String CREATE_PERSON_TABLE =
            "CREATE TABLE IF NOT EXISTS PEOPLE" +
            "(" +
            "descendant VARCHAR(255) NOT NULL, " +
            "firstName VARCHAR(255) NOT NULL, " +
            "lastName VARCHAR(255) NOT NULL, " +
            "gender VARCHAR(2) NOT NULL, " +
            "personID VARCHAR(255) PRIMARY KEY, " +
            "fatherID VARCHAR(255), " +
            "motherID VARCHAR(255), " +
            "spouseID VARCHAR(255)" +
            ");";
    //static queries to make it easier to call multiple times
    public static final String ADD_TO_PERSON_TABLE =
            "INSERT INTO PEOPLE" +
            "(" +
            "descendant, " +
            "firstName, " +
            "lastName, " +
            "gender, " +
            "personID, " +
            "fatherID, " +
            "motherID, " +
            "spouseID" +
            ") " +
            "values(?, ?, ?, ?, ?, ?, ?, ?);";

    //create the person
    public boolean createPerson(Connection connection, PersonModel person) {

        if (person.getPersonID() == null) {
            UUIDGenerator id = new UUIDGenerator();
            String personID = id.getUUID();
            person.setPersonID(personID);
        }

        try {
            Statement statement;
            statement = connection.createStatement();
            statement.executeUpdate(CREATE_PERSON_TABLE);

            PreparedStatement preparedStatement = connection.prepareStatement(ADD_TO_PERSON_TABLE);
            preparedStatement.setString(1, person.getDescendant());
            preparedStatement.setString(2, person.getFirstName());
            preparedStatement.setString(3, person.getLastName());
            preparedStatement.setString(4, person.getGender());
            preparedStatement.setString(5, person.getPersonID());
            preparedStatement.setString(6, person.getFatherID());
            preparedStatement.setString(7, person.getMotherID());
            preparedStatement.setString(8, person.getSpouseID());

            preparedStatement.addBatch();

            preparedStatement.executeBatch();

            statement.close();
            preparedStatement.close();

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }

        return true;
    }
    //helper function, returns the person from a result set
    private PersonModel getPersonFromResult(ResultSet resultSet, PersonModel person) {
        try {
            person.setDescendant(resultSet.getString("descendant"));
            person.setFirstName(resultSet.getString("firstName"));
            person.setLastName(resultSet.getString("lastName"));
            person.setGender(resultSet.getString("gender"));
            person.setPersonID(resultSet.getString("personID"));
            person.setFatherID(resultSet.getString("fatherID"));
            person.setMotherID(resultSet.getString("motherID"));
            person.setSpouseID(resultSet.getString("spouseID"));

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return person;
    }
    //read function, based on the personId
    public PersonModel readPerson(Connection connection, String personID) {
        Statement statement = null;
        ResultSet resultSet = null;
        PersonModel person = new PersonModel();

        try {
            statement = connection.createStatement();
            statement.executeUpdate(CREATE_PERSON_TABLE);
            String FIND_PERSON =
                    "SELECT * " +
                    "FROM PEOPLE " +
                    "WHERE personID = \'" +
                    personID +
                    "\'";

            resultSet = statement.executeQuery(FIND_PERSON);

            if (!resultSet.next()) {
                return null;
            }

            person = getPersonFromResult(resultSet, person);



        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                resultSet.close();
                statement.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return person;
    }
    //read function, based on the descendant's username
    public PersonModel[] readPeople(Connection connection, String username) {
        Statement statement;
        ResultSet resultSet;
        try {
            statement = connection.createStatement();
            String FIND_EVENT =
                "SELECT * " +
                "FROM PEOPLE " +
                "WHERE descendant = \'" +
                username +
                "\'";


            statement.executeUpdate(CREATE_PERSON_TABLE);
            resultSet = statement.executeQuery(FIND_EVENT);

            ArrayList array = new ArrayList<PersonModel>();
            while (resultSet.next()) {
                PersonModel person = new PersonModel();
                person = getPersonFromResult(resultSet, person);
                array.add(person);
            }

            PersonModel[] people = new PersonModel[array.size()];
            for (int i = 0; i < array.size(); i++) {
                PersonModel person = (PersonModel) array.get(i);
                people[i] = person;
            }

            statement.close();
            return people;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
    //deletes all the current ancestry of the person, except the person themself
    public void deleteAncestry(Connection connection, String username, String personID) {
        String deleteAncestry =
                "DELETE FROM PEOPLE " +
                "WHERE descendant = \'" +
                username +
                "\';";

        Statement statement;
        try {
            statement = connection.createStatement();
            statement.executeUpdate(CREATE_PERSON_TABLE);
            statement.executeUpdate(deleteAncestry);
            statement.close();
        }
        catch (Exception e) {
            e.printStackTrace();
            return;
        }
    }
    //deletes the person based on personID
    public boolean deletePerson(Connection connection, String personID) {
        String deletePerson =
                "DELETE FROM PEOPLE " +
                "WHERE personID = \'" +
                personID +
                "\';";

        Statement statement;
        try {
            statement = connection.createStatement();
            statement.executeUpdate(CREATE_PERSON_TABLE);
            statement.executeUpdate(deletePerson);
            statement.close();
            return true;
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
}
