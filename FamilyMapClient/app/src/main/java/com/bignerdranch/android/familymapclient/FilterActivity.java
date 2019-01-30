package com.bignerdranch.android.familymapclient;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;

import com.bignerdranch.android.familymapclient.Models.EventModel;

import java.util.ArrayList;


public class FilterActivity extends AppCompatActivity {
    private RecyclerView mRecyclerView;
    private Switch mFatherSwitch;
    private Switch mMotherSwitch;
    private Switch mMaleSwitch;
    private Switch mFemaleSwitch;


    private class FilterItem {
        private String mData;

        public FilterItem(String data) {
            mData = data;
        }
        public String getData() {
            return mData;
        }
        public void setData(String data) {
            mData = data;
        }
    }
    private class FilterHolder extends RecyclerView.ViewHolder {
        private TextView mTextView;
        private Switch mSwitch;

        public FilterHolder(View v) {
            super(v);
            mTextView = v.findViewById(R.id.filter_item_text);
            mSwitch = v.findViewById(R.id.filter_item_switch);

            mSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    SettingsInfo.getInstance().startEvents(String.valueOf(mTextView.getText()), isChecked);
                }

            });
        }
        public void bind(FilterItem item) {
            mTextView.setText(item.getData());
        }
    }
    private class FilterAdapter extends RecyclerView.Adapter<FilterHolder> {
        private ArrayList mFilters;
        private Context mContext;

        public FilterAdapter(ArrayList<FilterItem> filter, Context context) {
            mFilters = filter;
            context = context;
        }
        @Override
        public FilterHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_filter, parent, false);
            FilterHolder holder = new FilterHolder(v);
            return holder;
        }
        @Override
        public void onBindViewHolder(FilterHolder holder, int position) {
            FilterItem item = (FilterItem) mFilters.get(position);
            holder.bind(item);
        }
        @Override
        public void onAttachedToRecyclerView(RecyclerView recyclerView) {
            super.onAttachedToRecyclerView(recyclerView);
        }
        @Override
        public int getItemCount() {
            return mFilters.size();
        }

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filter);

        mRecyclerView = findViewById(R.id.filter_recycler_view);
        mFatherSwitch = findViewById(R.id.father_switch);
        mMotherSwitch = findViewById(R.id.mother_switch);
        mMaleSwitch = findViewById(R.id.male_switch);
        mFemaleSwitch = findViewById(R.id.female_switch);

        mFatherSwitch.setChecked(SettingsInfo.getInstance().isFatherSide());
        mMotherSwitch.setChecked(SettingsInfo.getInstance().isMotherSide());
        mMaleSwitch.setChecked(SettingsInfo.getInstance().isMaleSide());
        mFemaleSwitch.setChecked(SettingsInfo.getInstance().isFemaleSide());

        mFatherSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                SettingsInfo.getInstance().setFatherSide(isChecked);
            }
        });
        mMotherSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                SettingsInfo.getInstance().setMotherSide(isChecked);
            }
        });
        mMaleSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                SettingsInfo.getInstance().setMaleSide(isChecked);
            }
        });
        mFemaleSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                SettingsInfo.getInstance().setFemaleSide(isChecked);
            }
        });

        ArrayList<FilterItem> filters = new ArrayList<>();
        for (EventModel event : Storage.getInstance().getEventResult().getEvents()) {
            FilterItem item = new FilterItem(event.getEventID());
            filters.add(item);
        }
        FilterAdapter adapter = new FilterAdapter(filters, getApplicationContext());
        mRecyclerView.setAdapter(adapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

    }

    @Override
    protected void onResume() {
        super.onResume();
        mFatherSwitch.setChecked(SettingsInfo.getInstance().isFatherSide());
        mMotherSwitch.setChecked(SettingsInfo.getInstance().isMotherSide());
        mMaleSwitch.setChecked(SettingsInfo.getInstance().isMaleSide());
        mFemaleSwitch.setChecked(SettingsInfo.getInstance().isFemaleSide());

        ArrayList<FilterItem> filters = new ArrayList<>();
        ArrayList<String> allEvents = Storage.getInstance().getAllEventTypes();
        for (int i = 0; i < allEvents.size(); i++) {
            String event = (String) allEvents.get(i);
            FilterItem item = new FilterItem(event);
            filters.add(item);
        }
        FilterAdapter adapter = new FilterAdapter(filters, getApplicationContext());
        mRecyclerView.setAdapter(adapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.invalidate();

    }
}
