package com.bignerdranch.android.familymapclient;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.Fragment;

import com.bignerdranch.android.familymapclient.Models.EventModel;
import com.bignerdranch.android.familymapclient.Models.PersonModel;
import com.google.android.gms.maps.SupportMapFragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;
import com.joanzapata.iconify.IconDrawable;
import com.joanzapata.iconify.fonts.FontAwesomeIcons;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class MapFragment extends Fragment implements OnMapReadyCallback {
    private GoogleMap mMap;
    private Map<Marker, EventModel> mEventMarkers;
    private Marker mMarker;

    private String mCurrentEvent;

    private ConstraintLayout mEventBar;
    private TextView mEventPersonName;
    private TextView mEventInfo;
    private ImageView mGenderImage;


    public MapFragment(){}

    public static MapFragment newInstance(String id) {
        MapFragment mFragment = new MapFragment();
        Bundle args = new Bundle();
        args.putString("event_id", id);
        mFragment.setArguments(args);
        return mFragment;
    }
    public void newIntent(String personID) {
        Intent intent = new Intent(getActivity(), PersonActivity.class);
        intent.putExtra("EXTRA_PERSON", personID);
        startActivity(intent);
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        //create filter?
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_map, container, false);
        SupportMapFragment smf = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);
        smf.getMapAsync(this);
        mEventMarkers = new HashMap<>();


        Bundle bundle = getArguments();
        if (bundle != null) {
            mCurrentEvent = getArguments().getString("EXTRA_EVENT");
        }
        else {
            mCurrentEvent = null;
            SettingsInfo.getInstance().setSpouseLines(false);
            SettingsInfo.getInstance().setStoryLines(false);
            SettingsInfo.getInstance().setTreeLines(false);
            SettingsInfo.getInstance().setSpouseLinesColor("BLUE");
            SettingsInfo.getInstance().setTreeLinesColor("BLUE");
            SettingsInfo.getInstance().setStoryLinesColor("BLUE");
            SettingsInfo.getInstance().setMapType("normal");
        }


        mEventBar = v.findViewById(R.id.event_bar);
        mEventPersonName = v.findViewById(R.id.event_person_name);
        mEventInfo = v.findViewById(R.id.event_info);
        mGenderImage = v.findViewById(R.id.map_fragment_gender_image);

        mEventBar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                newIntent(mEventMarkers.get(mMarker).getPerson());
            }
        });

        return v;
    }
    @Override
    public void onResume() {
        super.onResume();
        if (mMap != null) {
            mMap.clear();
            setType();
            makeMarkers();
            setLines();
        }
    }
    @Override
    public void onMapReady(GoogleMap map) {
        mMap = map;
        makeMarkers();

        mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {
                mMarker = marker;
                EventModel event = mEventMarkers.get(marker);
                updateEvent(event);
                return false;
            }
        });


    }
    public void setLines() {
        EventModel[] events = Storage.getInstance().getEventResult().getEvents();
        for (EventModel event : events) {
            LatLng latLngEvent = new LatLng(event.getLatitude(), event.getLongitude());
            PersonModel person = Storage.getInstance().getPerson(event.getPerson());
            if (SettingsInfo.getInstance().isSpouseLines()) {
                if (person.getSpouseID() != null) {
                    EventModel birth = (EventModel) Storage.getInstance().getEvents(person.getSpouseID()).get(0);
                    LatLng latLngBirth = new LatLng(birth.getLatitude(), birth.getLongitude());
                    String color = SettingsInfo.getInstance().getSpouseLinesColor();
                    mMap.addPolyline(new PolylineOptions()
                            .add(latLngBirth, latLngEvent)
                            .width(2)
                            .color(getSpinnerColor(color)));
                }
            }
            if (SettingsInfo.getInstance().isStoryLines()) {
                ArrayList<EventModel> personEvents = Storage.getInstance().getEvents(person.getPersonID());
                for (int i = 0; i < personEvents.size() - 1; i++) {
                    EventModel personEvent1 = (EventModel) personEvents.get(i);
                    EventModel personEvent2 = (EventModel) personEvents.get(i + 1);
                    LatLng latLng1 = new LatLng(personEvent1.getLatitude(), personEvent1.getLongitude());
                    LatLng latLng2 = new LatLng(personEvent2.getLatitude(), personEvent2.getLongitude());
                    String color = SettingsInfo.getInstance().getStoryLinesColor();
                    mMap.addPolyline(new PolylineOptions()
                            .add(latLng1, latLng2)
                            .width(2)
                            .color(getSpinnerColor(color)));
                }
            }
            if (SettingsInfo.getInstance().isTreeLines()) {

            }
        }

    }
    public void setType() {
        String type = SettingsInfo.getInstance().getMapType();
        if (type.equals("hybrid")) {
            mMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);
        }
        else if (type.equals("satellite")) {
            mMap.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
        }
        else if (type.equals("terrain")) {
            mMap.setMapType(GoogleMap.MAP_TYPE_TERRAIN);
        }
        else {
            mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        }
    }
    public void updateEvent(EventModel event) {
        PersonModel person = Storage.getInstance().getPerson(event.getPerson());
        String personName = person.getFirstName() + " " + person.getLastName();
        String eventInfo = event.getEventType() + ": " + event.getCity() + ",\n" + event.getCountry() + " (" + event.getYear() + ")";
        mEventPersonName.setText(personName);
        mEventInfo.setText(eventInfo);
        if (person.getGender().equals("m")) {
            Drawable genderIcon =  new IconDrawable(getActivity(), FontAwesomeIcons.fa_male).
                    colorRes(R.color.maleIcon).sizeDp(40);
            mGenderImage.setImageDrawable(genderIcon);
        }
        else if (person.getGender().equals("f")) {
            Drawable genderIcon =  new IconDrawable(getActivity(), FontAwesomeIcons.fa_female).
                    colorRes(R.color.femaleIcon).sizeDp(40);
            mGenderImage.setImageDrawable(genderIcon);
        }
        LatLng newCenter = new LatLng(event.getLatitude(), event.getLongitude());
        mMap.moveCamera(CameraUpdateFactory.newLatLng(newCenter));
    }
    public Float getColor(String eventType) {
        eventType = eventType.toLowerCase();
        if (eventType.equals("birth")) {
            return BitmapDescriptorFactory.HUE_RED;
        }
        else if (eventType.equals("baptism")) {
            return BitmapDescriptorFactory.HUE_BLUE;
        }
        else if (eventType.equals("christening")) {
            return BitmapDescriptorFactory.HUE_MAGENTA;
        }
        else if (eventType.equals("marriage")) {
            return BitmapDescriptorFactory.HUE_GREEN;
        }
        else if (eventType.equals("census")) {
            return BitmapDescriptorFactory.HUE_ORANGE;
        }
        else if (eventType.equals("death")) {
            return BitmapDescriptorFactory.HUE_YELLOW;
        }
        else {
            return BitmapDescriptorFactory.HUE_VIOLET;
        }
    }
    public int getSpinnerColor(String spinnerColor) {
        spinnerColor = spinnerColor.toUpperCase();
        if (spinnerColor.equals("BLUE")) {
            return Color.BLUE;
        }
        if (spinnerColor.equals("GREEN")) {
            return Color.GREEN;
        }
        if (spinnerColor.equals("YELLOW")) {
            return Color.YELLOW;
        } else {
            return Color.RED;
        }
    }
    public void makeMarkers() {
        EventModel[] events = Storage.getInstance().getEventResult().getEvents();
        for (EventModel event : events) {
            LatLng coordinates = new LatLng(event.getLatitude(), event.getLongitude());
            mMarker = mMap.addMarker(new MarkerOptions()
                    .position(coordinates)
                    .icon(BitmapDescriptorFactory.defaultMarker(getColor(event.getEventType()))));

            mEventMarkers.put(mMarker, event);
        }
        EventModel event = Storage.getInstance().getEvent(mCurrentEvent);
        if (event != null) {
            updateEvent(event);
        }
    }
}
