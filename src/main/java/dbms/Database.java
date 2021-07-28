package dbms;

import dbms.metadata.DatabaseMeta;
import dbms.metadata.TableMeta;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import static dbms.Constants.databasesFolder;


public class Database {
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

    public void addTable(TableMeta tableMeta) {
        if (tables.containsKey(tableMeta.getName())) {
            System.out.println("Table Already exists");
            return;
        }
        Table table = new Table();
        table.create(tableMeta);
        tables.put(tableMeta.getName(), table);
        metaData.addTable(tableMeta.getName());
    }

    public void deleteTable(String name) {
        tables.remove(name);
        metaData.removeTable(name);
    }
}
