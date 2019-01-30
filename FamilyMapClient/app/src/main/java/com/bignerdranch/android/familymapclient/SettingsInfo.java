package com.bignerdranch.android.familymapclient;

import com.bignerdranch.android.familymapclient.Models.EventModel;
import com.bignerdranch.android.familymapclient.Models.PersonModel;

import java.util.ArrayList;
import java.util.Map;

class SettingsInfo {
    private static SettingsInfo INSTANCE = new SettingsInfo();

    static SettingsInfo getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new SettingsInfo();
        }
        return INSTANCE;
    }

    private SettingsInfo() {

    }

    private String mMapType;
    private String mSpouseLinesColor;
    private String mTreeLinesColor;
    private String mStoryLinesColor;

    private Map<String, Boolean> showEventMap;

    private boolean spouseLines;
    private boolean treeLines;
    private boolean storyLines;
    private boolean fatherSide;
    private boolean motherSide;
    private boolean maleSide;
    private boolean femaleSide;

    private ArrayList<EventModel> filteredEvents;
    private ArrayList<PersonModel> filteredPeople;

    public String getMapType() {
        return mMapType;
    }
    public void setMapType(String mapType) {
        mMapType = mapType;
    }

    public boolean isSpouseLines() {
        return spouseLines;
    }
    public void setSpouseLines(boolean spouseLines) {
        this.spouseLines = spouseLines;
    }
    public boolean isTreeLines() {
        return treeLines;
    }
    public void setTreeLines(boolean treeLines) {
        this.treeLines = treeLines;
    }
    public boolean isStoryLines() {
        return storyLines;
    }
    public void setStoryLines(boolean storyLines) {
        this.storyLines = storyLines;
    }

    public String getSpouseLinesColor() {
        return mSpouseLinesColor;
    }
    public void setSpouseLinesColor(String spouseLinesColor) {
        mSpouseLinesColor = spouseLinesColor;
    }
    public String getTreeLinesColor() {
        return mTreeLinesColor;
    }
    public void setTreeLinesColor(String treeLinesColor) {
        mTreeLinesColor = treeLinesColor;
    }
    public String getStoryLinesColor() {
        return mStoryLinesColor;
    }
    public void setStoryLinesColor(String storyLinesColor) {
        mStoryLinesColor = storyLinesColor;
    }

    public boolean isFatherSide() {
        return fatherSide;
    }
    public void setFatherSide(boolean fatherSide) {
        this.fatherSide = fatherSide;
    }
    public boolean isMotherSide() {
        return motherSide;
    }
    public void setMotherSide(boolean motherSide) {
        this.motherSide = motherSide;
    }
    public boolean isMaleSide() {
        return maleSide;
    }
    public void setMaleSide(boolean maleSide) {
        this.maleSide = maleSide;
    }
    public boolean isFemaleSide() {
        return femaleSide;
    }


    public void setFemaleSide(boolean femaleSide) {
        this.femaleSide = femaleSide;
    }
    public ArrayList<EventModel> getFilteredEvents() {
        return filteredEvents;
    }
    public void setFilteredEvents(ArrayList<EventModel> filteredEvents) {
        this.filteredEvents = filteredEvents;
    }
    public ArrayList<PersonModel> getFilteredPeople() {
        return filteredPeople;
    }
    public void setFilteredPeople(ArrayList<PersonModel> filteredPeople) {
        this.filteredPeople = filteredPeople;
    }

    public void startEvents(String event, boolean isValid) {

    }
}
