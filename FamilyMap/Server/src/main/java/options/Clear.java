package options;

import database.Database;
import results.ClearResult;

//clear class, clears the entire database and returns a clear result
public class Clear {

    public Clear() {}
    public ClearResult clear() {

        Database database = Database.database;
        database.openConnection();

        if (!database.clearTables()) {
            database.closeConnection(false);
            ClearResult clearResult = new ClearResult("Unsuccessful clear.");
            return clearResult;
        }

        database.closeConnection(true);
        ClearResult clearResult = new ClearResult("Clear succeeded.");
        return clearResult;
    }
}
