package handlers;
import com.google.gson.Gson;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;

import options.Login;
import requests.LoginRequest;
import results.LoginResult;

public class LoginHandler implements HttpHandler {
    @Override
    public void handle(HttpExchange exchange) throws IOException {
        boolean success = false;
        Gson gson = new Gson();

        try {
            if (exchange.getRequestMethod().toLowerCase().equals("post")) {
                InputStream reqBody = exchange.getRequestBody();
                String reqData = readString(reqBody);

                exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);
                LoginRequest loginRequest = gson.fromJson(reqData, LoginRequest.class);
                Login login = new Login();
                LoginResult loginResult = login.Login(loginRequest);

                if (loginResult.getAuthToken() != null) {
                    exchange.getResponseBody().write(gson.toJson(loginResult).getBytes());
                    exchange.getResponseBody().close();
                    success = true;
                }
                else if (loginResult != null) {
                    exchange.getResponseBody().write(gson.toJson(loginResult).getBytes());
                    exchange.getResponseBody().close();
                    success = true;
                }
            }
            if (!success) {
                exchange.sendResponseHeaders(HttpURLConnection.HTTP_BAD_REQUEST, 0);
                exchange.getResponseBody().write(gson.toJson("Login fail").getBytes());
                exchange.getResponseBody().close();
            }

        } catch (IOException e) {
            exchange.sendResponseHeaders(HttpURLConnection.HTTP_SERVER_ERROR, 0);
            exchange.getResponseBody().write(gson.toJson("Login fail").getBytes());
            exchange.getResponseBody().close();
            e.printStackTrace();
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
