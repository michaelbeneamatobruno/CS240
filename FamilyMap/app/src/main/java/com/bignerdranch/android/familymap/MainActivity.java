package com.bignerdranch.android.familymap;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {
    private LoginFragment login;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FragmentManager fm = getSupportFragmentManager();
        Fragment fragment = fm.findFragmentById(R.id.fragment_container);
        if (Storage.getInstance().getIsLoggedIn()) {

        }
        if (fragment == null) {
            fragment = new LoginFragment();
            fm.beginTransaction().add(R.id.fragment_container, fragment).commit();
        }
    }
}
