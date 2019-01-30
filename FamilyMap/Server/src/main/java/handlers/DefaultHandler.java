package handlers;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URI;
import java.util.Scanner;
//default handler, checks the URI and sends the user to the right web page based off of the path.
public class DefaultHandler implements HttpHandler {


    @Override
    public void handle(HttpExchange httpExchange) throws IOException {

        String filePathString = "C:\\Users\\micha\\AndroidStudioProjects\\FamilyMap\\Server\\web";


        URI uri = httpExchange.getRequestURI();
        String uriString = uri.toString();
        String[] uris = uriString.split("/");


        String uriPath;
        if (uris.length == 0 || uris.length == 1) {
            httpExchange.getResponseHeaders().set("Content-Type", "text/html");
            uriPath = "index.html";
        }
        else if (uris[1].equals("css")) {
            httpExchange.getResponseHeaders().set("Content-Type", "text/css");
            uris[1] = "css\\main.css";
            uriPath = uris[1];
        }
        else if (uris[1].equals("img")) {
            httpExchange.getResponseHeaders().set("Content-Type", "img/png");
            uris[1] = "favicon.ico";
            uriPath = uris[1];
        }
        else {
            httpExchange.getResponseHeaders().set("Content-Type", "text/html");
            uris[1] = "HTML\\404.html";
            uriPath = uris[1];
        }



        httpExchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);

        OutputStreamWriter out = new OutputStreamWriter(httpExchange.getResponseBody());
        Scanner scanner = new Scanner(new FileReader(filePathString + "\\" + uriPath));
        StringBuilder sb = new StringBuilder();
        while (scanner.hasNextLine()) {
            sb.append(scanner.nextLine() + "\n");
        }
        scanner.close();
        out.write(sb.toString());
        out.close();
    }
}
