package dbms;

import dbms.parser.*;

import java.util.ArrayList;

public class WithoutValidation {
    public static void main(String args[]) throws Exception {
        Datasource datasource = new Datasource();
        String databaseName = "TestDatabase";
        datasource.connect();
        datasource.setCurrentDatabase("DB");
        String inputQuery = "create table aTable (id INT, name VARCHAR)";
        //create a list of parsers
        ArrayList<Parser> parserList = new ArrayList<>() {
            {
                add(new UseDatabase(datasource));
                add(new CreateTable(datasource));
                add(new CreateDatabase(datasource));
                add(new InsertRow(datasource));
                add(new SelectTable(datasource));
            }
        };
        for(int i=0;i<parserList.size();i++) {
            Parser parser = parserList.get(i);
            Boolean isValidQueryExecute = parser.isValid(inputQuery);
            if(isValidQueryExecute)break;
        }
    }
}