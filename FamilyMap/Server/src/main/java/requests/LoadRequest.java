package requests;
import models.EventModel;
import models.PersonModel;
import models.UserModel;

//load request, essentially just contains the information needed to perform the load option
public class LoadRequest {

    UserModel[] users;
    PersonModel[] persons;
    EventModel[] events;

    public LoadRequest(UserModel[] users, PersonModel[] people, EventModel[] events) {
        this.users = users;
        this.persons = people;
        this.events = events;
    }

    //getters
    public UserModel[] getUsers() {
        return users;
    }
    public PersonModel[] getPeople() {
        return persons;
    }
    public EventModel[] getEvents() {
        return events;
    }
}
