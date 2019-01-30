package com.bignerdranch.android.familymapclient;

import com.bignerdranch.android.familymapclient.Models.EventResult;
import com.bignerdranch.android.familymapclient.Models.LoginRequest;
import com.bignerdranch.android.familymapclient.Models.LoginResult;
import com.bignerdranch.android.familymapclient.Models.PersonResult;
import com.bignerdranch.android.familymapclient.Models.RegisterRequest;
import com.bignerdranch.android.familymapclient.Models.RegisterResult;
import com.google.gson.Gson;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

class ServerProxy {
    private static ServerProxy INSTANCE = null;

    static ServerProxy getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new ServerProxy();
        }
        return INSTANCE;
    }

    private ServerProxy() {

    }

    public EventResult getEventResult(String serverHost, String serverPort, String authToken) {
        EventResult eventResult = null;
        try {
            URL url = new URL("http://" + serverHost + ":" + serverPort + "/event/");
            HttpURLConnection http = (HttpURLConnection)url.openConnection();
            http.setRequestMethod("GET");
            http.setDoOutput(false);
            http.addRequestProperty("Authorization", authToken);
            http.addRequestProperty("Accept", "application/json");
            http.connect();
            if (http.getResponseCode() == HttpURLConnection.HTTP_OK) {
                InputStream respBody = http.getInputStream();
                String respData = readString(respBody);
                Gson gson = new Gson();
                eventResult = gson.fromJson(respData, EventResult.class);
            }
            else {
                System.out.println("ERROR: " + http.getResponseMessage());
            }
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        return eventResult;
    }
    public PersonResult getPersonResult(String serverHost, String serverPort, String authToken) {
        PersonResult personResult = null;
        try {
            URL url = new URL("http://" + serverHost + ":" + serverPort + "/person/");
            HttpURLConnection http = (HttpURLConnection)url.openConnection();
            http.setRequestMethod("GET");
            http.setDoOutput(false);
            http.addRequestProperty("Authorization", authToken);
            http.addRequestProperty("Accept", "application/json");
            http.connect();
            if (http.getResponseCode() == HttpURLConnection.HTTP_OK) {
                InputStream respBody = http.getInputStream();
                String respData = readString(respBody);
                Gson gson = new Gson();
                personResult = gson.fromJson(respData, PersonResult.class);
            }
            else {
                System.out.println("ERROR: " + http.getResponseMessage());
            }
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        return personResult;
    }
    public LoginResult getLoginResult(String serverHost, String serverPort, LoginRequest request) {
        LoginResult loginResult = null;
        try {
            URL url = new URL("http://" + serverHost + ":" + serverPort + "/user/login");
            HttpURLConnection http = (HttpURLConnection)url.openConnection();
            http.setRequestMethod("POST");
            http.setDoOutput(true);
            http.connect();
            Gson gson = new Gson();
            String reqData = gson.toJson(request);
            OutputStream reqBody = http.getOutputStream();
            writeString(reqData, reqBody);
            reqBody.close();
            if (http.getResponseCode() == HttpURLConnection.HTTP_OK) {
                InputStream respBody = http.getInputStream();
                String respData = readString(respBody);
                loginResult = gson.fromJson(respData, LoginResult.class);
            }
            else {
                System.out.println("ERROR: " + http.getResponseMessage());
            }
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        return loginResult;
    }
    public RegisterResult getRegisterResult(String serverHost, String serverPort, RegisterRequest request) {
        RegisterResult registerResult = null;
        try {
            URL url = new URL("http://" + serverHost + ":" + serverPort + "/user/register");
            HttpURLConnection http = (HttpURLConnection)url.openConnection();
            http.setRequestMethod("POST");
            http.setDoOutput(true);
            http.connect();
            Gson gson = new Gson();
            String reqData = gson.toJson(request);
            OutputStream reqBody = http.getOutputStream();
            writeString(reqData, reqBody);
            reqBody.close();
            if (http.getResponseCode() == HttpURLConnection.HTTP_OK) {
                InputStream respBody = http.getInputStream();
                String respData = readString(respBody);
                registerResult = gson.fromJson(respData, RegisterResult.class);
            }
            else {
                System.out.println("ERROR: " + http.getResponseMessage());
            }
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        return registerResult;
    }
    private static String readString(InputStream is) throws IOException {
        StringBuilder sb = new StringBuilder();
        InputStreamReader sr = new InputStreamReader(is);
        char[] buf = new char[1024];
        int len;
        while ((len = sr.read(buf)) > 0) {
            sb.append(buf, 0, len);
        }
        return sb.toString();
    }
    private static void writeString(String str, OutputStream os) throws IOException {
        OutputStreamWriter sw = new OutputStreamWriter(os);
        sw.write(str);
        sw.flush();
    }
}
