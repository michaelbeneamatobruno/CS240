package database.DAOs;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import models.EventModel;

public class EventDAO {
    //static queries make it easier to call multiple times
    public static final String CREATE_EVENT_TABLE =
            "CREATE TABLE IF NOT EXISTS EVENTS" +
            "(" +
            "eventID TEXT NOT NULL, " +
            "descendant VARCHAR(255) NOT NULL, " +
            "person VARCHAR(255) NOT NULL, " +
            "country TEXT NOT NULL, " +
            "city TEXT NOT NULL, " +
            "eventType TEXT NOT NULL, " +
            "latitude REAL NOT NULL, " +
            "longitude REAL NOT NULL, " +
            "year INTEGER NOT NULL" +
            ");";
    //static queries make it easier to call multiple times
    public static final String ADD_EVENT =
            "INSERT INTO EVENTS" +
            "(" +
            "eventID, " +
            "descendant, " +
            "person, " +
            "country, " +
            "city, " +
            "eventType, " +
            "latitude, " +
            "longitude, " +
            "year" +
            ") " +
            "values(?, ?, ?, ?, ?, ?, ?, ?, ?);";

    //creates an event
    public boolean createEvent(Connection connection, EventModel event) {
        Statement statement;
        try {
            statement = connection.createStatement();
            statement.executeUpdate(CREATE_EVENT_TABLE);
            PreparedStatement preparedStatement = connection.prepareStatement(ADD_EVENT);
            //set everything
            preparedStatement.setString(1, event.getEventID());
            preparedStatement.setString(2, event.getDescendant());
            preparedStatement.setString(3, event.getPerson());
            preparedStatement.setString(4, event.getCountry());
            preparedStatement.setString(5, event.getCity());
            preparedStatement.setString(6, event.getEventType());
            preparedStatement.setDouble(7, event.getLatitude());
            preparedStatement.setDouble(8, event.getLongitude());
            preparedStatement.setInt(9, event.getYear());
            //add to batch
            preparedStatement.addBatch();
            //execute statement
            preparedStatement.executeBatch();
            //close the statement
            statement.close();
            preparedStatement.close();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }
    //returns the event from a result set
    private EventModel getEventFromResult(ResultSet resultSet, EventModel event) {
        try {
            event.setEventID(resultSet.getString("eventID"));
            event.setDescendant(resultSet.getString("descendant"));
            event.setPerson(resultSet.getString("person"));
            event.setCountry(resultSet.getString("country"));
            event.setCity(resultSet.getString("city"));
            event.setEventType(resultSet.getString("eventType"));
            event.setLatitude(resultSet.getDouble("latitude"));
            event.setLongitude(resultSet.getDouble("longitude"));
            event.setYear(resultSet.getInt("year"));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return event;
    }
    //returns the event based off of the eventID
    public EventModel readEvent(Connection connection, String eventID) {
        Statement statement;
        ResultSet resultSet;
        try {
            statement = connection.createStatement();
            statement.executeUpdate(CREATE_EVENT_TABLE);
            String FIND_EVENT =
                    "SELECT * " +
                    "FROM EVENTS " +
                    "WHERE eventID = \'" +
                    eventID +
                    "\'";
            resultSet = statement.executeQuery(FIND_EVENT);

            if (!resultSet.next()) {
                return null;
            }

            EventModel event = new EventModel();
            event = getEventFromResult(resultSet, event);

            statement.close();
            return event;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
    //returns all the events of the person with the personID that's passed in.
    public EventModel[] readAllEvents(Connection connection, String personID) {
        Statement statement;
        ResultSet resultSet;

        try {
            statement = connection.createStatement();
            String findEvent =
                    "SELECT * " +
                    "FROM EVENTS " +
                    "WHERE person = \'" +
                    personID +
                    "\'";

            statement.executeUpdate(CREATE_EVENT_TABLE);
            resultSet = statement.executeQuery(findEvent);
            if (!resultSet.next()) {
                return null;
            }
            ArrayList array = new ArrayList<EventModel>();
            do {
                EventModel event = new EventModel();
                event = getEventFromResult(resultSet, event);
                array.add(event);
            } while (resultSet.next());

            EventModel[] events = new EventModel[array.size()];
            for (int i = 0; i < array.size(); i++) {
                EventModel event = (EventModel)array.get(i);
                events[i] = event;
            }

            statement.close();
            return events;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
    //resets all of the events for all the people with the descendant who is the current user
    public void resetEvents(Connection connection, String descendant) {
        Statement statement;
        try {
            String removeEvents =
                    "DELETE FROM Events " +
                    "WHERE descendant = \'" +
                    descendant +
                    "\'";
            statement = connection.createStatement();
            statement.executeUpdate(CREATE_EVENT_TABLE);
            statement.executeUpdate(removeEvents);

            statement.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
