package dbms;

import dbms.parser.*;


import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;


public class Main {

  protected static Datasource datasource;

  public static void main(String args[]) throws Exception {

    ValidateUser user = new ValidateUser();
    Boolean isUserValidUser = user.performUserValidation();
    if (isUserValidUser) {
      System.out.println(">>>You are signed in as:" + user.getUserName());
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

  private static void doParseAndExecute() throws Exception {
    System.out.println("You are in sql operations");
    System.out.println(">> Please enter your query");
    Scanner scanner= new Scanner(System.in);
    String inputQuery = scanner.nextLine();

    Datasource datasource = new Datasource();
    datasource.createDB("kvskdb1");//hardcode for now
    datasource.setCurrentDatabase("kvskdb1");//hardcode for now
    //create a list of parsers
    ArrayList<Parser> parserList = new ArrayList<Parser>() {
      {
        //add(new CreateTable(datasource));
        add(new CreateDatabase(datasource));
        //add(new InsertRow(datasource));
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
