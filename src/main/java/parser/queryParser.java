package parser;

import java.util.Scanner;

public class queryParser implements Interface.parserInterface{

  private static queryValidator queryValidator;

  public queryParser(){
    queryValidator = new queryValidator();
  }

  public static void main(String[] args) {
    System.out.println("Enter the query:");
    Scanner scanner = new Scanner(System.in);
    String string = scanner.nextLine();
    queryParser queryParser = new queryParser();
    System.out.println(queryValidator.validateQuery(string));
  }
}
