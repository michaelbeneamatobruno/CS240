package results;

import models.PersonModel;

//personID result, contains a person
public class PersonIDResult {
    PersonModel person;

    public PersonIDResult(PersonModel person) {
        this.person = person;
    }

    public PersonModel getPerson() {
        return person;
    }
}
