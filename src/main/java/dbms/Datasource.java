package dbms;

import dbms.metadata.DatabaseMeta;
import dbms.metadata.TableMeta;

import java.util.*;

import static dbms.Constants.*;
import static java.util.Objects.isNull;

public class Datasource {
    private final Map<String, Database> databases = new HashMap<>();
    private Database currentDatabase;
    public void connect() {
        FileOperator.createInitialFilesFolders();
        loadDatabases();
    }

    public Map<String, List<TableMeta>> getDBTableMetaMap(List<String> tableStrings) {
        Map<String, List<TableMeta>> dbTableMetaMap = new HashMap<>();
        for (String tableString: tableStrings) {
            TableMeta tableMeta = new TableMeta().fromString(tableString);
            if (!dbTableMetaMap.containsKey(tableMeta.getDbName())){

                dbTableMetaMap.put(tableMeta.getDbName(), new ArrayList<>(Arrays.asList(tableMeta)));
            } else {
                dbTableMetaMap.get(tableMeta.getDbName()).add(tableMeta);
            }
        }
        return dbTableMetaMap;
    }

    public void loadDatabases() {
        List<String> databasesMeta = FileOperator.getFileContents(databaseMetaDataFile);
        List<String> tablesMeta = FileOperator.getFileContents(tablesMetaDataFile);
        Map<String, List<TableMeta>> dbTableMap = getDBTableMetaMap(tablesMeta);
        for (String databaseMetaString: databasesMeta) {
            DatabaseMeta databaseMeta = new DatabaseMeta().fromString(databaseMetaString);
            List<TableMeta> tableMetasForThisDB = dbTableMap.get(databaseMeta.getName());
            Map<String, Table> tableMap = new HashMap<>();
            if(!isNull(tableMetasForThisDB)) {
                for (TableMeta tableMeta: tableMetasForThisDB) {
                    Table table = new Table(tableMeta);
                    table.populate();
                    tableMap.put(table.getMetaData().getName(), table);
                }
            }
            databases.put(databaseMeta.getName(), new Database(tableMap, databaseMeta));
        }
    }

    public Database createDB(String name) {
        Database database = new Database();
        database.create(name);
        databases.put(name, database);
        FileOperator.appendToFile(databaseMetaDataFile, database.getMetaData().toDBString());
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
        System.out.println("Current database set to "+ currentDatabase.getMetaData().getName());
    }

    public Map<String, Database> getDatabases() {
        return databases;
    }
}
