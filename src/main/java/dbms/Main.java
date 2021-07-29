package dbms;


import dbms.parser.Parser;
import dbms.parser.UseDatabase;

public class Main {
    public static void main(String args[]) {
        ValidateUser user = new ValidateUser();
        Boolean isUserValidUser = user.performUserValidation();
        if(isUserValidUser) {
            System.out.println(">>>You are signed in as:"+user.getUserName());
        } else {
            System.out.println(">> Error in login <<");
        }
        Datasource datasource = new Datasource();
        Parser parser = new UseDatabase(datasource);
        String query = "use data;";
        System.out.println(parser.isValid(query));
    }
}
