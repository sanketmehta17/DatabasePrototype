package dbms;

import dbms.logger.CustomLogger;
import dbms.logger.QueryLog;
import dbms.parser.*;


import java.sql.Timestamp;
import java.time.Instant;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.logging.Logger;


public class Main {

    protected static Datasource datasource  = new Datasource();

    private static final List<Parser> parserList = Arrays.asList(new UseDatabase(datasource), new CreateDatabase(datasource),
            new CreateTable(datasource), new InsertRow(datasource));

    private static final Logger logger = CustomLogger.getLogger();

    public static void main(String args[]) throws Exception {
        ValidateUser user = new ValidateUser();
        Boolean isUserValidUser = user.performUserValidation();
        if (isUserValidUser) {
            System.out.println(">>>You are signed in as:" + user.getUserName());
            datasource.connect();
            displayUserMenu();
        } else {
            System.out.println(">> Error in login <<");
        }
    }

    public static void displayUserMenu() throws Exception {
        System.out.println("1.Execute SQL operations");
        System.out.println("2.Generate ERD");
        System.out.println("3. Log out");
        System.out.println("Please select your input");
        Scanner scanner = new Scanner(System.in);
        String menuInput = scanner.nextLine();
        operationType(menuInput);
    }

    public static void operationType(String menuInput) throws Exception {

        if (menuInput.equals("1")) {
            doParseAndExecute();
            displayUserMenu();
        } else if (menuInput.equals("2")) {
            //TO DO ERD
            System.out.println("You are in ERD");
            displayUserMenu();
        } else if (menuInput.equals("3")) {
            System.out.println("End of Program");
        }
    }

    private static void doParseAndExecute() {
        System.out.println("You are in sql operations");
        System.out.println(">> Please enter your query");
        Scanner scanner= new Scanner(System.in);
        String inputQuery = scanner.nextLine();
        logger.info(new QueryLog(inputQuery, Timestamp.from(Instant.now())).toString());
        for(int i=0;i<parserList.size();i++) {
            Parser parser = parserList.get(i);
            Boolean isValidQueryExecute = parser.isValid(inputQuery);
            if(isValidQueryExecute)break;
        }
    }

}
