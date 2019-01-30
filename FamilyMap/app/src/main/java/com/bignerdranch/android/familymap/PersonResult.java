package com.bignerdranch.android.familymap;

//person result, contains an array of all the people in the database
public class PersonResult {
    PersonModel[] people;
    //Constructors
    public PersonResult(PersonModel[] people) {
        this.people = people;
    }

    //getter
    public PersonModel[] getPeople() {
        return people;
    }
}
