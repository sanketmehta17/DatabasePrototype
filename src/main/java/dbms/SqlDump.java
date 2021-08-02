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
            String tableName = database.getTables().keySet().toString();
            tableName = tableName.replace("[","");
            tableName = tableName.replace("]","");

            for(Table table: database.getTables().values()) {
                fileContent.add(table.getMetaData().toCreateString());
                String createStr = table.getMetaData().toCreateString();
                createStr= createStr.substring(createStr.indexOf("("),createStr.length());
                if(table.toDBString()!=null && table.toDBString()!="")
                fileContent.add("insert into "+tableName+createStr+ " values ("+table.toDBString().replace("~~", ",")+")");
            }
        }
        FileOperator.addToFile(sqlDump, fileContent);
    }
}
