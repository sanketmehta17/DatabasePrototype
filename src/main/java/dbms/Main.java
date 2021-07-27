package dbms;

import java.io.IOException;

public class Main {
    public static void main(String args[]) throws IOException {
        Datasource datasource = new Datasource();
        datasource.connect();
        Database db = datasource.addDatabase("Test");
        db.addTable("test");
    }
}
