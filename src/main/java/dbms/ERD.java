package dbms;

import dbms.metadata.ColumnMeta;
import dbms.metadata.TableMeta;

import static java.util.Objects.isNull;

public class ERD {
    private ERD() {}
    public static void generateERD(Datasource datasource) throws Exception {
        if (isNull(datasource.getCurrentDatabase())) {
            System.out.println("DB not selected");
            throw new Exception();
        }
        System.out.println("Table name followed by columns shown\n");
        Database database = datasource.getCurrentDatabase();
        for(Table table: database.getTables().values()) {
            TableMeta tableMeta = table.getMetaData();
            System.out.println(tableMeta.getName());
            for (ColumnMeta columnMeta: tableMeta.getColumnMetaMap().values()) {
                String columnName = columnMeta.getName();
                if (columnName.equals(tableMeta.getPrimaryKey())){
                    columnName = columnName + "(PK)";
                }
                System.out.println(columnName + " - " + columnMeta.getType().toString());
            }
            System.out.println();
        }
    }
}
