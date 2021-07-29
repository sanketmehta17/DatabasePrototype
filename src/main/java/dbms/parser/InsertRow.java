package dbms.parser;

import dbms.*;
import dbms.metadata.ColumnMeta;
import dbms.metadata.TableMeta;

import java.util.*;
import java.util.regex.Matcher;

import static dbms.Constants.escapeSequence;
import static dbms.Constants.secondaryDelimiter;
import static java.util.Objects.isNull;

public class InsertRow extends Parser{
    public InsertRow(Datasource datasource) {
        super(datasource);
    }

    @Override
    protected String getRegex() {
        return "insert +into +(.+) *\\((.+)\\) *values *\\((.+)\\)";
    }

    private void checkTableExist(Table table) throws Exception {
        if (isNull(table)) {
            throw new Exception("Invalid table");
        }
    }

    private Map<String, Object> getInputMap(String columnNamesString, String columnValuesString) throws Exception {
        List<String> columnsNames = List.of(columnNamesString.split(escapeSequence + secondaryDelimiter));
        List<String> columnValues = List.of(columnValuesString.split(escapeSequence + secondaryDelimiter));
        if(columnsNames.size() != columnValues.size()) {
            throw new Exception("column and values does not match");
        }
        Map<String, Object> inputMap = new HashMap<>();
        for(int i=0; i<columnsNames.size();i++) {
            inputMap.put(columnsNames.get(i).trim(), columnValues.get(i).trim());
        }
        return inputMap;
    }


    @Override
    protected void execute(Matcher matcher, String query) {
        try {
            validateCurrentDatabase();
            Database currentDatabase = datasource.getCurrentDatabase();
            String tableName = matcher.group(1).trim();
            String columnNames = matcher.group(2);
            String values = matcher.group(3);
            Table table = currentDatabase.getTable(tableName);

            checkTableExist(table);

            Map<String, Object> inputMap = getInputMap(columnNames, values);
            TableMeta tableMeta = table.getMetaData();
            Set<String> tableColumns = tableMeta.getColumns();

            Map<String, ColumnMeta> columnMetaMap = tableMeta.getColumnMetaMap();
            List<Column>columns = new ArrayList<>();
            for (String columnName: tableColumns) {
                Column column = new Column(inputMap.getOrDefault(columnName, null));
                columns.add(column);
            }
            Row row = new Row(columns);
            table.addRow(row);
        } catch (Exception ex) {
            System.out.println("Invalid options provided for insert.");
        }
    }
}
