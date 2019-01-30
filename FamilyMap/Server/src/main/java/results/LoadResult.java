package results;

//load result, contains the number of users, people, and events added to the database
public class LoadResult {
    int numUsers;
    int numPeople;
    int numEvents;

    public LoadResult(int numUsers, int numPeople, int numEvents) {
        this.numUsers = numUsers;
        this.numPeople = numPeople;
        this.numEvents = numEvents;
    }

    //getters
    public int getNumUsers() {
        return numUsers;
    }
    public int getNumPeople() {
        return numPeople;
    }
    public int getNumEvents() {
        return numEvents;
    }
}
