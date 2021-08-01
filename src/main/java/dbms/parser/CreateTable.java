package dbms.parser;

import dbms.Database;
import dbms.Datasource;
import dbms.metadata.ColumnMeta;
import dbms.metadata.DataType;
import dbms.metadata.TableMeta;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static java.util.Objects.isNull;


public class CreateTable extends Parser{

    public CreateTable(Datasource datasource) {
        super(datasource);
    }

    private String checkAndReturnPrimaryKey(String column) {
        String primaryKey = null;
        Pattern r = Pattern.compile("PRIMARY KEY (.+)");
        Matcher matcher = r.matcher(column);
        if (matcher.find()) {
            primaryKey = matcher.group(1);
        }
        return primaryKey;
    }

    @Override
    protected String getRegex() {
        return "create *table *(.*) *\\((.*)\\)";
    }

    @Override
    protected void execute(Matcher matcher, String query) {
        try {
            validateCurrentDatabase();
        } catch (Exception e) {
            System.out.println("NO DB SELECTED");
            return;
        }
        String tableName = matcher.group(1).trim();
        Database currentDatabase = datasource.getCurrentDatabase();
        List<String> columns = List.of(matcher.group(2).split(" *, *"));
        List<ColumnMeta> columnMetaList = new ArrayList<>();
        String primaryKey = null;
        for (String column: columns) {
            Boolean isPrimaryKey = column.contains("PRIMARY KEY");
            List<String> columnDetails = List.of(column.split(" +"));
            String columnName = columnDetails.get(0).trim();
            if(isPrimaryKey) primaryKey = columnName;
            DataType columnType = DataType.valueOf(columnDetails.get(1).toUpperCase().trim());
            columnMetaList.add(new ColumnMeta(columnName, columnType));
        }
        TableMeta tableMeta = new TableMeta(tableName, columnMetaList, currentDatabase.getMetaData().getName());
        try {
            if (!isNull(primaryKey)) tableMeta.setPrimaryKey(primaryKey);
            currentDatabase.addTable(tableMeta);

        } catch (Exception e) {
            System.out.println("Invalid Primary Key");
        }
    }
}
