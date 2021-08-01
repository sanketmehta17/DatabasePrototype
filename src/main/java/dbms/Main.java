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
          new SelectTable(datasource), new CreateTable(datasource), new InsertRow(datasource), new DeleteTable(datasource));

    private static final Logger logger = CustomLogger.getLogger();

    protected static Transaction transaction = new Transaction(datasource,logger,parserList);


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
        System.out.println("2.Generate SQL DUMP");
        System.out.println("3.Generate ERD");
        System.out.println("4.Execute a transaction");
        System.out.println("5.Log out");
        System.out.println("Please select your input");
        Scanner scanner = new Scanner(System.in);
        String menuInput = scanner.nextLine();
        operationType(menuInput);
    }

    public static void operationType(String menuInput) throws Exception {

        switch (menuInput) {
            case "1" -> {
                doParseAndExecute();
                displayUserMenu();
            }
            case "2" -> {
                SqlDump.getDump(datasource);
                System.out.println("Dump is generated successfully");
                displayUserMenu();
            }
            case "3" -> {
                System.out.println("Enter database name");
                Scanner scanner= new Scanner(System.in);
                String dbName = scanner.nextLine();
                datasource.setCurrentDatabase(dbName);
                ERD.generateERD(datasource);
            }
            case "4" -> {
                transaction.startTransaction();
            }
            case "5" -> {
                System.out.println("End of Program");
            }
            default -> {
                System.out.println("Invalid choice. Please try again");
                displayUserMenu();
            }
        }

/*        if (menuInput.equals("1")) {
            doParseAndExecute();
            displayUserMenu();
        } else if (menuInput.equals("2")) {
            SqlDump.getDump(datasource);
            System.out.println("Dump is generated successfully");
            displayUserMenu();
        } else if (menuInput.equals("3")) {
            transaction.startTransaction();
        } else if (menuInput.equals("4")) {
            System.out.println("End of Program");
        }*/
    }

    private static void doParseAndExecute() {
        System.out.println("You are in sql operations");
        System.out.println(">> Please enter your query");
        Scanner scanner= new Scanner(System.in);
        String inputQuery = scanner.nextLine();
        logger.info(new QueryLog(inputQuery, Timestamp.from(Instant.now())).toString());
        Boolean executed = false;
        for(int i=0;i<parserList.size();i++) {
            Parser parser = parserList.get(i);
            Boolean isValidQueryExecute = parser.isValid(inputQuery);
            if(isValidQueryExecute){
                executed = true;
                break;
            }
        }
        if(!executed) System.out.println("Invalid query");
    }

}
