package dbms;

import dbms.metadata.DatabaseMeta;
import dbms.metadata.TableMeta;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import static dbms.Constants.databasesFolder;
import static dbms.Constants.tablesMetaDataFile;


public class Database {
    private Map<String, Table> tables;
    private DatabaseMeta metaData;

    public Database(Map<String, Table> tables, DatabaseMeta metaData) {
        this.tables = tables;
        this.metaData =metaData;
    }

    public Database(){}

    public void create(String name) {
        FileOperator.getOrCreateFolder(databasesFolder + "/" + name);
        tables = new HashMap<>();
        metaData = new DatabaseMeta(name);
    }

    public Table getTable(String tableName) {
        return tables.get(tableName);
    }

    public void delete() {
        FileOperator.deleteFolderOrFile(new File(databasesFolder + "/" + metaData.getName()));
    }

    public DatabaseMeta getMetaData() {
        return metaData;
    }

    public void addTable(TableMeta tableMeta) {
        if (tables.containsKey(tableMeta.getName())) {
            System.out.println("Table Already exists");
            return;
        }
        Table table = new Table(tableMeta);
        table.create();
        tables.put(tableMeta.getName(), table);
        FileOperator.appendToFile(tablesMetaDataFile, tableMeta.toDBString());
    }

    public void deleteTable(String name) {
        tables.remove(name);
    }

    public Map<String, Table> getTables() {
        return tables;
    }
}
