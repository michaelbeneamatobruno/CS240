package database.DAOs;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class AuthTokenDAO {
    //static queries make it easier to call multiple times
    private static final String CREATE_TOKEN_TABLE =
            "CREATE TABLE IF NOT EXISTS TOKENS" +
            "(" +
            "authToken VARCHAR(255) NOT NULL, " +
            "username VARCHAR(255) NOT NULL" +
            ");";
    //static queries make it easier to call multiple times
    private static final String ADD_TOKEN =
            "INSERT INTO TOKENS" +
            "(authToken, " +
            "username" +
            ") " +
            "values(?, ?)";
    //create the authorization token
    public boolean createAuthToken(Connection connection, String username, String authToken) {
        Statement statement;
        try {
            statement = connection.createStatement();
            statement.executeUpdate(CREATE_TOKEN_TABLE);

            PreparedStatement preparedStatement = connection.prepareStatement(ADD_TOKEN);
            preparedStatement.setString(1, authToken);
            preparedStatement.setString(2, username);

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
    //check the token and return the username if it's good
    public String checkToken(Connection connection, String authToken) {
        Statement statement;
        ResultSet resultSet;
        String DBusername;
        try {
            String getToken =
                    "SELECT * " +
                    "FROM TOKENS " +
                    "WHERE authToken = \'" +
                    authToken +
                    "\'";
            statement = connection.createStatement();
            statement.executeUpdate(CREATE_TOKEN_TABLE);
            resultSet = statement.executeQuery(getToken);

            if (!resultSet.next()) {
                return null;
            }

            DBusername = resultSet.getString("username");

            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
        return DBusername;
    }
}
