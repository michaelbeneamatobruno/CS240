package com.bignerdranch.android.familymapclient;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;

//Event Activity class,
public class EventActivity extends AppCompatActivity {
    //this is my map
    private MapFragment mMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main); //change to activity_event

        //I need to recieve data from whatever is calling
        String eventID = getIntent().getStringExtra("EXTRA_EVENT");
        //Here I give data
        Bundle bundle = new Bundle();
        bundle.putString("EXTRA_EVENT", eventID);

        //set up a new map fragment and start it
        FragmentManager fm = getSupportFragmentManager();
        mMap = (MapFragment) fm.findFragmentById(R.id.fragment_container);
        mMap = new MapFragment();
        mMap.setArguments(bundle);
        fm.beginTransaction().add(R.id.fragment_container, mMap).commit();
    }
}
