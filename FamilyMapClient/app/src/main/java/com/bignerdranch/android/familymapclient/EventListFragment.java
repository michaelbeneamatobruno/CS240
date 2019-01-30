package com.bignerdranch.android.familymapclient;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bignerdranch.android.familymapclient.Models.EventModel;
import com.bignerdranch.android.familymapclient.Models.PersonModel;
import com.joanzapata.iconify.IconDrawable;
import com.joanzapata.iconify.fonts.FontAwesomeIcons;

import java.util.ArrayList;

//this is the fragment that essentially holds the Events recycler view in the person activity
public class EventListFragment extends Fragment {
    //the adapter to transfer data to the recycler view
    private EventAdapter mAdapter;
    //this is the event recycler view
    private RecyclerView mEventRecyclerView;
    //this is the event title, it says LIFE EVENTS
    private TextView eventTitleText;
    //this is just a map marker image
    private ImageView eventTitleImage;
    //this is the header for the events, it's what retracts the events
    private ConstraintLayout eventTitleHeader;

    //classic create view, prety much your oncreate of the fragment class
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        //this is where I get the personID of who we're making the Person Activity for
        Bundle bundle = this.getArguments();
        String personID = bundle.getString("personID");
        View v = inflater.inflate(R.layout.fragment_event_list, container, false);

        //finding all of the views from their XML IDs
        mEventRecyclerView = v.findViewById(R.id.event_recycler_view);
        eventTitleText = v.findViewById(R.id.event_fragment_title_text);
        eventTitleImage = v.findViewById(R.id.event_fragment_title_image);
        eventTitleHeader = v.findViewById(R.id.event_list_title);

        //this is how you get the recycler view to go
        mEventRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        //setting up all the texts and images
        eventTitleText.setText(R.string.event_fragment_title);
        final Drawable markerUp = new IconDrawable(getActivity(), FontAwesomeIcons.fa_arrow_up).
                colorRes(R.color.personActivityMarker).sizeDp(40);
        final Drawable markerDown = new IconDrawable(getActivity(), FontAwesomeIcons.fa_arrow_down).
                colorRes(R.color.personActivityMarker).sizeDp(40);
        eventTitleImage.setImageDrawable(markerUp);

        //when the header gets clicked it retracts the events
        eventTitleHeader.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mEventRecyclerView.getAlpha() == 1) {
                    mEventRecyclerView.setAlpha(0);
                    eventTitleImage.setImageDrawable(markerDown);
                }
                else {
                    mEventRecyclerView.setAlpha(1);
                    eventTitleImage.setImageDrawable(markerUp);
                }
            }
        });

        //this ties everything together
        updateUI(personID);

        return v;
    }
    //event holder, it holds the events and gives them to the adapter
    private class EventHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        //the text from the event
        private TextView mEventTextView;
        //the text from the person
        private TextView mPersonTextView;
        //the marker image
        private ImageView markerImage;
        //the eventID, we need it just in case the event gets clicked
        private String mEventID;
        //the person, we mostly just get it for the person text view
        private PersonModel mPerson;

        //bind function, takes the private data members and sticks the information from the event into them
        public void bind(EventModel event) {
            //get the eventID from the event
            mEventID = event.getEventID();
            //get the our person from our singleton storage
            mPerson = Storage.getInstance().getPerson(event.getPerson());
            //get our event and person info from the event and person models
            String eventInfo = event.getEventType() + ": " + event.getCity() + ", " + event.getCountry() + " (" + event.getYear() + ")";
            String personInfo = mPerson.getFirstName() + " " + mPerson.getLastName();
            //set the text and images
            mEventTextView.setText(eventInfo);
            mPersonTextView.setText(personInfo);
            Drawable marker = new IconDrawable(getActivity(), FontAwesomeIcons.fa_map_marker).
                    colorRes(R.color.personActivityMarker).sizeDp(40);
            markerImage.setImageDrawable(marker);
        }
        //event holder constructor
        public EventHolder(LayoutInflater inflater, ViewGroup parent) {
            super(inflater.inflate(R.layout.list_item_event, parent, false));
            itemView.setOnClickListener(this);

            mEventTextView = itemView.findViewById(R.id.event_list_event);
            mPersonTextView = itemView.findViewById(R.id.event_person_name);
            markerImage = itemView.findViewById(R.id.marker_image);

        }
        //if the event gets clicked then we need to open an event activity
        @Override
        public void onClick(View view) {
            //stick some data in an event and give it to a new event activity
            Intent intent = new Intent(getActivity(), EventActivity.class);
            intent.putExtra("EXTRA_EVENT", mEventID);
            startActivity(intent);
        }
    }
    //event adapter, takes the holder and sticks it into a recycler view
    private class EventAdapter extends RecyclerView.Adapter<EventHolder> {
        //list of events
        private ArrayList mEvents;

        //constructor passes in all of the events
        public EventAdapter(ArrayList events) {
            mEvents = events;
        }

        //makes a new event holder
        @Override
        public EventHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
            return new EventHolder(layoutInflater, parent);
        }
        //goes through the events and sticks them into an event holder
        @Override
        public void onBindViewHolder(EventHolder holder, int position) {
            EventModel event = (EventModel) mEvents.get(position);
            holder.bind(event);
        }
        //gets the number of items, you need to know how many you're going through
        @Override
        public int getItemCount() {
            return mEvents.size();
        }
    }
    //ties it all together
    private void updateUI(String ID) {
        String personID = ID;
        ArrayList events = Storage.getInstance().getEvents(personID);

        mAdapter = new EventAdapter(events);
        mEventRecyclerView.setAdapter(mAdapter);
    }
}
