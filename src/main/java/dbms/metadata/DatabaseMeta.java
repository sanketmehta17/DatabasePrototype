package dbms.metadata;

import dbms.FileOperator;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.*;
import java.util.stream.Collectors;

import static dbms.Constants.*;
import static java.util.Arrays.stream;
import static java.util.Collections.emptySet;
import static java.util.stream.Collectors.toSet;

public class DatabaseMeta implements Meta {
    private String name;
    private Timestamp createdAt;
    private Timestamp updatedAt;
    //TODO: Add Created User

    public DatabaseMeta() {}

    public DatabaseMeta(String name) {
        this.name = name;
        this.createdAt = Timestamp.from(Instant.now());
        this.updatedAt = Timestamp.from(Instant.now());
    }

    public String getName() {
        return name;
    }


    @Override
    public List<String> getOrder() {
        return Arrays.asList("name", "createdAt", "updatedAt");
    }

    public DatabaseMeta fromString(String databaseMetaString) {
        List<String> databaseMetaFieldValues = getFieldValues(databaseMetaString);
        this.name = databaseMetaFieldValues.get(0);
        this.createdAt = Timestamp.valueOf(databaseMetaFieldValues.get(1));
        this.updatedAt = Timestamp.valueOf(databaseMetaFieldValues.get(2));
        return this;
    }

    private Set<String> getTables(List<String> databaseMetaFieldValues) {
        return databaseMetaFieldValues.get(1).equals("") ? emptySet() : stream(databaseMetaFieldValues.get(1).split(secondaryDelimiter)).collect(toSet());
    }

    private List<String> getFieldValues(String databaseMetaString) {
        return stream(databaseMetaString.split(delimiter)).map(value -> {
            if (value.equals("null")) {
                return null;
            }
            return value;
        }).collect(Collectors.toList());
    }

    public String toDBString() {
        List<String> metaDataRow = new ArrayList<>();
        metaDataRow.add(name);
        metaDataRow.add(createdAt.toString());
        metaDataRow.add(updatedAt.toString());
        return String.join(delimiter, metaDataRow);
    }

    public String toCreateString() {
        return "create database "+ name;
    }

}
