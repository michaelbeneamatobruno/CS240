package com.bignerdranch.android.familymapclient;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.bignerdranch.android.familymapclient.Models.EventModel;
import com.bignerdranch.android.familymapclient.Models.PersonModel;
import com.joanzapata.iconify.IconDrawable;
import com.joanzapata.iconify.fonts.FontAwesomeIcons;

import java.util.ArrayList;

public class SearchActivity extends AppCompatActivity {
    private EditText mSearchView;
    private RecyclerView mRecyclerView;
    private ArrayList mResults;


    private class SearchItem {
        private String data;
        private PersonModel mPerson;
        private EventModel mEvent;
        private String mType;

        SearchItem(EventModel event) {
            mEvent = event;
            mType = "event";
            PersonModel eventPerson = Storage.getInstance().getPerson(event.getPerson());
            data = event.getEventType() + ": " + event.getCity() + ",\n" + event.getCountry() + " (" + event.getYear() + ")\n" + eventPerson.getFirstName() + " " + eventPerson.getLastName();
        }
        SearchItem(PersonModel person) {
            mPerson = person;
            mType = "person";
            data = person.getFirstName() + " " + person.getLastName();
        }

        public String getData() {
            return data;
        }
        public void setData(String data) {
            this.data = data;
        }
        public PersonModel getPerson() {
            return mPerson;
        }
        public void setPerson(PersonModel person) {
            mPerson = person;
        }
        public EventModel getEvent() {
            return mEvent;
        }
        public void setEvent(EventModel event) {
            mEvent = event;
        }
        public String getType() {
            return mType;
        }
        public void setType(String type) {
            mType = type;
        }
    }
    private class SearchHolder extends RecyclerView.ViewHolder {
        private Context mContext;
        private TextView mTextView;
        private ImageView mImageView;
        private ConstraintLayout item;
        private String mPersonID;
        private String mEventID;
        private String mType;


        public SearchHolder(View v, final Context context) {
            super(v);
            mContext = context;
            mTextView = v.findViewById(R.id.search_item_text);
            mImageView = v.findViewById(R.id.search_item_image);
            item = v.findViewById(R.id.search_item);
            item.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mType.equals("person")) {
                        Intent intent = new Intent(mContext, PersonActivity.class);
                        intent.putExtra("EXTRA_PERSON", mPersonID);
                        mContext.startActivity(intent);
                    }
                    else {
                        Intent intent = new Intent(mContext, EventActivity.class);
                        intent.putExtra("EXTRA_EVENT", mEventID);
                        mContext.startActivity(intent);
                    }
                }
            });
        }
        public void bind(SearchItem item) {
            mType = item.getType();
            mTextView.setText(item.getData());
            if (mType.equals("person")) {
                if (item.getPerson().getGender().equals("m")) {
                    Drawable genderIcon =  new IconDrawable(SearchActivity.this, FontAwesomeIcons.fa_male).
                            colorRes(R.color.maleIcon).sizeDp(40);
                    mImageView.setImageDrawable(genderIcon);
                }
                else {
                    Drawable genderIcon =  new IconDrawable(SearchActivity.this, FontAwesomeIcons.fa_female).
                            colorRes(R.color.femaleIcon).sizeDp(40);
                    mImageView.setImageDrawable(genderIcon);
                }
                mPersonID = item.getPerson().getPersonID();
            }
            else {
                Drawable marker = new IconDrawable(SearchActivity.this, FontAwesomeIcons.fa_arrow_up).
                        colorRes(R.color.personActivityMarker).sizeDp(40);
                mImageView.setImageDrawable(marker);
                mEventID = item.getEvent().getEventID();
            }
        }

    }
    private class SearchAdapter extends RecyclerView.Adapter<SearchHolder> {
        private ArrayList<SearchItem> mResults;
        Context mContext;

        public SearchAdapter(ArrayList results, Context context) {
            mResults = results;
            mContext = context;
        }
        @Override
        public void onAttachedToRecyclerView(RecyclerView recycler) {
            super.onAttachedToRecyclerView(recycler);
        }
        @Override
        public SearchHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_search, parent, false);
            return new SearchHolder(v, mContext);
        }
        @Override
        public void onBindViewHolder(SearchHolder holder, int position) {
            SearchItem item = (SearchItem) mResults.get(position);
            holder.bind(item);
        }
        @Override
        public int getItemCount() {
            return mResults.size();
        }
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        mResults = new ArrayList();
        getSupportActionBar().setTitle("Family Map: Search");

        mRecyclerView = findViewById(R.id.search_recycler_view);
        mSearchView = findViewById(R.id.search_view);

        SearchAdapter adapterOG = new SearchAdapter(mResults, getApplicationContext());
        mRecyclerView.setAdapter(adapterOG);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        mSearchView.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!s.toString().isEmpty()) {
                    mResults = search(s.toString());
                    SearchAdapter adapter = new SearchAdapter(mResults, getApplicationContext());
                    mRecyclerView.setAdapter(adapter);
                    mRecyclerView.setLayoutManager(new LinearLayoutManager(getBaseContext()));
                }
            }
            @Override
            public void afterTextChanged(Editable s) {

            }
        });

//        mSearchView.setOnKeyListener(new View.OnKeyListener() {
//            public boolean onKey(View v, int keyCode, KeyEvent event) {
//                if ((event.getAction() == KeyEvent.ACTION_DOWN) && (keyCode == KeyEvent.KEYCODE_ENTER)) {
//                    mSearchView.getText().toString(); //what?
//                }
//                return false;
//            }
//        });
    }
    public ArrayList search(String query) {
        query = query.toLowerCase();
        ArrayList<SearchItem> results = new ArrayList<>();
        for (PersonModel person : Storage.getInstance().getPersonResult().getPeople()) {
            String first = person.getFirstName().toLowerCase();
            String last = person.getLastName().toLowerCase();
            if (first.contains(query)) {
                results.add(new SearchItem(person));
            }
            else if (last.contains(query)) {
                results.add(new SearchItem(person));
            }
        }
        for (EventModel event : Storage.getInstance().getEventResult().getEvents()) {
            String country = event.getCountry().toLowerCase();
            String city = event.getCity().toLowerCase();
            String year = Integer.toString(event.getYear());
            if (country.contains(query)) {
                results.add(new SearchItem(event));
            }
            else if (city.contains(query)) {
                results.add(new SearchItem(event));
            }
            else if (year.contains(query)) {
                results.add(new SearchItem(event));
            }
        }
        return results;
    }
}
