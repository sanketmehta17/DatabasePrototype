package dbms;

import dbms.parser.CreateTable;
import dbms.parser.InsertRow;
import dbms.parser.Parser;

public class WithoutValidation {
    public static void main(String args[]) throws Exception {
        Datasource datasource = new Datasource();
        String databaseName = "TestDatabase";
        datasource.connect();
        datasource.createDB(databaseName);
        datasource.setCurrentDatabase(databaseName);
        Parser createTable = new CreateTable(datasource);
        Parser insertRow = new InsertRow(datasource);
        createTable.isValid("create table user (id int, name varchar, age int)");
        insertRow.isValid("insert into user (id, name, age) values (1, abc, 23)");
    }
}