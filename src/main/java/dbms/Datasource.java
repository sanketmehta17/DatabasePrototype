package dbms;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static dbms.Constants.databasesFolder;

public class Datasource {
    private String name;
    private final Map<String, Database> databases = new HashMap<>();
    private Database currentDatabase;
    public void connect() throws IOException {
        File databases = new File(databasesFolder);
        if (!databases.exists()) {
            databases.mkdir();
        }
    }

    public Database addDatabase(String name) {
        Database database = new Database();
        database.create(name, databasesFolder);
        databases.put(name, database);
        return database;
    }

    public void deleteDatabase(String name) {
        Database database = databases.get(name);
        databases.remove(name);
        database.delete();
    }

    public void getDatabase(String name) {
        databases.get(name);
    }

    public Database getCurrentDatabase() {
        return currentDatabase;
    }

    public void setCurrentDatabase(String dbName) {
        this.currentDatabase = databases.get(dbName);
    }
}
