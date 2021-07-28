package parser;

import java.util.Scanner;

public class queryParser implements Interface.parserInterface{

  private static queryValidator queryValidator;

  public queryParser(){
    queryValidator = new queryValidator();
  }

  @Override
  public void QueryParser() {
    System.out.println("Enter the query:");
    Scanner scanner = new Scanner(System.in);
    String string = scanner.nextLine();
    System.out.println(queryValidator.validateQuery(string));
  }

}
