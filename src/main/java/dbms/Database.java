package dbms;

import dbms.metadata.DatabaseMeta;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static dbms.Constants.databasesFolder;


public class Database {
    private String name;
    private File folder;
    private Map<String, Table> tables;
    private DatabaseMeta metaData;

    public void create(String name) {
        this.folder = FileOperator.getOrCreateFolder(databasesFolder + "/" + name);
        tables = new HashMap<>();
        metaData = new DatabaseMeta(name);
    }

    public void delete() {
        FileOperator.deleteFolderOrFile(folder);
    }

    public File getFolder() {
        return folder;
    }

    public DatabaseMeta getMetaData() {
        return metaData;
    }

    public void addTable(String name) throws IOException {
        Table table = new Table();
        table.create(name, this);
        tables.put(name, table);
        metaData.addTable(name);
    }

    public void deleteTable(String name) {
        tables.remove(name);
        metaData.removeTable(name);
    }
}
