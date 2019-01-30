package handlers;
import com.google.gson.Gson;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URI;

import options.Fill;
import results.FillResult;

public class FillHandler implements HttpHandler {
    @Override
    public void handle(HttpExchange exchange) throws IOException {
        boolean success = false;
        Gson gson = new Gson();

        try {
            URI uri = exchange.getRequestURI();
            String uriString = uri.toString();
            String[] uris = uriString.split("/");

            String username = null;
            int numGenerations = -1;

            if (uris.length == 3) {
                username = uris[2];
            }
            else if (uris.length == 4) {
                username = uris[2];
                numGenerations = Integer.parseInt(uris[3]);
            }
            if (exchange.getRequestMethod().toLowerCase().equals("post")) {
                if (username != null) {

                    Fill fill = new Fill(username, numGenerations);
                    FillResult result = fill.fill();

                    exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);

                    exchange.getResponseBody().write(gson.toJson(result).getBytes());
                    exchange.getResponseBody().close();
                    success = true;
                }
            }
            if (!success) {
                exchange.sendResponseHeaders(HttpURLConnection.HTTP_BAD_REQUEST, 0);
                exchange.getResponseBody().write(gson.toJson("Fill fail").getBytes());
                exchange.getResponseBody().close();
            }
        }
        catch (IOException e) {
            exchange.sendResponseHeaders(HttpURLConnection.HTTP_SERVER_ERROR, 0);
            exchange.getResponseBody().write(gson.toJson("Fill fail").getBytes());
            exchange.getResponseBody().close();
            e.printStackTrace();
        }
    }
}
