package dbms.metadata;

import dbms.FileOperator;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.*;

import static dbms.Constants.*;

public class TableMeta implements Meta{
    private String name;
    private String dbName;
    private Set<String> columns = null;
    private Map<String, ColumnMeta> columnMetaMap;
    private Timestamp createdAt;
    private Timestamp updatedAt;
    private Boolean locked = false;
    private String primaryKey = null;
    private List<String> indexes= new ArrayList<>();
    private List<String> foreignKeys = new ArrayList<>();

    public TableMeta(String name, DatabaseMeta databaseMeta) {
        this.name = name;
        this.dbName = databaseMeta.getName();
        this.createdAt = Timestamp.from(Instant.now());
        this.updatedAt = Timestamp.from(Instant.now());
        this.locked = false;
        this.columnMetaMap = new HashMap<>();
        FileOperator.appendToFile(tablesMetaDataFile, this.toString());
    }

    public String getName() {
        return name;
    }


    private String getColumnsString() {
        List<String> columnList = new ArrayList<>();
        for (Map.Entry<String, ColumnMeta> columnMetaMapping: columnMetaMap.entrySet()) {
            columnList.add(columnMetaMapping.getKey());
        }
        return columnList.size() > 0 ? String.join(secondaryDelimiter, columnList): null;
    }

    private String getIndexesString() {
        return  indexes.size() > 0 ? String.join(secondaryDelimiter, indexes): null;
    }

    private String getForeignKeysString() {
        return  foreignKeys.size() > 0 ? String.join(secondaryDelimiter, foreignKeys): null;
    }

    @Override
    public List<String> getOrder() {
        return Arrays.asList("name", "dbName", "columns", "createdAt",
                "updatedAt", "locked", "primaryKey", "indexes", "foreignKeys");
    }

    public String toString() {
        List<String> metaValues = Arrays.asList(name, dbName, getColumnsString(),
                createdAt.toString(), updatedAt.toString(), locked.toString(), primaryKey,
                indexes.toString(), foreignKeys.toString());
        return String.join(delimiter, metaValues);
    }
}
