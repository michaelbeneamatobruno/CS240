package com.bignerdranch.android.familymapclient;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.joanzapata.iconify.IconDrawable;
import com.joanzapata.iconify.Iconify;
import com.joanzapata.iconify.fonts.FontAwesomeIcons;
import com.joanzapata.iconify.fonts.FontAwesomeModule;

public class MainActivity extends AppCompatActivity {
    private LoginFragment mLogin;
    private MapFragment mMap;

    private MenuItem settings;
    private MenuItem filter;
    private MenuItem search;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Iconify.with(new FontAwesomeModule());

        FragmentManager fm = getSupportFragmentManager();
        mLogin = (LoginFragment) fm.findFragmentById(R.id.fragment_container);
        mMap = (MapFragment) fm.findFragmentById(R.id.fragment_container);
        if (mLogin == null) {
            mLogin = new LoginFragment();
            fm.beginTransaction().add(R.id.fragment_container, mLogin).commit();
        }
        else if (Storage.getInstance().getLoggedIn()) {
            mMap = new MapFragment();
            fm.beginTransaction().add(R.id.fragment_container, mMap).commit();
        }
        else {
            mLogin = new LoginFragment();
            fm.beginTransaction().add(R.id.fragment_container, mLogin).commit();
        }
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater mi = getMenuInflater();
        mi.inflate(R.menu.main_menu, menu);
        Drawable settingsIcon = new IconDrawable(this, FontAwesomeIcons.fa_gear).
                colorRes(R.color.personActivityMarker).sizeDp(40);
        settings = menu.findItem(R.id.main_settings);
        settings.setIcon(settingsIcon);
        Drawable filterIcon = new IconDrawable(this, FontAwesomeIcons.fa_filter).
                colorRes(R.color.personActivityMarker).sizeDp(40);
        filter = menu.findItem(R.id.main_filter);
        filter.setIcon(filterIcon);
        Drawable searchIcon = new IconDrawable(this, FontAwesomeIcons.fa_search).
                colorRes(R.color.personActivityMarker).sizeDp(40);
        search = menu.findItem(R.id.main_search);
        search.setIcon(searchIcon);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.main_settings) {
            Intent intent = new Intent(this, SettingsActivity.class);
            startActivity(intent);
            return true;
        }
        if (item.getItemId() == R.id.main_filter) {
            Intent intent = new Intent(this,FilterActivity.class);
            startActivity(intent);
            return true;
        }
        if (item.getItemId() == R.id.main_search) {
            Intent intent = new Intent(this, SearchActivity.class);
            startActivity(intent);
            return true;
        }
        return false; //is this right?
    }
}
