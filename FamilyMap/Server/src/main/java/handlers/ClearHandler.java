package handlers;

import com.google.gson.Gson;
import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URI;

import database.Database;
import options.Clear;
import results.ClearResult;
//clear handler, checks the uri and if it's clear it creates a clear class to clear the database
public class ClearHandler implements HttpHandler {

    @Override
    public void handle(HttpExchange exchange) throws IOException {

        URI uri = exchange.getRequestURI();
        String uriString = uri.toString();

        if (uriString.equals("/clear/")) {

            Gson gson = new Gson();
            boolean success = false;
            try {
                Database database = Database.database;
                if (exchange.getRequestMethod().toLowerCase().equals("post")) {
                    Headers reqHeaders = exchange.getRequestHeaders();
                    if (reqHeaders.containsKey("Authorization")) {
                        InputStream reqBody = exchange.getRequestBody();
                        String reqData = readString(reqBody);
                        System.out.println(reqData);


                        exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);
                        Clear clear = new Clear();
                        ClearResult clearResult = clear.clear();
                        exchange.getResponseBody().write(gson.toJson(clearResult).getBytes());
                        exchange.getResponseBody().close();
                        success = true;
                    }
                }
                if (!success) {
                    exchange.sendResponseHeaders(HttpURLConnection.HTTP_BAD_REQUEST, 0);
                    exchange.getResponseBody().write(gson.toJson("clear fail").getBytes());
                    exchange.getResponseBody().close();
                }
            }

            catch (IOException e) {
                exchange.sendResponseHeaders(HttpURLConnection.HTTP_SERVER_ERROR, 0);
                exchange.getResponseBody().write(gson.toJson("clear fail").getBytes());
                exchange.getResponseBody().close();
                e.printStackTrace();
            }
        }


    }
    private String readString(InputStream is) throws IOException {
        StringBuilder sb = new StringBuilder();
        InputStreamReader sr = new InputStreamReader(is);
        char[] buf = new char[1024];
        int len;
        while ((len = sr.read(buf)) > 0) {
            sb.append(buf, 0, len);
        }
        return sb.toString();
    }
}