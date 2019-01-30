package com.bignerdranch.android.familymapclient;

import android.test.ActivityInstrumentationTestCase2;
import android.widget.Button;
import android.widget.EditText;

import com.bignerdranch.android.familymapclient.Models.EventModel;
import com.bignerdranch.android.familymapclient.Models.PersonModel;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.*;

public class LoginTest extends ActivityInstrumentationTestCase2<MainActivity> {

    private MainActivity mActivity;

    private String hostNumber = "10.0.2.2";
    private String portNumber = "8085";

    protected void setUp() throws Exception {
        super.setUp();
        mActivity = getActivity();
    }

    public LoginTest() {
        super(MainActivity.class);
    }

    public void setHostAndPort(final EditText field, final String text) {
        assertNotNull(field);
        getInstrumentation().runOnMainSync(new Runnable() {
            @Override
            public void run() {
                field.setText(text);
            }
        });
    }

    public void testLogin() {
        final EditText host = mActivity.findViewById(R.id.server_host);
        final EditText port = mActivity.findViewById(R.id.server_port);
        final EditText username = mActivity.findViewById(R.id.user_name);
        final EditText password = mActivity.findViewById(R.id.password);

        insertData(host, hostNumber);
        insertData(port, portNumber);
        insertData(username, "sheila");
        insertData(password, "parker");

        Button login = mActivity.findViewById(R.id.login_button);
        pressButton(login);

        int attempts = 0;
        while (attempts < 15) {
            try {
                Thread.sleep(1000, 0);
            }
            catch (InterruptedException e)
            {
                e.printStackTrace();
            }
            attempts++;
        }

        checkData();
        checkEvents();

    }

    public void checkData() {
        PersonModel[] people = Storage.getInstance().getPersonResult().getPeople();
        for (PersonModel person : people) {
            String personID = person.getPersonID();
            String fatherID = person.getFatherID();
            String motherID = person.getMotherID();
            String spouseID = person.getSpouseID();
            PersonModel father = Storage.getInstance().getPerson(fatherID);
            PersonModel mother = Storage.getInstance().getPerson(motherID);
            PersonModel spouse = Storage.getInstance().getPerson(spouseID);
            PersonModel self = Storage.getInstance().getPerson(personID);

            if (fatherID != null) {
                assertTrue(father.getPersonID().equals(fatherID));
            }
            if (motherID != null) {
                assertTrue(mother.getPersonID().equals(motherID));
            }
            if (spouseID != null) {
                assertTrue(spouse.getSpouseID().equals(personID));
            }
            assertTrue(self.getPersonID().equals(personID));
        }
    }
    public void checkEvents() {
        for (PersonModel person : Storage.getInstance().getPersonResult().getPeople()) {
            ArrayList<EventModel> events = Storage.getInstance().getEvents(person.getPersonID());
            for (EventModel event : events) {
                if (event.getEventType().equals("birth")) {
                    assertTrue(event.getEventID().equals(events.get(0).getEventID()));
                }
            }
        }
    }

    public void insertData(final EditText field, final String text) {
        assertNotNull(field);
        getInstrumentation().runOnMainSync(new Runnable() {
            @Override
            public void run() {
                field.setText(text);
            }
        });
    }
    public void pressButton(final Button button) {
        assertNotNull(button);
        getInstrumentation().runOnMainSync(new Runnable() {
            @Override
            public void run() {
                button.performClick();
            }
        });
    }


}