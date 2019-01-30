package database.DataGeneration;

import java.util.UUID;

//A class that creates a UUID
public class UUIDGenerator {

    public String getUUID() {
        UUID id = UUID.randomUUID();
        return id.toString();
    }
}
