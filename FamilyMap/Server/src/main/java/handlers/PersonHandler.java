package handlers;

import com.google.gson.Gson;
import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URI;

import database.Database;
import models.PersonModel;
import options.Person;
import options.PersonID;
import results.PersonIDResult;
import results.PersonResult;

public class PersonHandler implements HttpHandler {
    @Override
    public void handle(HttpExchange exchange) throws IOException {
        boolean success = false;
        Gson gson = new Gson();

        URI uri = exchange.getRequestURI();
        String uriString = uri.toString();
        String[] uris = uriString.split("/");

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
                            Person personOption = new Person(username);
                            PersonResult result = personOption.getPeople();
                            exchange.getResponseBody().write(gson.toJson(result).getBytes());
                            exchange.getResponseBody().close();
                            success = true;
                        }
                    }
                }
                if (!success) {
                    exchange.sendResponseHeaders(HttpURLConnection.HTTP_BAD_REQUEST, 0);
                    exchange.getResponseBody().write(gson.toJson("Person fail").getBytes());
                    exchange.getResponseBody().close();
                }
            } catch (IOException e) {
                    exchange.sendResponseHeaders(HttpURLConnection.HTTP_SERVER_ERROR, 0);
                exchange.getResponseBody().write(gson.toJson("Person fail").getBytes());
                    exchange.getResponseBody().close();
                    e.printStackTrace();
            }
        }

        else if (uris.length == 3) {

            String personID = uris[2];

            try {
                Database database = Database.database;
                database.openConnection();
                PersonModel person = database.getPerson(personID);
                database.closeConnection(true);
                if (person == null) {
                    exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);
                    exchange.getResponseBody().write(gson.toJson("fail: person not found").getBytes());
                    exchange.getResponseBody().close();
                    success = true;
                }
                else if (exchange.getRequestMethod().toLowerCase().equals("get")) {
                    String username = person.getDescendant();
                    Headers reqHeaders = exchange.getRequestHeaders();
                    if (reqHeaders.containsKey("Authorization")) {
                        String authToken = reqHeaders.getFirst("Authorization");
                        database.openConnection();
                        String checkUsername = database.checkAuthToken(authToken);
                        database.closeConnection(true);
                        if (checkUsername.equals(username)) {
                            exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);
                            PersonID id = new PersonID(personID);
                            PersonIDResult result = id.getPerson();
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
                    exchange.getResponseBody().write(gson.toJson("PersonID fail").getBytes());
                    exchange.getResponseBody().close();
                }
            } catch (IOException e) {
                exchange.sendResponseHeaders(HttpURLConnection.HTTP_SERVER_ERROR, 0);
                exchange.getResponseBody().write(gson.toJson("PersonID fail").getBytes());
                exchange.getResponseBody().close();
                e.printStackTrace();
            }
        }
    }
}
