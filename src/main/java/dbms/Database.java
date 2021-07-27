package dbms;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;


public class Database implements DatasourceEntity{
    private String name;
    private File folder;
    private Map<String, Table> tables;

    @Override
    public void create(String name, String basePath) {
        this.name = name;
        this.folder = new File(basePath + "/" + name);
        folder.mkdir();
        tables = new HashMap<>();
    }

    @Override
    public void delete() {
        this.folder.delete();
    }

    public void addTable(String name) throws IOException {
        Table table = new Table();
        table.create(name, folder.getPath());
        tables.put(name, table);
    }

    public void deleteTable(String name) {
        tables.remove(name);
    }
}
