package com.bignerdranch.android.familymap;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

public class LoginFragment extends Fragment {

    private LoginRequest mLoginRequest = new LoginRequest();
    private RegisterRequest mRegisterRequest = new RegisterRequest();

    private EditText mServerHost;
    private EditText mServerPort;
    private EditText mUserName;
    private EditText mPassword;
    private EditText mFirstName;
    private EditText mLastName;
    private EditText mEmail;

    private boolean hostFilled = false;
    private boolean portFilled = false;
    private boolean userNameFilled = false;
    private boolean passwordFilled = false;
    private boolean firstNameFilled = false;
    private boolean lastNameFilled = false;
    private boolean emailFilled = false;

    private RadioButton mMaleButton;
    private RadioButton mFemaleButton;

    private Button mRegisterButton;
    private Button mLoginButton;

    private static final String TAG = "LoginFragment";

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    void registerCheck() {
        if (!hostFilled) {
            mRegisterButton.setEnabled(false);
        }
        else if (!portFilled) {
            mRegisterButton.setEnabled(false);
        }
        else if (!userNameFilled) {
            mRegisterButton.setEnabled(false);
        }
        else if (!passwordFilled) {
            mRegisterButton.setEnabled(false);
        }
        else if (!firstNameFilled) {
            mRegisterButton.setEnabled(false);
        }
        else if (!lastNameFilled) {
            mRegisterButton.setEnabled(false);
        }
        else if (!emailFilled) {
            mRegisterButton.setEnabled(false);
        }
        else if ((!mMaleButton.isChecked()) && (!mFemaleButton.isChecked())) {
            mRegisterButton.setEnabled(false);
        }
        else {
            mRegisterButton.setEnabled(true);
        }
    }
    void loginCheck() {
        if (!hostFilled) {
            mLoginButton.setEnabled(false);
        }
        else if (!portFilled) {
            mLoginButton.setEnabled(false);
        }
        else if (!userNameFilled) {
            mLoginButton.setEnabled(false);
        }
        else if (!passwordFilled) {
            mLoginButton.setEnabled(false);
        }
        else {
            mLoginButton.setEnabled(true);
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_login, container, false);

        mMaleButton = v.findViewById(R.id.male_button);
        mFemaleButton = v.findViewById(R.id.female_button);
        mRegisterButton = v.findViewById(R.id.register_button);
        mLoginButton = v.findViewById(R.id.login_button);
        mRegisterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new RegisterTask().execute();
            }
        });
        mLoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new LoginTask().execute();
            }
        });

        mMaleButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mRegisterRequest.setGender("male");
                registerCheck();
            }
        });
        mFemaleButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mRegisterRequest.setGender("female");
                registerCheck();
            }
        });

        mServerHost = v.findViewById(R.id.server_host);
        mServerHost.setText("10.0.2.2");
        hostFilled = true;
        mServerHost.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.toString().isEmpty()) {
                    hostFilled = false;
                }
                else {
                    hostFilled = true;
                }
                registerCheck();
                loginCheck();
            }
            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        mServerPort = v.findViewById(R.id.server_port);
        mServerPort.setText("8085");
        portFilled = true;
        mServerPort.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.toString().isEmpty()) {
                    portFilled = false;
                }
                else {
                    portFilled = true;
                }
                registerCheck();
                loginCheck();
            }
            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        mUserName = v.findViewById(R.id.user_name);
        mUserName.setText("username");
        mRegisterRequest.setUsername("username");
        mLoginRequest.setUsername("username");
        userNameFilled = true;
        mUserName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.toString().isEmpty()) {
                    userNameFilled = false;
                }
                else {
                    userNameFilled = true;
                }
                registerCheck();
                loginCheck();
                mRegisterRequest.setUsername(s.toString());
                mLoginRequest.setUsername(s.toString());
            }
            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        mPassword = v.findViewById(R.id.password);
        mPassword.setText("password");
        mRegisterRequest.setPassword("password");
        mLoginRequest.setPassword("password");
        passwordFilled = true;
        mPassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.toString().isEmpty()) {
                    passwordFilled = false;
                }
                else {
                    passwordFilled = true;
                }
                registerCheck();
                loginCheck();
                mRegisterRequest.setPassword(s.toString());
                mLoginRequest.setPassword(s.toString());
            }
            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        mFirstName = v.findViewById(R.id.first_name);
        mFirstName.setText("firstName");
        mRegisterRequest.setFirstName("firstName");
        firstNameFilled = true;
        mFirstName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.toString().isEmpty()) {
                    firstNameFilled = false;
                }
                else {
                    firstNameFilled = true;
                }
                registerCheck();
                loginCheck();
                mRegisterRequest.setFirstName(s.toString());
            }
            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        mLastName = v.findViewById(R.id.last_name);
        mLastName.setText("lastName");
        mRegisterRequest.setLastName("lastName");
        lastNameFilled = true;
        mLastName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.toString().isEmpty()) {
                    lastNameFilled = false;
                }
                else {
                    lastNameFilled = true;
                }
                registerCheck();
                loginCheck();
                mRegisterRequest.setLastName(s.toString());
            }
            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        mEmail = v.findViewById(R.id.email);
        mEmail.setText("email");
        mRegisterRequest.setEmail("email");
        emailFilled = true;
        mEmail.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.toString().isEmpty()) {
                    emailFilled = false;
                }
                else {
                    emailFilled = true;
                }
                registerCheck();
                loginCheck();
                mRegisterRequest.setEmail(s.toString());
            }
            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        registerCheck();
        loginCheck();
        return v;
    }

    private class LoginTask extends AsyncTask<Void,Void,LoginResult> {
        @Override
        protected LoginResult doInBackground(Void... params) {
            LoginResult result;
            result = ServerProxy.getInstance().getLoginResult(mServerHost.getText().toString(), mServerPort.getText().toString(), mLoginRequest);
            if (result == null) {
                return result;
            }
            Log.i(TAG, "Login Fetched contents of URL");
            Storage.getInstance().setCurrentPersonID(result.getPersonID());
            Storage.getInstance().setCurrentAuthToken(result.getAuthToken());
            return result;
        }
        @Override
        protected void onPostExecute(LoginResult result) {
            if (result == null) {
                Toast.makeText(getActivity(), "Error: you have a connection problem", Toast.LENGTH_SHORT).show();
            }
            if (result.getResult() != null) {
                Toast.makeText(getActivity(), result.getResult(), Toast.LENGTH_LONG).show();
            }
            else {
                new EventTask().execute(result.getAuthToken());
                new PersonTask().execute(result.getAuthToken());
            }
        }
    }
    private class RegisterTask extends AsyncTask<Void,Void,RegisterResult> {
        @Override
        protected RegisterResult doInBackground(Void... params) {
            RegisterResult result;
            result = ServerProxy.getInstance().getRegisterResult(mServerHost.getText().toString(), mServerPort.getText().toString(), mRegisterRequest);
            if (result == null) {
                return result;
            }
            Log.i(TAG, "Register Fetched contents of URL");
            Storage.getInstance().setCurrentPersonID(result.getPersonID());
            Storage.getInstance().setCurrentAuthToken(result.getAuthToken());
            return result;
        }
        @Override
        protected void onPostExecute(RegisterResult result) {
            if (result == null) {
                Toast.makeText(getActivity(), "Error: you have a connection problem", Toast.LENGTH_SHORT).show();
            }
            if (result.getResult() != null) {
                Toast.makeText(getActivity(), result.getResult(), Toast.LENGTH_LONG).show();
            }
            else {
                new EventTask().execute(result.getAuthToken());
                new PersonTask().execute(result.getAuthToken());
            }
        }
    }

    private class PersonTask extends AsyncTask<String,Void,PersonResult> {
        @Override
        protected PersonResult doInBackground(String... params) {
            PersonResult result;
            String authToken = params[0];
            result = ServerProxy.getInstance().getPersonResult(mServerHost.getText().toString(), mServerPort.getText().toString(), authToken);
            Storage.getInstance().setPersonResult(result);
            return result;
        }
        @Override
        protected void onPostExecute(PersonResult result) {
            PersonModel current;
            for (int i = 0; i < result.getPeople().length; i++) {
                current = result.getPeople()[i];
                if (current.getPersonID().equals(Storage.getInstance().getCurrentPersonID())) {
                    Toast.makeText(getActivity(), "firstName: " + current.getFirstName() + "\nlastName: " + current.getLastName(), Toast.LENGTH_SHORT).show();
                }
            }
        }
    }
    private class EventTask extends AsyncTask<String,Void,EventResult> {
        @Override
        protected EventResult doInBackground(String... params) {
            EventResult result;
            String authToken = params[0];
            result = ServerProxy.getInstance().getEventResult(mServerHost.getText().toString(), mServerPort.getText().toString(), authToken);
            Storage.getInstance().setEventResult(result);
            return result;
        }
        @Override
        protected void onPostExecute(EventResult result) {

        }
    }
}