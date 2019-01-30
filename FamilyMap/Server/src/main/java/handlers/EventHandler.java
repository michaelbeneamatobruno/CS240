package handlers;

import com.google.gson.Gson;
import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URI;

import database.Database;
import models.EventModel;
import options.Event;
import options.EventID;
import results.EventIDResult;
import results.EventResult;
//event handler, this one is tricky because it handles when the path is /event, or /event/personID
public class EventHandler implements HttpHandler {
    @Override
    public void handle(HttpExchange exchange) throws IOException {

        URI uri = exchange.getRequestURI();
        String uriString = uri.toString();
        String[] uris = uriString.split("/");

        Gson gson = new Gson();
        boolean success = false;
        ///event handler essentially
        if (uris.length == 2) {
            try {
                Database database = Database.database;
                if (exchange.getRequestMethod().toLowerCase().equals("get")) {
                    Headers reqHeaders = exchange.getRequestHeaders();
                    if (reqHeaders.containsKey("Authorization")) {
                        String authToken = reqHeaders.getFirst("Authorization");
                        database.openConnection();
                        String username = database.checkAuthToken(authToken);
                        database.closeConnection(true);
                        if (username != null) {
                            exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);
                            Event eventOption = new Event(username);
                            EventResult result = eventOption.getEvents();
                            exchange.getResponseBody().write(gson.toJson(result).getBytes());
                            exchange.getResponseBody().close();
                            success = true;
                        }
                    }
                }
                if (!success) {
                    exchange.sendResponseHeaders(HttpURLConnection.HTTP_BAD_REQUEST, 0);
                    exchange.getResponseBody().write(gson.toJson("event fail").getBytes());
                    exchange.getResponseBody().close();
                }
            } catch (IOException e) {
                exchange.sendResponseHeaders(HttpURLConnection.HTTP_SERVER_ERROR, 0);
                exchange.getResponseBody().write(gson.toJson("event fail").getBytes());
                exchange.getResponseBody().close();
                e.printStackTrace();
            }
        }
        ///event/personID handler
        else if (uris.length == 3) {

            String eventID = uris[2];


            try {
                Database database = Database.database;
                database.openConnection();
                EventModel event = database.getEvent(eventID);
                database.closeConnection(true);
                if (event == null) {
                    exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);
                    exchange.getResponseBody().write(gson.toJson("fail: event not found").getBytes());
                    exchange.getResponseBody().close();
                    success = true;
                }
                else if (exchange.getRequestMethod().toLowerCase().equals("get")) {
                    String username = event.getDescendant();
                    Headers reqHeaders = exchange.getRequestHeaders();
                    if (reqHeaders.containsKey("Authorization")) {
                        String authToken = reqHeaders.getFirst("Authorization");
                        database.openConnection();
                        String checkUsername = database.checkAuthToken(authToken);
                        database.closeConnection(true);
                        if (checkUsername.equals(username)) {
                            exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);
                            EventID id = new EventID(eventID);
                            EventIDResult result = id.getEvent();
                            exchange.getResponseBody().write(gson.toJson(result).getBytes());
                            exchange.getResponseBody().close();
                            success = true;
                        }
                        else {
                            exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);
                            exchange.getResponseBody().write(gson.toJson("personID fail: This person does not exist for this user").getBytes());
                            exchange.getResponseBody().close();
                            success = true;
                        }
                    }
                }
                if (!success) {
                    exchange.sendResponseHeaders(HttpURLConnection.HTTP_BAD_REQUEST, 0);
                    exchange.getResponseBody().write(gson.toJson("eventID fail").getBytes());
                    exchange.getResponseBody().close();
                }
            } catch (IOException e) {
                exchange.sendResponseHeaders(HttpURLConnection.HTTP_SERVER_ERROR, 0);
                exchange.getResponseBody().write(gson.toJson("eventID fail").getBytes());
                exchange.getResponseBody().close();
                e.printStackTrace();
            }
        }
    }
}
