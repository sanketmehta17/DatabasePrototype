package dbms.metadata;


import java.sql.Timestamp;
import java.time.Instant;
import java.util.*;

import static dbms.Constants.*;
import static java.util.Arrays.stream;
import static java.util.stream.Collectors.toList;
import static java.util.stream.Collectors.toMap;

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
    }

    public TableMeta() {}

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
            columnList.add(columnMetaMapping.getValue().toDBString());
        }
        return columnList.size() > 0 ? String.join(secondaryDelimiter, columnList): null;
    }

    public Map<String, ColumnMeta> getColumnMetaMap() {
        return columnMetaMap;
    }

    public String getPrimaryKey() {
        return primaryKey;
    }

    public Set<String> getColumns() {
        return columnMetaMap.keySet();
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

    public void setPrimaryKey(String columnName) throws Exception {
        if (!columnMetaMap.containsKey(columnName)) {
            System.out.println("Column does not exist");
            throw new Exception();
        }
        primaryKey = columnName;

    }

    @Override
    public List<String> getOrder() {
        return Arrays.asList("name", "dbName", "columns", "createdAt",
                "updatedAt", "locked", "primaryKey", "indexes", "foreignKeys", "uniqueColumns");
    }

    public String toDBString() {
        List<String> metaValues = Arrays.asList(name, dbName, getColumnsString(),
                createdAt.toString(), updatedAt.toString(), locked.toString(), primaryKey,
                getIndexesString(), getForeignKeysString(), getUniqueColumnsString());
        return String.join(delimiter, metaValues);
    }

    public TableMeta fromString(String string) {
        final List<String> fields = stream(string.split(delimiter)).collect(toList());
        name = fields.get(0);
        dbName = fields.get(1);
        columnMetaMap = stream(fields.get(2).split(escapeSequence + secondaryDelimiter))
                .map(field -> new ColumnMeta().fromString(field))
                .collect(toMap(x->x.getName(), x->x));
        createdAt = Timestamp.valueOf(fields.get(3));
        updatedAt = Timestamp.valueOf(fields.get(4));
        locked = Boolean.valueOf(fields.get(5));
        primaryKey = fields.get(6).equals("null") ? null: fields.get(6);
        indexes = fields.get(7).equals("null") ? null : stream(fields.get(7).split(escapeSequence +secondaryDelimiter)).collect(toList());
        foreignKeys = fields.get(8).equals("null") ? null : stream(fields.get(7).split(escapeSequence + secondaryDelimiter)).collect(toList());
        uniqueColumns = fields.get(9).equals("null") ? null : stream(fields.get(7).split(escapeSequence + secondaryDelimiter)).collect(toList());
        return this;
    }

    public String getColumnCreationString() {
        List<String> columnCreationStringList = new ArrayList<>();
        for(ColumnMeta columnMeta: columnMetaMap.values()) {
            columnCreationStringList.add(columnMeta.getName()+ " "+ columnMeta.getType().toString());
        }
        return String.format("(%s)", String.join(",", columnCreationStringList));
    }

    public String toCreateString() {
        return String.format("create table %s %s", name, getColumnCreationString());
    }
}
