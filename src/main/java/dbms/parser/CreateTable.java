package dbms.parser;

import dbms.Database;
import dbms.Datasource;
import dbms.metadata.ColumnMeta;
import dbms.metadata.DataType;
import dbms.metadata.TableMeta;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;


public class CreateTable extends Parser{

    public CreateTable(Datasource datasource) {
        super(datasource);
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
        }
        String tableName = matcher.group(1).trim();
            Database currentDatabase = datasource.getCurrentDatabase();
            List<String> columns = List.of(matcher.group(2).split(" *, *"));
            List<ColumnMeta> columnMetaList = new ArrayList<>();
            for (String column: columns) {
                List<String> columnDetails = List.of(column.split(" +"));
                String columnName = columnDetails.get(0).trim();
                DataType columnType = DataType.valueOf(columnDetails.get(1).toUpperCase().trim());
                columnMetaList.add(new ColumnMeta(columnName, columnType));
            }
            TableMeta tableMeta = new TableMeta(tableName, columnMetaList, currentDatabase.getMetaData().getName());
            currentDatabase.addTable(tableMeta);
    }
}
