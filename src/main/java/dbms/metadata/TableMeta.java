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
    private List<String> uniqueColumns = new ArrayList<>();

    public TableMeta(String name, List<ColumnMeta> columnMetaList, String dbName) {
        this.name = name;
        this.dbName = dbName;
        this.createdAt = Timestamp.from(Instant.now());
        this.updatedAt = Timestamp.from(Instant.now());
        this.locked = false;
        this.columnMetaMap = new HashMap<>();
        setColumnMetaMap(columnMetaList);
        FileOperator.appendToFile(tablesMetaDataFile, this.toString());
    }

    public String getName() {
        return name;
    }

    public String getDbName() {return dbName;}

    public void setColumnMetaMap(List<ColumnMeta> columnMetaList) {
        columnMetaMap = new HashMap<>();
        for (ColumnMeta columnMeta: columnMetaList) {
            columnMeta.setOrder(columnMetaList.indexOf(columnMeta));
            columnMetaMap.put(columnMeta.getName(), columnMeta);
        }
    }

    private String getColumnsString() {
        List<String> columnList = new ArrayList<>();
        for (Map.Entry<String, ColumnMeta> columnMetaMapping: columnMetaMap.entrySet()) {
            columnList.add(columnMetaMapping.getValue().toString());
        }
        return columnList.size() > 0 ? String.join(secondaryDelimiter, columnList): null;
    }

    private String getIndexesString() {
        return  indexes.size() > 0 ? String.join(secondaryDelimiter, indexes): null;
    }

    private String getForeignKeysString() {
        return  foreignKeys.size() > 0 ? String.join(secondaryDelimiter, foreignKeys): null;
    }

    private String getUniqueColumnsString() {
        return  uniqueColumns.size() > 0 ? String.join(secondaryDelimiter, uniqueColumns): null;
    }


    @Override
    public List<String> getOrder() {
        return Arrays.asList("name", "dbName", "columns", "createdAt",
                "updatedAt", "locked", "primaryKey", "indexes", "foreignKeys", "uniqueColumns");
    }

    public String toString() {
        List<String> metaValues = Arrays.asList(name, dbName, getColumnsString(),
                createdAt.toString(), updatedAt.toString(), locked.toString(), primaryKey,
                getIndexesString(), getForeignKeysString(), getUniqueColumnsString());
        return String.join(delimiter, metaValues);
    }
}
