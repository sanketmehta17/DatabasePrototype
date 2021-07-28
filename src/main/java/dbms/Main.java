package dbms;
import parser.queryParser;

import java.io.IOException;

public class Main {
    public static void main(String args[]) throws IOException {
        ValidateUser user = new ValidateUser();
        Boolean isUserValidUser = user.performUserValidation();
        if(isUserValidUser) {
            System.out.println(">>>You are signed in as:"+user.getUserName());
        } else {
            System.out.println(">> Error in login <<");
        }
        Datasource datasource = new Datasource();
        datasource.connect();
        Database db = datasource.addDatabase("Test");
        db.addTable("newTable");
        queryParser queryParser = new queryParser();
        queryParser.QueryParser();
    }
}
