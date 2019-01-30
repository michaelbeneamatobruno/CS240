package com.bignerdranch.android.familymapclient;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CompoundButton;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;

public class SettingsActivity extends AppCompatActivity {
    private Switch storyLinesSwitch;
    private Switch treeLinesSwitch;
    private Switch spouseLinesSwitch;

    private Spinner storyLinesSpinner;
    private Spinner treeLinesSpinner;
    private Spinner spouseLinesSpinner;
    private Spinner mapTypeSpinner;

    private TextView storyLinesText;
    private TextView treeLinesText;
    private TextView spouseLinesText;
    private TextView mapTypeText;
    private TextView resyncText;
    private TextView logoutText;

    private ConstraintLayout resyncBox;
    private ConstraintLayout logoutBox;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        getSupportActionBar().setTitle("Family Map: Settings");

        storyLinesSwitch = findViewById(R.id.story_lines_switch);
        treeLinesSwitch = findViewById(R.id.tree_lines_switch);
        spouseLinesSwitch = findViewById(R.id.spouse_lines_switch);

        storyLinesSpinner = findViewById(R.id.story_lines_spinner);
        treeLinesSpinner = findViewById(R.id.tree_lines_spinner);
        spouseLinesSpinner = findViewById(R.id.spouse_lines_spinner);
        mapTypeSpinner = findViewById(R.id.map_type_spinner);

        storyLinesText = findViewById(R.id.story_lines_text);
        treeLinesText = findViewById(R.id.tree_lines_text);
        spouseLinesText = findViewById(R.id.spouse_lines_text);
        mapTypeText = findViewById(R.id.map_type_text);
        resyncText = findViewById(R.id.resync_data_text);
        logoutText = findViewById(R.id.logout_text);

        resyncBox = findViewById(R.id.resync_data_box);
        logoutBox = findViewById(R.id.logout_box);

        String storyLines = "Life Story Lines\nSHOW LIFE STORY LINES";
        storyLinesText.setText(storyLines);
        String treeLines = "Family Tree Lines\nSHOW TREE LINES";
        treeLinesText.setText(treeLines);
        String spouseLines = "Spouse Lines\nSHOW SPOUSE LINES";
        spouseLinesText.setText(spouseLines);
        final String mapType = "Map Type\nBACKGROUND DISPLAY ON MAP";
        mapTypeText.setText(mapType);
        String resync = "Re-sync Data\nFROM FAMILYMAP SERVICE";
        resyncText.setText(resync);
        String logout = "Logout\nRETURNS TO LOGIN SCREEN";
        logoutText.setText(logout);

        storyLinesSwitch.setChecked(SettingsInfo.getInstance().isStoryLines());
        treeLinesSwitch.setChecked(SettingsInfo.getInstance().isTreeLines());
        spouseLinesSwitch.setChecked(SettingsInfo.getInstance().isSpouseLines());

        storyLinesSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                SettingsInfo.getInstance().setStoryLines(isChecked);
            }
        });
        treeLinesSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                SettingsInfo.getInstance().setTreeLines(isChecked);
            }
        });
        spouseLinesSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                SettingsInfo.getInstance().setSpouseLines(isChecked);
            }
        });



        String[] arrayStoryLines = new String[] {
                "RED", "BLUE", "GREEN", "YELLOW"
        };
        ArrayAdapter<String> adapterStoryLines = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item, arrayStoryLines);
        adapterStoryLines.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        storyLinesSpinner.setAdapter(adapterStoryLines);
        int storyIndex = getIndex(storyLinesSpinner, SettingsInfo.getInstance().getStoryLinesColor());
        storyLinesSpinner.setSelection(storyIndex);
        String[] arrayTreeLines = new String[] {
                "RED", "BLUE", "GREEN", "YELLOW"
        };
        ArrayAdapter<String> adapterTreeLines = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item, arrayTreeLines);
        adapterTreeLines.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        treeLinesSpinner.setAdapter(adapterTreeLines);
        int treeIndex = getIndex(treeLinesSpinner, SettingsInfo.getInstance().getTreeLinesColor());
        treeLinesSpinner.setSelection(treeIndex);
        String[] arraySpouseLines = new String[] {
                "RED", "BLUE", "GREEN", "YELLOW"
        };
        ArrayAdapter<String> adapterSpouseLines = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item, arraySpouseLines);
        adapterSpouseLines.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spouseLinesSpinner.setAdapter(adapterSpouseLines);
        int spouseIndex = getIndex(spouseLinesSpinner, SettingsInfo.getInstance().getSpouseLinesColor());
        spouseLinesSpinner.setSelection(spouseIndex);
        String[] arrayMapType = new String[] {
                "normal", "hybrid", "satellite", "terrain"
        };
        ArrayAdapter<String> adapterMapType = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item, arrayMapType);
        adapterMapType.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mapTypeSpinner.setAdapter(adapterMapType);
        int mapTypeIndex = getIndex(mapTypeSpinner, SettingsInfo.getInstance().getTreeLinesColor());
        mapTypeSpinner.setSelection(mapTypeIndex);


        storyLinesSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String color = storyLinesSpinner.getItemAtPosition(position).toString().toUpperCase();
                SettingsInfo.getInstance().setStoryLinesColor(color);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        treeLinesSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String color = treeLinesSpinner.getItemAtPosition(position).toString().toUpperCase();
                SettingsInfo.getInstance().setTreeLinesColor(color);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        spouseLinesSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String color = spouseLinesSpinner.getItemAtPosition(position).toString().toUpperCase();
                SettingsInfo.getInstance().setSpouseLinesColor(color);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        mapTypeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String mapType = mapTypeSpinner.getItemAtPosition(position).toString().toLowerCase();
                SettingsInfo.getInstance().setMapType(mapType);
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });










        resyncBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //do something
                Intent intent = new Intent(SettingsActivity.this, MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });
        logoutBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Storage.getInstance().setLoggedIn(false);
                Intent intent = new Intent(SettingsActivity.this, MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });








    }


    private int getIndex(Spinner spinner, String myString) {
        for (int i = 0; i < spinner.getCount(); i++){
            if (spinner.getItemAtPosition(i).toString().equalsIgnoreCase(myString)){
                return i;
            }
        }

        return 0;
    }
}
