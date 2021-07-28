package dbms.metadata;

import dbms.FileOperator;
import dbms.Table;

import java.lang.reflect.Array;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.*;

import static dbms.Constants.databaseMetaDataFile;
import static dbms.Constants.delimiter;

public class DatabaseMeta implements Meta{
    private String name;
    private Set<String> tables;
    private final Timestamp createdAt;
    private Timestamp updatedAt;
    //TODO: Add Created User

    public DatabaseMeta(String name) {
        this.name = name;
        this.createdAt = Timestamp.from(Instant.now());
        this.updatedAt = Timestamp.from(Instant.now());
        this.tables = new HashSet<>();
        FileOperator.appendToFile(databaseMetaDataFile, this.toString());
    }

    public void addTable(String table) {
        this.tables.add(table);

    }

    public void removeTable(String table) {
        this.tables.remove(table);
    }

    @Override
    public List<String> getOrder() {
        return Arrays.asList("name", "tables", "createdAt", "updatedAt");
    }

    public String toString() {
        List<String> metaDataRow = new ArrayList<>();
        metaDataRow.add(name);
        metaDataRow.add(String.join(",", tables));
        metaDataRow.add(createdAt.toString());
        metaDataRow.add(updatedAt.toString());
        return String.join(delimiter, metaDataRow);
    }

    public String getName() {
        return name;
    }
}
