package database.DAOs;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import models.UserModel;

public class UserDAO {
    //static queries to make it easier to call multiple times
    private static final String CREATE_USER_TABLE =
            "CREATE TABLE IF NOT EXISTS USERS" +
            "(" +
            "username VARCHAR(255) PRIMARY KEY, " +
            "password VARCHAR(255) NOT NULL, " +
            "email VARCHAR(255) NOT NULL, " +
            "firstName VARCHAR(255) NOT NULL, " +
            "lastName VARCHAR(255) NOT NULL, " +
            "gender VARCHAR(2) NOT NULL, " +
            "personID VARCHAR(255)" +
            ");";
    //static queries to make it easier to call multiple times
    private static final String ADD_TO_USER_TABLE =
            "INSERT INTO USERS" +
            "(" +
            "username, " +
            "password, " +
            "email, " +
            "firstName, " +
            "lastName, " +
            "gender, " +
            "personID" +
            ") " +
            "values(?, ?, ?, ?, ?, ?, ?);";
    //create the user
    public boolean createUser(Connection connection, UserModel user) {
        Statement statement;
        try {
            statement = connection.createStatement();
            statement.executeUpdate(CREATE_USER_TABLE);
            PreparedStatement preparedStatement = connection.prepareStatement(ADD_TO_USER_TABLE);

            preparedStatement.setString(1, user.getUsername());
            preparedStatement.setString(2, user.getPassword());
            preparedStatement.setString(3, user.getEmailAddress());
            preparedStatement.setString(4, user.getFirstName());
            preparedStatement.setString(5, user.getLastName());
            preparedStatement.setString(6, user.getGender());
            preparedStatement.setString(7, user.getPersonID());

            preparedStatement.addBatch();
            preparedStatement.executeBatch();
            preparedStatement.close();
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }
    //get the user from the result set spit out by the query
    private UserModel getUserFromResult(ResultSet resultSet) {
        UserModel user = new UserModel();
        try {
            user.setPersonID(resultSet.getString("personID"));
            user.setUsername(resultSet.getString("username"));
            user.setPassword(resultSet.getString("password"));
            user.setEmailAddress(resultSet.getString("email"));
            user.setFirstName(resultSet.getString("firstName"));
            user.setLastName(resultSet.getString("lastName"));
            user.setGender(resultSet.getString("gender"));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return user;
    }
    //Read Function, returns based on username
    public UserModel readUser(Connection connection, String username) {
        Statement statement;
        ResultSet resultSet;
        UserModel user = new UserModel();
        try {
            statement = connection.createStatement();
            String FIND_USER =
                    "SELECT * " +
                    "FROM Users " +
                    "WHERE username = \'" +
                    username +
                    "\'";

            statement.executeUpdate(CREATE_USER_TABLE);
            resultSet = statement.executeQuery(FIND_USER);

            if (!resultSet.next()) {
                return null;
            }

            user = getUserFromResult(resultSet);

            resultSet.close();
            statement.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return user;
    }
    //Read function, makes sure the username and password are right
    public UserModel readUser(Connection connection, String username, String password) {
        Statement statement;
        ResultSet resultSet;
        UserModel user = new UserModel();
        try {
            statement = connection.createStatement();
            statement.executeUpdate(CREATE_USER_TABLE);
            String FIND_USER =
                    "SELECT * " +
                    "FROM USERS " +
                    "WHERE username = \'" +
                    username +
                    "\'" +
                    "AND password = \'" +
                    password +
                    "\'";

            resultSet = statement.executeQuery(FIND_USER);

            if (!resultSet.next()) {
                return null;
            }

            user = getUserFromResult(resultSet);

            statement.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return user;
    }
    //Updates a user's personID
    public void updatePersonID(Connection connection, String personID, String username) {
        Statement statement;
        try {
            statement = connection.createStatement();
            statement.executeUpdate(CREATE_USER_TABLE);
            String UPDATE_ID =
                    "UPDATE USERS " +
                    "SET personID = \'" +
                    personID +
                    "\'" +
                    "WHERE username = \'" +
                    username +
                    "\'";

            statement.executeUpdate(UPDATE_ID);
            statement.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
