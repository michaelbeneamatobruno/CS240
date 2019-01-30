package com.bignerdranch.android.familymapclient;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bignerdranch.android.familymapclient.Models.PersonModel;


public class PersonActivity extends AppCompatActivity {

    TextView mFirstNameText;
    TextView mLastNameText;
    TextView mGenderText;
    LinearLayout personRecycleView;
    LinearLayout eventRecycleView;

    private EventListFragment lifeEvents;
    private PersonListFragment family;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_person);
        getSupportActionBar().setTitle("Family Map: Person Details");


        mFirstNameText = findViewById(R.id.first_name);
        mLastNameText = findViewById(R.id.last_name);
        mGenderText = findViewById(R.id.gender);
        personRecycleView = findViewById(R.id.person_recycler_linear_layout);
        eventRecycleView = findViewById(R.id.event_recycler_linear_layout);


        FragmentManager fm = getSupportFragmentManager();
        family = (PersonListFragment) fm.findFragmentById(R.id.person_recycler_view);
        lifeEvents = (EventListFragment) fm.findFragmentById(R.id.event_recycler_view);

        Intent intent = getIntent();
        String personID = intent.getStringExtra("EXTRA_PERSON");
        PersonModel person = Storage.getInstance().getPerson(personID);
        Bundle bundle = new Bundle();
        bundle.putString("personID", personID);


        if (lifeEvents == null) {
            lifeEvents = new EventListFragment();
            lifeEvents.setArguments(bundle);
            fm.beginTransaction().add(R.id.person_recycler_linear_layout, lifeEvents).commit();
        }
        if (family == null) {
            family = new PersonListFragment();
            family.setArguments(bundle);
            fm.beginTransaction().add(R.id.event_recycler_linear_layout, family).commit();
        }

        mFirstNameText.setText(person.getFirstName());
        mLastNameText.setText(person.getLastName());
        if (person.getGender().equals("m")) {
            mGenderText.setText("Male");
        }
        else if (person.getGender().equals("f")) {
            mGenderText.setText("Female");
        }
        else {
            mGenderText.setText("Ambigious gender?");
        }

//        personRecycleView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if (mPersonID != null) {
//                    newPersonIntent(mPersonID);
//                }
//            }
//        });
//        eventRecycleView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if (mEventID != null) {
//                    newEventIntent(mEventID);
//                }
//            }
//        });
    }

}
