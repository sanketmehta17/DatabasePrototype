package dbms;

import java.util.HashMap;
import java.util.Map;

public class Datasource {
    private String currentUser;
    private final Map<String, Database> databases = new HashMap<>();
    private Database currentDatabase;
    public void connect() {
        FileOperator.createInitialFilesFolders();
    }

    public Database addDatabase(String name) {
        Database database = new Database();
        database.create(name);
        databases.put(name, database);
        return database;
    }

    public void deleteDatabase(String name) {
        Database database = databases.get(name);
        databases.remove(name);
        database.delete();
    }

    public Database getDatabase(String name) {
        return databases.get(name);
    }

    public Database getCurrentDatabase() {
        return currentDatabase;
    }

    public void setCurrentDatabase(String dbName) {
        this.currentDatabase = databases.get(dbName);
    }
}
