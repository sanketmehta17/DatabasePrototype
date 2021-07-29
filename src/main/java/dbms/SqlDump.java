package dbms;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static dbms.Constants.sqlDump;

public class SqlDump {
    private SqlDump() {}
    public static void getDump(Datasource datasource) {
        List<String> fileContent = new ArrayList<>();
        for(Database database: datasource.getDatabases().values()) {
            fileContent.add(database.getMetaData().toCreateString());
            fileContent.add("use "+database.getMetaData().getName());
            for(Table table: database.getTables().values()) {
                fileContent.add(table.getMetaData().toCreateString());
            }
        }
        FileOperator.addToFile(sqlDump, fileContent);
    }
}
