package dbms;

import dbms.parser.CreateDatabase;
import dbms.parser.Parser;

import java.util.Scanner;


public class Main {

  protected static Datasource datasource;

  public static void main(String args[]) {

    ValidateUser user = new ValidateUser();
    Boolean isUserValidUser = user.performUserValidation();
    if (isUserValidUser) {
      System.out.println(">>>You are signed in as:" + user.getUserName());
      displayUserMenu();
    } else {
      System.out.println(">> Error in login <<");
    }
  }

  public static void displayUserMenu() {
    System.out.println("1.Execute CRUD operations");
    System.out.println("2.Generate ERD");
    System.out.println("3. Log out");
    System.out.println("Please select your input");
    Scanner scanner = new Scanner(System.in);
    String menuInput = scanner.nextLine();
    if (menuInput.equals("1")) {
      System.out.println("You are in crud");
      System.out.println(">> Please enter your query");
      String inputQuery = scanner.nextLine();

      Parser parser = new CreateDatabase(new Datasource());
      parser.isValid(inputQuery);
    } else if (menuInput.equals("2")) {
      System.out.println("You are in ERD");
    } else if (menuInput.equals("3")) {
      System.out.println("End of Program");
    }
  }
}
